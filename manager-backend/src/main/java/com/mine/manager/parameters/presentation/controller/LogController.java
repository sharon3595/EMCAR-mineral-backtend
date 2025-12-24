package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.exception.EntityNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Tag(name = "Logs", description = "Operaciones para descargar o visualizar los logs")
@RestController
@RequestMapping("/log")
public class LogController {

  @GetMapping("/downloadLogs")
  public ResponseEntity<Resource> downloadLogFile() {
    return this.processLog(false);
  }

  @GetMapping("/openLogs")
  public ResponseEntity<Resource> openLogFile() {
    return this.processLog(true);
  }

  private ResponseEntity<Resource> processLog(Boolean isOpening) {
    String logFilePath = "logs/spring.log";
    try {
      Path path = Paths.get(logFilePath);
      Resource resource = new UrlResource(path.toUri());
      if (resource.exists() && resource.isReadable()) {
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("text/plain"))
            .header(HttpHeaders.CONTENT_DISPOSITION,
                isOpening ? "inline" : "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
      } else {
        throw new EntityNotFoundException("No se encontró el log");
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }
}

package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.parameters.domain.entity.Mine;
import com.mine.manager.parameters.domain.service.Interfaces.MineService;
import com.mine.manager.parameters.presentation.request.dto.MineDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Mine Service", description = "Módulo para gestionar las Minas")
@AllArgsConstructor
@RestController
@RequestMapping("/mines")
public class MineController {

    private final MineService mineService;

    @Operation(summary = "Listar todas las Minas.")
    @GetMapping
    public ResponseEntity<List<Mine>> getAll() {
        List<Mine> list = mineService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Operation(summary = "Registrar nueva Mina.")
    @PostMapping
    public ResponseEntity<Mine> create(@Valid @RequestBody MineDto dto) {
        Mine saved = mineService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Actualizar Mina por Id.")
    @PutMapping("/{id}")
    public ResponseEntity<Mine> update(@PathVariable Integer id, @Valid @RequestBody MineDto dto) {
        Mine updated = mineService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @Operation(summary = "Obtener Mina por Id.")
    @GetMapping("/{id}")
    public ResponseEntity<Mine> getById(@PathVariable Integer id) {
        Mine found = mineService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

    @Operation(summary = "Eliminar Mina por Id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        mineService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

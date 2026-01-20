package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.parameters.domain.entity.Mineral;
import com.mine.manager.parameters.domain.entity.Mine;
import com.mine.manager.parameters.domain.service.Interfaces.MineralService;
import com.mine.manager.parameters.presentation.request.dto.MineralDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Mineral Service", description = "Módulo para gestionar los minerales")
@AllArgsConstructor
@RestController
@RequestMapping("/minerals")
public class MineralController {


    private final MineralService mineralService;


    @Operation(summary = "Listar todos los minerales.")
    @GetMapping
    public ResponseEntity<List<Mineral>> getAll() {
        List<Mineral> list = mineralService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @Operation(summary = "Registrar nuevo mineral.")
    @PostMapping
    public ResponseEntity<Mineral> create(@Valid @RequestBody MineralDto dto) {
        Mineral saved = mineralService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @Operation(summary = "Actualizar Mineral por Id.")
    @PutMapping("/{id}")
    public ResponseEntity<Mineral> update(@PathVariable Integer id, @Valid @RequestBody MineralDto dto) {
        Mineral updated = mineralService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }


    @Operation(summary = "Obtener Mineral por Id.")
    @GetMapping("/{id}")
    public ResponseEntity<Mineral> getById(@PathVariable Integer id) {
        Mineral found = mineralService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }


    @Operation(summary = "Eliminar Mineral por Id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        mineralService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/select")
    public ResponseEntity<List<Mineral>> getFilteredForSelect(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String symbol,
            @RequestParam(required = false) String some) {
        List<Mineral> minerals = mineralService.getFilteredForSelect(name, symbol, some);
        return ResponseEntity.status(HttpStatus.OK).body(minerals);
    }
}


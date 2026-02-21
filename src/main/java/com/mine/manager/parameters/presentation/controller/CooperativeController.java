package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.parameters.domain.entity.Cooperative;
import com.mine.manager.parameters.domain.service.Interfaces.CooperativeService;
import com.mine.manager.parameters.presentation.request.dto.CooperativeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cooperative Service", description = "Módulo para gestionar los cooperativas")
@AllArgsConstructor
@RestController
@RequestMapping("/cooperatives")
public class CooperativeController {

    private final CooperativeService cooperativeService;

    @Operation(summary = "Listar todos las cooperativas.")
    @GetMapping
    public ResponseEntity<List<Cooperative>> getAll() {
        List<Cooperative> list = cooperativeService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Operation(summary = "Registrar nueva cooperativa.")
    @PostMapping
    public ResponseEntity<Cooperative> create(@Valid @RequestBody CooperativeDto dto) {
        Cooperative saved = cooperativeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Actualizar Cooperativa por Id.")
    @PutMapping("/{id}")
    public ResponseEntity<Cooperative> update(@PathVariable Integer id, @Valid @RequestBody CooperativeDto dto) {
        Cooperative updated = cooperativeService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @Operation(summary = "Obtener Cooperativa por Id.")
    @GetMapping("/{id}")
    public ResponseEntity<Cooperative> getById(@PathVariable Integer id) {
        Cooperative found = cooperativeService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

    @Operation(summary = "Eliminar Cooperativa por Id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cooperativeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}



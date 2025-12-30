package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.parameters.domain.entity.Material;
import com.mine.manager.parameters.domain.entity.Mine;
import com.mine.manager.parameters.domain.service.Interfaces.MaterialService;
import com.mine.manager.parameters.presentation.request.dto.MaterialDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Tag(name = "Material Service", description = "Módulo para gestionar los materiales")
@AllArgsConstructor
@RestController
@RequestMapping("/materials")
public class MaterialController {


    private final MaterialService materialService;


    @Operation(summary = "Listar todos los materiales.")
    @GetMapping
    public ResponseEntity<List<Material>> getAll() {
        List<Material> list = materialService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @Operation(summary = "Registrar nuevo material.")
    @PostMapping
    public ResponseEntity<Material> create(@Valid @RequestBody MaterialDto dto) {
        Material saved = materialService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @Operation(summary = "Actualizar Material por Id.")
    @PutMapping("/{id}")
    public ResponseEntity<Material> update(@PathVariable Integer id, @Valid @RequestBody MaterialDto dto) {
        Material updated = materialService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }


    @Operation(summary = "Obtener Material por Id.")
    @GetMapping("/{id}")
    public ResponseEntity<Material> getById(@PathVariable Integer id) {
        Material found = materialService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }


    @Operation(summary = "Eliminar Material por Id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        materialService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/select")
    public ResponseEntity<List<Material>> getFilteredForSelect(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String some) {
        List<Material> materials = materialService.getFilteredForSelect(name,description,some);
        return ResponseEntity.status(HttpStatus.OK).body(materials);
    }
}

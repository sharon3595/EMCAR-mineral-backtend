package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.parameters.domain.entity.TypeMaterial;
import com.mine.manager.parameters.domain.service.Interfaces.TypeMaterialService;
import com.mine.manager.parameters.presentation.request.dto.TypeMaterialDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "TypeMaterial Service", description = "Módulo para gestionar los tipos de materiales")
@AllArgsConstructor
@RestController
@RequestMapping("/typeMaterials")
public class TypeMaterialController {


    private final TypeMaterialService typeMaterialService;


    @Operation(summary = "Listar todos los tipos de materiales.")
    @GetMapping
    public ResponseEntity<List<TypeMaterial>> getAll() {
        List<TypeMaterial> list = typeMaterialService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @Operation(summary = "Registrar nuevo tipo de material.")
    @PostMapping
    public ResponseEntity<TypeMaterial> create(@Valid @RequestBody TypeMaterialDto dto) {
        TypeMaterial saved = typeMaterialService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @Operation(summary = "Actualizar tipo de material por Id.")
    @PutMapping("/{id}")
    public ResponseEntity<TypeMaterial> update(@PathVariable Integer id, @Valid @RequestBody TypeMaterialDto dto) {
        TypeMaterial updated = typeMaterialService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }


    @Operation(summary = "Obtener tipo de material por Id.")
    @GetMapping("/{id}")
    public ResponseEntity<TypeMaterial> getById(@PathVariable Integer id) {
        TypeMaterial found = typeMaterialService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }


    @Operation(summary = "Eliminar tipo de material por Id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        typeMaterialService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


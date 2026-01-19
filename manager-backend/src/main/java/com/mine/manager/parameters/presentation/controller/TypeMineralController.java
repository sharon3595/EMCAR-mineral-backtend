package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.parameters.domain.entity.Mine;
import com.mine.manager.parameters.domain.entity.TypeMineral;
import com.mine.manager.parameters.domain.service.Interfaces.TypeMineralService;
import com.mine.manager.parameters.presentation.request.dto.TypeMineralDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "TypeMineral Service", description = "Módulo para gestionar los tipos de minerales")
@AllArgsConstructor
@RestController
@RequestMapping("/typeMinerals")
public class TypeMineralController {


    private final TypeMineralService typeMineralService;


    @Operation(summary = "Listar todos los tipos de minerales.")
    @GetMapping
    public ResponseEntity<List<TypeMineral>> getAll() {
        List<TypeMineral> list = typeMineralService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @Operation(summary = "Registrar nuevo tipo de mineral.")
    @PostMapping
    public ResponseEntity<TypeMineral> create(@Valid @RequestBody TypeMineralDto dto) {
        TypeMineral saved = typeMineralService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @Operation(summary = "Actualizar tipo de mineral por Id.")
    @PutMapping("/{id}")
    public ResponseEntity<TypeMineral> update(@PathVariable Integer id, @Valid @RequestBody TypeMineralDto dto) {
        TypeMineral updated = typeMineralService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }


    @Operation(summary = "Obtener tipo de mineral por Id.")
    @GetMapping("/{id}")
    public ResponseEntity<TypeMineral> getById(@PathVariable Integer id) {
        TypeMineral found = typeMineralService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }


    @Operation(summary = "Eliminar tipo de mineral por Id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        typeMineralService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/select")
    public ResponseEntity<List<TypeMineral>> getFilteredForSelect(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String symbol,
            @RequestParam(required = false) String some) {
        List<TypeMineral> typeMinerals = typeMineralService.getFilteredForSelect(name, symbol, some);
        return ResponseEntity.status(HttpStatus.OK).body(typeMinerals);
    }
}



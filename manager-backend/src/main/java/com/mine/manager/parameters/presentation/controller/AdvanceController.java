package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.parameters.domain.entity.Advance;
import com.mine.manager.parameters.domain.service.Interfaces.AdvanceService;
import com.mine.manager.parameters.presentation.request.dto.AdvanceDto;
import com.mine.manager.parameters.presentation.response.pojo.AdvancePojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/advances")
@Tag(name = "Advance Service", description = "Módulo para gestionar los anticipos")
public class AdvanceController {

    private final AdvanceService advanceService;

    @Operation(summary = "Obtener todos los anticipos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos los anticipos", content = @Content)})
    @GetMapping
    public ResponseEntity<List<AdvancePojo>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(advanceService.getAdvances());
    }

    @Operation(summary = "Crear nuevo anticipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nuevo anticipo",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Advance.class))}),
            @ApiResponse(responseCode = "400", description = "Entrada invalida", content = @Content)})
    @PostMapping
    public ResponseEntity<AdvancePojo> create(
            @RequestBody @Validated({AdvanceDto.OnCreate.class, Default.class}) AdvanceDto dto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(advanceService.create(dto));
    }

    @Operation(summary = "Actualizar anticipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anticipo actualizado")})
    @PutMapping("/{id}")
    public ResponseEntity<AdvancePojo> update(@PathVariable Integer id,
                                              @Valid @RequestBody AdvanceDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(advanceService.update(id, dto));
    }

    @Operation(summary = "Obtener anticipo por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anticipo encontrado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Advance.class))}),
            @ApiResponse(responseCode = "400", description = "ID incorrecto", content = @Content),
            @ApiResponse(responseCode = "404", description = "Anticipo no encontrado", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<AdvancePojo> getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(advanceService.getAdvanceById(id));
    }

    @Operation(summary = "Eliminar anticipo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Anticipo eliminado"),
            @ApiResponse(responseCode = "400", description = "Id incorrecto", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        advanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todos los anticipos por carga")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos los anticipos por carga", content = @Content)})
    @GetMapping("/load/{id}")
    public ResponseEntity<List<AdvancePojo>> getAllByLoadId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(advanceService.getAdvancesByLoadId(id));
    }
}




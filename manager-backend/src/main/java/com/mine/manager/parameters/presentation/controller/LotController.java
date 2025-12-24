package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.parameters.domain.entity.Lot;
import com.mine.manager.parameters.domain.service.Interfaces.LotService;
import com.mine.manager.parameters.presentation.request.dto.LotDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/lots")
@Tag(name = "Lot Service", description = "Módulo para gestionar los lotes")
public class LotController {


    private final LotService lotService;


    @Operation(summary = "Obtener todos los lotes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos los lotes", content = @Content)})
    @GetMapping
    public ResponseEntity<List<Lot>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(lotService.getLots());
    }


    @Operation(summary = "Crear nuevo lote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nuevo lote",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Lot.class))}),
            @ApiResponse(responseCode = "400", description = "Entrada invalida", content = @Content)})
    @PostMapping
    public ResponseEntity<Lot> create(@Valid @RequestBody LotDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lotService.create(dto));
    }


    @Operation(summary = "Actualizar lote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lote actualizado")})
    @PutMapping("/{id}")
    public ResponseEntity<Lot> update(@PathVariable Integer id,
                                          @Valid @RequestBody LotDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(lotService.update(id, dto));
    }


    @Operation(summary = "Obtener lote por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lote encontrado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Lot.class))}),
            @ApiResponse(responseCode = "400", description = "ID incorrecto", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lote no encontrado", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Lot> getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(lotService.getLotById(id));
    }


    @Operation(summary = "Eliminar lote por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lote eliminado"),
            @ApiResponse(responseCode = "400", description = "Id incorrecto", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        lotService.delete(id);
        return ResponseEntity.noContent().build();
    }


    /*@Operation(summary = "Obtener correlativo general por ID de loto de compañia, periodo mensual y departamento")
    @GetMapping("/correlative")
    public ResponseEntity<GeneralCorrelativePojo> getCorrelative(
            @RequestParam(required = false) Integer lotId,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Integer companyDepartmentId
    ) {


        GeneralCorrelativePojo correlative = lotService.getCodeCorrelative(lotId, date,
                companyDepartmentId);
        return ResponseEntity.status(HttpStatus.OK).body(correlative);
    }*/
}

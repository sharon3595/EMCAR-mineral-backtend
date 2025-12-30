package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.common.enums.LotTypeEnum;
import com.mine.manager.parameters.domain.entity.Lot;
import com.mine.manager.parameters.domain.service.Interfaces.LotService;
import com.mine.manager.parameters.presentation.request.dto.LotDto;
import com.mine.manager.parameters.presentation.request.filter.LoadFilter;
import com.mine.manager.parameters.presentation.request.filter.LotFilter;
import com.mine.manager.parameters.presentation.response.pojo.LoadPojo;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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


    @Operation(summary = "Listar cargas aplicando filtros.")
    @GetMapping("/search")
    public ResponseEntity<List<Lot>> getFiltered(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String prefix,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer initialDocNumber,
            @RequestParam(required = false) Integer currentDocNumber,
            @RequestParam(required = false) String assignment,
            @RequestParam(required = false) Boolean state
    ) {

        LotFilter filter = new LotFilter(
                id,
                prefix,
                description,
                initialDocNumber,
                currentDocNumber,
                assignment,
                state
        );
        List<Lot> listFiltered = lotService.getFiltered(filter);
        return ResponseEntity.status(HttpStatus.OK).body(listFiltered);
    }


    @Operation(summary = "Devolver paginador aplicando filtros.")
    @GetMapping("/pageable")
    public ResponseEntity<PagePojo<Lot>> getByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String prefix,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer initialDocNumber,
            @RequestParam(required = false) Integer currentDocNumber,
            @RequestParam(required = false) String assignment,
            @RequestParam(required = false) Boolean state
    ) {

        LotFilter filter = new LotFilter(
                id,
                prefix,
                description,
                initialDocNumber,
                currentDocNumber,
                assignment,
                state
        );

        PagePojo<Lot> pageResult = lotService.getByPageAndFilters(
                page, size, sortBy, sortOrder, filter
        );

        return ResponseEntity.ok(pageResult);
    }
}

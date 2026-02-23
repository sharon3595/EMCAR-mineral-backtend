package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.parameters.domain.entity.Load;
import com.mine.manager.parameters.domain.service.Interfaces.LoadService;
import com.mine.manager.parameters.presentation.request.dto.LoadDto;
import com.mine.manager.parameters.presentation.request.filter.LoadFilter;
import com.mine.manager.parameters.presentation.response.pojo.CorrelativePojo;
import com.mine.manager.parameters.presentation.response.pojo.LoadPojo;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
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

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/loads")
@Tag(name = "Load Service", description = "Módulo para gestionar la recepción de carga")
public class LoadController {

    private final LoadService loadService;

    @Operation(summary = "Obtener todos las cargas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos las cargas", content = @Content)})
    @GetMapping
    public ResponseEntity<List<LoadPojo>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(loadService.getLoads());
    }


    @Operation(summary = "Crear nueva carga")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nueva carga",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Load.class))}),
            @ApiResponse(responseCode = "400", description = "Entrada invalida", content = @Content)})
    @PostMapping
    public ResponseEntity<LoadPojo> create(@Valid @RequestBody LoadDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loadService.create(dto));
    }


    @Operation(summary = "Actualizar carga")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carga actualizada")})
    @PutMapping("/{id}")
    public ResponseEntity<LoadPojo> update(@PathVariable Integer id,
                                       @Valid @RequestBody LoadDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(loadService.update(id, dto));
    }


    @Operation(summary = "Obtener carga por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carga encontrada",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Load.class))}),
            @ApiResponse(responseCode = "400", description = "ID incorrecto", content = @Content),
            @ApiResponse(responseCode = "404", description = "Carga no encontrada", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<LoadPojo> getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(loadService.getLoadById(id));
    }


    @Operation(summary = "Eliminar carga por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carga eliminado"),
            @ApiResponse(responseCode = "400", description = "Id incorrecto", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        loadService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/correlative/preview/{lotId}")
    @Operation(summary = "Obtener el siguiente código correlativo disponible (Previsualización)")
    public ResponseEntity<CorrelativePojo> getNextCorrelative(@PathVariable Integer lotId) {
        return ResponseEntity.ok(loadService.processCorrelative(lotId, false));
    }



    @Operation(summary = "Listar cargas aplicando filtros.")
    @GetMapping("/search")
    public ResponseEntity<List<LoadPojo>> getFiltered(
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) String correlativeLotCode,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String lotDescription,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String some,
            @RequestParam(required = false) Boolean active
    ) {

        LoadFilter filter = new LoadFilter(
                supplierName,
                correlativeLotCode,
                startDate,
                endDate,
                lotDescription,
                state,
                some,
                active
        );
        List<LoadPojo> listFiltered = loadService.getFiltered(filter);
        return ResponseEntity.status(HttpStatus.OK).body(listFiltered);
    }


    @Operation(summary = "Devolver paginador aplicando filtros.")
    @GetMapping("/pageable")
    public ResponseEntity<PagePojo<LoadPojo>> getByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) String correlativeLotCode,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String lotDescription,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String some,
            @RequestParam(required = false) Boolean active
    ) {

        LoadFilter filter = new LoadFilter(
                supplierName,
                correlativeLotCode,
                startDate,
                endDate,
                lotDescription,
                state,
                some,
                active
        );

        PagePojo<LoadPojo> pageResult = loadService.getByPageAndFilters(
                page, size, sortBy, sortOrder, filter
        );

        return ResponseEntity.ok(pageResult);
    }
}

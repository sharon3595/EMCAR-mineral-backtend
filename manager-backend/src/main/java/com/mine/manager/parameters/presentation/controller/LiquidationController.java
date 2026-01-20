package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.parameters.domain.entity.Liquidation;
import com.mine.manager.parameters.domain.service.Interfaces.LiquidationService;
import com.mine.manager.parameters.presentation.request.dto.LiquidationDto;
import com.mine.manager.parameters.presentation.request.filter.LiquidationFilter;
import com.mine.manager.parameters.presentation.response.pojo.LiquidationPojo;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/liquidations")
@Tag(name = "Liquidation Service", description = "Módulo para gestionar las liquidaciones")
public class LiquidationController {


    private final LiquidationService liquidationService;


    @Operation(summary = "Obtener todas las liquidaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas las liquidaciones", content = @Content)})
    @GetMapping
    public ResponseEntity<List<LiquidationPojo>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(liquidationService.getLiquidations());
    }


    @Operation(summary = "Crear nueva liquidación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nueva liquidación",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Liquidation.class))}),
            @ApiResponse(responseCode = "400", description = "Entrada invalida", content = @Content)})
    @PostMapping
    public ResponseEntity<LiquidationPojo> create(@Valid @RequestBody LiquidationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(liquidationService.create(dto));
    }


    @Operation(summary = "Actualizar liquidación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "liquidación actualizada")})
    @PutMapping("/{id}")
    public ResponseEntity<LiquidationPojo> update(@PathVariable Integer id,
                                                  @Valid @RequestBody LiquidationDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(liquidationService.update(id, dto));
    }


    @Operation(summary = "Obtener liquidación por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "liquidación encontrada",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Liquidation.class))}),
            @ApiResponse(responseCode = "400", description = "ID incorrecto", content = @Content),
            @ApiResponse(responseCode = "404", description = "liquidación no encontrada", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<LiquidationPojo> getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(liquidationService.getLiquidationById(id));
    }


    @Operation(summary = "Eliminar liquidación por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "liquidación eliminada"),
            @ApiResponse(responseCode = "400", description = "Id incorrecto", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        liquidationService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Listar liquidaciones aplicando filtros.")
    @GetMapping("/search")
    public ResponseEntity<List<LiquidationPojo>> getFiltered(
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) String correlativeLotCode,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String lotDescription,
            @RequestParam(required = false) String some
    ) {


        LiquidationFilter filter = new LiquidationFilter(
                supplierName,
                correlativeLotCode,
                startDate,
                endDate,
                some
        );
        List<LiquidationPojo> listFiltered = liquidationService.getFiltered(filter);
        return ResponseEntity.status(HttpStatus.OK).body(listFiltered);
    }


    @Operation(summary = "Devolver paginador aplicando filtros.")
    @GetMapping("/pageable")
    public ResponseEntity<PagePojo<LiquidationPojo>> getByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) String correlativeLotCode,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String lotDescription,
            @RequestParam(required = false) String some
    ) {


        LiquidationFilter filter = new LiquidationFilter(
                supplierName,
                correlativeLotCode,
                startDate,
                endDate,
                some
        );


        PagePojo<LiquidationPojo> pageResult = liquidationService.getByPageAndFilters(
                page, size, sortBy, sortOrder, filter
        );


        return ResponseEntity.ok(pageResult);
    }


    @GetMapping(value = "/report/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getLoadReport(@PathVariable Integer id) {

        byte[] pdf = liquidationService.generateLiquidationPdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=liquidation_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}


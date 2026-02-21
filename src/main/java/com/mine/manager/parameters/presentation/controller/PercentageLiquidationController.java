package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.parameters.domain.entity.PercentageLiquidation;
import com.mine.manager.parameters.domain.service.Interfaces.PercentageLiquidationService;
import com.mine.manager.parameters.presentation.request.dto.LiquidPayableCalculationDto;
import com.mine.manager.parameters.presentation.request.dto.PercentageLiquidationDto;
import com.mine.manager.parameters.presentation.request.filter.LiquidationFilter;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import com.mine.manager.parameters.presentation.response.pojo.PercentageLiquidationPojo;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/percentageLiquidations")
@Tag(name = "PercentageLiquidation Service", description = "Módulo para gestionar las liquidaciones")
public class PercentageLiquidationController {


    private final PercentageLiquidationService percentageLiquidationService;


    @Operation(summary = "Obtener todas las liquidaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas las liquidaciones", content = @Content)})
    @GetMapping
    public ResponseEntity<List<PercentageLiquidationPojo>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(percentageLiquidationService.getPercentageLiquidations());
    }


    @Operation(summary = "Crear nueva liquidación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nueva liquidación",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = PercentageLiquidation.class))}),
            @ApiResponse(responseCode = "400", description = "Entrada invalida", content = @Content)})
    @PostMapping
    public ResponseEntity<PercentageLiquidationPojo> create(@Valid @RequestBody PercentageLiquidationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(percentageLiquidationService.create(dto));
    }


    @Operation(summary = "Actualizar liquidación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "liquidación actualizada")})
    @PutMapping("/{id}")
    public ResponseEntity<PercentageLiquidationPojo> update(@PathVariable Integer id,
                                                            @Valid @RequestBody PercentageLiquidationDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(percentageLiquidationService.update(id, dto));
    }


    @Operation(summary = "Obtener liquidación por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "liquidación encontrada",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = PercentageLiquidation.class))}),
            @ApiResponse(responseCode = "400", description = "ID incorrecto", content = @Content),
            @ApiResponse(responseCode = "404", description = "liquidación no encontrada", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<PercentageLiquidationPojo> getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(percentageLiquidationService.getPercentageLiquidationById(id));
    }

    @Operation(summary = "Devolver paginador aplicando filtros.")
    @GetMapping("/pageable")
    public ResponseEntity<PagePojo<PercentageLiquidationPojo>> getByPage(
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


        PagePojo<PercentageLiquidationPojo> pageResult = percentageLiquidationService.getByPageAndFilters(
                page, size, sortBy, sortOrder, filter
        );

        return ResponseEntity.ok(pageResult);
    }


    @GetMapping(value = "/report/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getLoadReport(@PathVariable Integer id) {

        byte[] pdf = percentageLiquidationService.generatePercentageLiquidationPdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=percentageLiquidation_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @PostMapping("/auxiliar-calculate")
    public ResponseEntity<BigDecimal> calculatePayableAg(
            @Valid @RequestBody LiquidPayableCalculationDto dto
    ) {
        BigDecimal result = percentageLiquidationService.calculatePayableAg(dto);
        return ResponseEntity.ok(result);
    }
}

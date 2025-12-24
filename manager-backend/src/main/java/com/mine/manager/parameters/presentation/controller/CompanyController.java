package com.mine.manager.parameters.presentation.controller;


import com.mine.manager.parameters.domain.entity.Company;
import com.mine.manager.parameters.domain.service.Interfaces.CompanyService;
import com.mine.manager.parameters.presentation.request.dto.CompanyDto;
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
@RequestMapping("/company")
@Tag(name = "Controlador de empresas", description = "Módulo para gestionar las empresas")
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "Listar todos las empresas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas las empresas", content = @Content)})
    @GetMapping
    public ResponseEntity<List<Company>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAll());
    }

    @Operation(summary = "Registrar nueva empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nueva empresa",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Company.class)) }),
            @ApiResponse(responseCode = "400", description = "Entrada invalida", content = @Content)})
    @PostMapping
    public ResponseEntity<Company> create(@Valid @RequestBody CompanyDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.create(dto));
    }

    @Operation(summary = "Actualizar empresa por Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresal actualizada")})
    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@PathVariable Integer id, @Valid @RequestBody CompanyDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.update(id, dto));
    }

    @Operation(summary = "Buscar empresa por Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Company.class)) }),
            @ApiResponse(responseCode = "400", description = "ID incorrecto", content = @Content),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada", content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Company> getById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getById(id));
    }

}

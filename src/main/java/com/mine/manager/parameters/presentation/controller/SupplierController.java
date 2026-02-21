package com.mine.manager.parameters.presentation.controller;

import com.mine.manager.parameters.domain.entity.Supplier;
import com.mine.manager.parameters.domain.service.Interfaces.SupplierService;
import com.mine.manager.parameters.presentation.request.dto.SupplierDto;
import com.mine.manager.parameters.presentation.request.filter.SupplierFilter;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import com.mine.manager.parameters.presentation.response.pojo.SupplierBasicInfoPojo;
import com.mine.manager.parameters.presentation.response.pojo.SupplierPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Supplier Service", description = "Módulo para gestionar los proveedores")
@AllArgsConstructor
@RestController
@RequestMapping("/suppliers")
public class SupplierController {


    private final SupplierService supplierService;


    @Operation(summary = "Listar todas los proveedores.")
    @GetMapping
    public ResponseEntity<List<Supplier>> getAll() {
        List<Supplier> suppliers = supplierService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(suppliers);
    }


    @Operation(summary = "Registrar nuevo proveedor.")
    @PostMapping
    public ResponseEntity<SupplierPojo> create(@Valid @RequestBody SupplierDto dto) {
        SupplierPojo supplierSaved = supplierService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierSaved);
    }


    @Operation(summary = "Actualizar proveedor por Id.")
    @PutMapping("/{id}")
    public ResponseEntity<SupplierPojo> update(@PathVariable Integer id,
                                           @Valid @RequestBody SupplierDto dto) {
        SupplierPojo supplierUpdated = supplierService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(supplierUpdated);
    }


    @Operation(summary = "Listar proveedor por Id.")
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getById(@PathVariable Integer id) {
        Supplier supplierFound = supplierService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(supplierFound);
    }


    @Operation(summary = "Eliminar proveedor por Id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Lista paginada de los proveedores aplicando filtros.")
    @GetMapping("/pageable")
    public ResponseEntity<PagePojo<SupplierPojo>> getByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String documentNumber,
            @RequestParam(required = false) String supplierGroup,
            @RequestParam(required = false) String some) {


        SupplierFilter filter = new SupplierFilter(name, surname,
                 documentNumber, address, supplierGroup, some);
        PagePojo<SupplierPojo> supplierPagePojoFiltered = supplierService.getByPageAndFilters(page, size,
                sortBy, sortOrder, filter);


        return ResponseEntity.status(HttpStatus.OK).body(supplierPagePojoFiltered);
    }


    @Operation(summary = "Listar proveedores aplicando filtros.")
    @GetMapping("/search")
    public ResponseEntity<List<Supplier>> getFiltered(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String documentNumber,
            @RequestParam(required = false) String supplierGroup,
            @RequestParam(required = false) String some) {
        SupplierFilter filter = new SupplierFilter(name, surname,
                documentNumber, address, supplierGroup, some);
        List<Supplier> supplierListFiltered = supplierService.getFiltered(filter);
        return ResponseEntity.status(HttpStatus.OK).body(supplierListFiltered);
    }

    @Operation(summary = "Listar nombre completo de los proveedores con su id y numero de documento según un termino de búsqueda.")
    @GetMapping("/select")
    public ResponseEntity<List<SupplierBasicInfoPojo>> getFilteredForSelect(
            @RequestParam(required = false) String some) {
        List<SupplierBasicInfoPojo> supplierListFiltered = supplierService.getFilteredForSelect(some);
        return ResponseEntity.status(HttpStatus.OK).body(supplierListFiltered);
    }

}




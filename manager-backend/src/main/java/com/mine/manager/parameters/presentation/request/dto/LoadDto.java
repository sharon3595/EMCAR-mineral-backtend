package com.mine.manager.parameters.presentation.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
        import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class LoadDto {

    @NotNull(message = "{load.supplierId.not-null}")
    @Schema(description = "ID del proveedor", example = "5")
    private Integer supplierId;

    @NotNull(message = "{load.lotId.not-null}")
    @Schema(description = "ID del lote al que pertenece", example = "20")
    private Integer lotId;

    @NotNull(message = "{load.materialId.not-null}")
    @Schema(description = "ID del mineral o material", example = "3")
    private Integer materialId;

    @NotNull(message = "{load.typeMaterialId.not-null}")
    @Schema(description = "ID del tipo de material", example = "2")
    private Integer typeMaterialId;

    @Schema(description = "ID de la mina (Opcional)", example = "1")
    private Integer mineId;

    @Schema(description = "ID de la cooperativa (Opcional)", example = "4")
    private Integer cooperativeId;

    @NotNull(message = "{load.date.not-null}")
    @PastOrPresent(message = "{load.date.past-or-present}")
    @Schema(description = "Fecha de la carga", example = "2023-11-01")
    private LocalDate date;

    @NotBlank(message = "{load.correlativeLotCode.not-blank}")
    @Size(max = 50, message = "{load.correlativeLotCode.size}")
    @Schema(description = "Código correlativo interno", example = "INT-001-A")
    private String correlativeLotCode;

    @PositiveOrZero(message = "{load.numberSacks.positive}")
    @Digits(integer = 15, fraction = 2, message = "{load.numberSacks.digits}")
    @Schema(description = "Cantidad de sacos", example = "50.50")
    private BigDecimal numberSacks = BigDecimal.ZERO;

    @NotNull(message = "{load.weight.not-null}")
    @Positive(message = "{load.weight.positive}")
    @Digits(integer = 15, fraction = 3, message = "{load.weight.digits}")
    @Schema(description = "Peso bruto de la carga", example = "1250.500")
    private BigDecimal weight;

    @Size(max = 500, message = "{load.observation.size}")
    @Schema(description = "Observaciones", example = "Material húmedo")
    private String observation;
}
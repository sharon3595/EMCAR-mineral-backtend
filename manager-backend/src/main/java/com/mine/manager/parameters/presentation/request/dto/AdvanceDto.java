package com.mine.manager.parameters.presentation.request.dto;

import com.mine.manager.common.enums.PaymentChanelEnum;
import com.mine.manager.common.enums.PaymentTypeEnum;
import com.mine.manager.common.enums.ReceiptTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AdvanceDto {

    public interface OnCreate {}

    @NotNull(message = "{advance.cargaId.not-null}", groups = OnCreate.class)
    @Schema(description = "ID de la Carga (Obligatorio al crear)", example = "105")
    private Integer loadId;

    @NotNull(message = "{load.lotId.not-null}")
    @Schema(description = "ID del lote al que pertenece", example = "20")
    private Integer lotId;

    @NotBlank(message = "{advance.receiptCode.not-blank}")
    @Size(max = 50, message = "{advance.receiptCode.size}")
    @Schema(description = "Número o código del recibo físico", example = "REC-00123")
    private String receiptCode;

    @NotNull(message = "{advance.receiptType.not-null}")
    @Schema(description = "Tipo de recibo", example = "MANUAL")
    private ReceiptTypeEnum receiptType;


    @NotNull(message = "{advance.date.not-null}")
    @PastOrPresent(message = "{advance.date.past-or-present}") // No puedes anticipar fechas futuras
    @Schema(description = "Fecha del anticipo", example = "2023-10-25")
    private LocalDate date;

    @NotNull(message = "{advance.amount.not-null}")
    @Positive(message = "{advance.amount.positive}")
    @Schema(description = "Monto del anticipo", example = "5000.00")
    private BigDecimal amount;


    @NotBlank(message = "{advance.concept.not-blank}")
    @Size(max = 500, message = "{advance.concept.size}")
    @Schema(description = "Razón o concepto del pago", example = "Pago adelanto por transporte de carga mineral")
    private String concept;

    @Size(max = 500, message = "{advance.observation.size}")
    @Schema(description = "Observaciones adicionales (Opcional)", example = "Entregado al chofer suplente")
    private String observation;


    @NotNull(message = "{advance.paymentType.not-null}")
    private PaymentTypeEnum paymentType;

    @NotNull(message = "{advance.paymentChanel.not-null}")
    private PaymentChanelEnum paymentChanel;

    @Size(max = 100, message = "{advance.checkNumber.size}")
    @Schema(description = "Número de cheque o comprobante de transferencia", example = "BNB-987654")
    private String checkNumber;

}


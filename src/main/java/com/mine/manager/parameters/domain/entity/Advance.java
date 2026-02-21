package com.mine.manager.parameters.domain.entity;

import com.mine.manager.common.enums.PaymentChanelEnum;
import com.mine.manager.common.enums.PaymentTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "advances", schema = "mine")
public class Advance extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "lot_id", nullable = false)
    private Lot lot;
    @Column(length = 50, nullable = false)
    private String receiptCode;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(length = 500)
    private String concept;
    @Column(length = 500)
    private String observation;
    @Column(length = 30, nullable = false)
    private PaymentTypeEnum paymentType;
    @Column(length = 100)
    private String checkNumber;
    @Column(length = 30, nullable = false)
    private PaymentChanelEnum paymentChanel;
    @ManyToOne
    @JoinColumn(name = "load_id", nullable = false)
    private Load load;
}

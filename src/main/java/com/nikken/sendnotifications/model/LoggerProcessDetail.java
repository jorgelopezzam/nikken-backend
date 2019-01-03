package com.nikken.sendnotifications.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="logprocdetalle")
@Getter
@Setter
@NoArgsConstructor
public class LoggerProcessDetail {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Integer idLogprocdetalle;

    @Column(name="numAsesor")
    private String numAsesor;

    @Column(name="correoAsesor")
    private String correoAsesor;

    @Column(name="numFactura")
    private String numFactura;

    @Column(name="numOrden")
    private String numOrden;

    @Column(name="guiaEstafeta")
    private String guiaEstafeta;

    @Column(name="estadoRegistro")
    private String estadoRegistro;

    @Column(name="motivoNoEnvio")
    private String motivoNoEnvio;

    @OneToOne(optional=false)
    @JoinColumn(name = "idLogprocesos")
    private LoggerProcess loggerProcess;
}

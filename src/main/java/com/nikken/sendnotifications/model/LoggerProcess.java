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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="logprocesos")
@Getter
@Setter
@NoArgsConstructor
public class LoggerProcess {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Integer idLogprocesos;

    @Column(name="fechaEjecucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecucion;

    @Column(name="fechaTerminado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTerminado;

    @Column(name="totalRegistros")
    private Integer totalRegistros;

    @Column(name="totalCorrectos")
    private Integer totalCorrectos;

    @Column(name="totalErrores")
    private Integer totalErrores;

    @Column(name="estadoProceso")
    private String estadoProceso;

    @Column(name="tipoProceso")
    private String tipoProceso;
}

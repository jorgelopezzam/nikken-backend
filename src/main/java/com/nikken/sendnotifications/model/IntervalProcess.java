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
@Table(name="intervalos")
@Getter
@Setter
@NoArgsConstructor
public class IntervalProcess {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Integer idIntervalos;

    @Column(name="horaInicial")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaInicial;

    @Column(name="horaFinal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaFinal;

    @Column(name="intervalo")
    private Integer intervalo;

    @OneToOne(optional=false)
    @JoinColumn(name = "idAdmin")
    private AdminProcess adminProcess;
}

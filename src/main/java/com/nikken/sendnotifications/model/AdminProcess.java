package com.nikken.sendnotifications.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "adminprocesos")
@Getter
@Setter
@NoArgsConstructor
public class AdminProcess {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer idAdmin;

    @Column(name="procesoAutomatico")
    private String procesoAutomatico;

    @Column(name="tipoEjecucion")
    private String tipoEjecucion;

    @Column(name="horaUno")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaUno;

    @Column(name="horaDos")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaDos;

    @Column(name="horaTres")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaTres;

    @Column(name="horaCuatro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaCuatro;

    @Column(name="horaCinco")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaCinco;

    @Column(name="intervalo")
    private String intervalo;

    @Column(name="horaInicial")
    private String horaInicial;

    @Column(name="horaFinal")
    private String horaFinal;

    @Column(name="copiaCarbon")
    private String copiaCarbon;

    @Column(name="procesoManual")
    private String procesoManual;

    @Column(name="fechaManual")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaManual;

    @Column(name="procesoPrueba")
    private String procesoPrueba;

    @Column(name="correosPrueba")
    private String correosPrueba;

    @Column(name="paisPrueba")
    private String paisPrueba;

    @Column(name="estadoProceso")
    private String estadoProceso;

    @Column(name="fechaModif")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;

    @Column(name="usuarioModif")
    private String usuarioModif;

    @PrePersist
    public void prePersist() {
        fechaModif = new Date();
        usuarioModif = "backend";
    }

    @PreUpdate
    public void preUpdate() {
        fechaModif = new Date();
        usuarioModif = "backend";
    }
}

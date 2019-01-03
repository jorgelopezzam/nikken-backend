package com.nikken.sendnotifications.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="horarios")
@Getter
@Setter
@NoArgsConstructor
public class SchedulerProcess {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Integer idHorarios;

    @Column(name="horario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horario;

    @OneToOne(optional=false)
    @JoinColumn(name = "idAdmin")
    private AdminProcess adminProcess;
}

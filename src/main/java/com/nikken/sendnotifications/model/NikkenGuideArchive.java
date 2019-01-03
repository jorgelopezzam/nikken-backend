package com.nikken.sendnotifications.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="nikken_data_summary_archive")
@Getter
@Setter
@NoArgsConstructor
public class NikkenGuideArchive {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Integer id;

    @Column(name="CardCode")
    private String cardCode;

    @Column(name="U_Orden")
    private String uOrden;

    @Column(name="U_Estafeta")
    private String uEstafeta;

    @Email
    @Column(name="E_Mail")
    private String email;

    @Column(name="Fecha_documento")
    private String fecDocumento;

    @Column(name="total_items")
    private Integer total;

    @Column(name="sendProcess")
    private String sendProcess;
}

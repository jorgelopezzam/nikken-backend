package com.nikken.sendnotifications.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="nikken_data")
@Getter
@Setter
@NoArgsConstructor
public class NikkenGuide {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Integer id;

    @Column(name="CardCode")
    private String cardCode;

    @Column(name="docNum")
    private String docNum;

    @Column(name="U_Orden")
    private String uOrden;

    @Column(name="nikken_user")
    private String nikkenUser;

    @Column(name="U_Estafeta")
    private String uEstafeta;

    @Column(name="ItemCode")
    private String itemCode;

    @Column(name="Description")
    private String description;

    @Column(name="Quantity")
    private String quantity;

    @Column(name="E_Mail")
    private String email;

    @Column(name="Fecha_documento")
    private String fecDocumento;
}

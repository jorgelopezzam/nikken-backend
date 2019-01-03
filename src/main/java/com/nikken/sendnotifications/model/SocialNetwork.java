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
@Table(name="publicidad")
@Getter
@Setter
@NoArgsConstructor
public class SocialNetwork {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Integer idPublicidad;

    @Column(name="paisPublic")
    private String paisPublic;

    @Column(name="urlFacebook")
    private String urlFacebook;

    @Column(name="urlInstagram")
    private String urlInstagram;

    @Column(name="urlTwitter")
    private String urlTwitter;

    @Column(name="urlYoutube")
    private String urlYoutube;
}

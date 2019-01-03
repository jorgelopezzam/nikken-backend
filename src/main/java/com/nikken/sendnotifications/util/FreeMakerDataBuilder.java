package com.nikken.sendnotifications.util;

import com.nikken.sendnotifications.model.NikkenGuide;
import com.nikken.sendnotifications.model.SocialNetwork;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class FreeMakerDataBuilder {
    private String orden;
    private List<NikkenGuide> references;
    private SocialNetwork socialNetwork;
    private String uEstafeta;
    private String resourceLink;
    private String nikkenUser;
}

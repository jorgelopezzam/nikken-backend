package com.nikken.sendnotifications.util;

import com.nikken.sendnotifications.exception.EstafetaException;

public class EstafetaValidator {

    public void validateEstafetaNumber(String estafetaGuide) {

        int word=0;

        for(int i=0;i<estafetaGuide.length();++i)
        {
            if(estafetaGuide.charAt(i)==' ')
                word++;
        }

        if (word > 2) {
            throw new EstafetaException("ERROR on Estafeta Guide");
        }

    }

}

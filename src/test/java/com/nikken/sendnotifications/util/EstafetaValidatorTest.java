package com.nikken.sendnotifications.util;

import com.nikken.sendnotifications.exception.EstafetaException;
import org.junit.Test;

public class EstafetaValidatorTest {

    private EstafetaValidator estafetaValidator;

    @Test(expected = EstafetaException.class)
    public void should_validate_not_valid_estafeta() {
        estafetaValidator = new EstafetaValidator();

        String wrongEstafeta = "NC 20626 FAVOR DE NO SURTIR; 405503264329B780140104";

        estafetaValidator.validateEstafetaNumber(wrongEstafeta);

    }

    @Test
    public void should_validate_correct_estafeta_format() {
        estafetaValidator = new EstafetaValidator();

        String correctEstafeta = "905503264329B780140593; 705503264329B780140594; 505503264329B780140595";

        estafetaValidator.validateEstafetaNumber(correctEstafeta);
    }

}

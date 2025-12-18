package com.suivie_academique.test_cours;

import com.suivi_academique.utils.PassWordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidPasswordTest {

    @Test
    public void TestPassword(){

        PassWordValidator passWordValidator = new PassWordValidator();
        Assertions.assertEquals(true, passWordValidator.isvalid("10000"));
    }
}

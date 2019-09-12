/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.podam;

import org.apache.commons.lang3.RandomStringUtils;
import uk.co.jemos.podam.common.AttributeStrategy;

/**
 *
 * @author Estudiante
 */
public class EmailStrategy implements AttributeStrategy<String> {

    @Override
    /**
     * Retorna un email válido.
     */
    public String getValue() {
        return RandomStringUtils.randomAlphabetic(8) + "@prueba.edu.co";
    }
}

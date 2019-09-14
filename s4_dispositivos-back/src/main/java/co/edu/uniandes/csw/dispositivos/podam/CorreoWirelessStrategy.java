/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.podam;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Random;
import uk.co.jemos.podam.common.AttributeStrategy;

/**
 * Strategy para generar correos corporativos
 * @author Dianis Caro
 */
public class CorreoWirelessStrategy implements AttributeStrategy<String>
{
    @Override
    public String getValue()
    {
        SecureRandom num = new SecureRandom();
        
        int numero = Math.abs(num.nextInt(7)+4);
        byte[] array = new byte[numero];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return (generatedString += "@wireless.com");
    }
}

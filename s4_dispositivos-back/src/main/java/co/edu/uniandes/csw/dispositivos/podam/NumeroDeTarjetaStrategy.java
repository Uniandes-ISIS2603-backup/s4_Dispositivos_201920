/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.podam;

import uk.co.jemos.podam.common.AttributeStrategy;

/**
 *Strategy para generar números de tarjetas
 * @author Dianis Caro
 */
public class NumeroDeTarjetaStrategy implements AttributeStrategy<String>
{
    @Override
    public String getValue()
    {
        String numTarjeta = "";
        for(int i=0;i<16;i++)
        {
             int numero = (int) (Math.random() * 9);
             numTarjeta += ""+numero;
        }
        return numTarjeta;
    }
}

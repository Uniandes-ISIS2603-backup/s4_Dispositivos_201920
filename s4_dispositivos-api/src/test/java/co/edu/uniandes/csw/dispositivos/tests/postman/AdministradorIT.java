/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.tests.postman;

import co.edu.uniandes.csw.dispositivos.dtos.AdministradorDTO;
import co.edu.uniandes.csw.dispositivos.dtos.CascaraDTO;
import co.edu.uniandes.csw.dispositivos.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.dispositivos.resources.AdministradorResource;
import co.edu.uniandes.csw.postman.tests.PostmanTestBuilder;
import co.edu.uniandes.csw.dispositivos.resources.RestConfig;
import java.io.File;
import java.io.IOException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Pruebas de integracion del recurso de Comprobante de pago.
 * @author Dianis Caro
 */
@RunWith(Arquillian.class)
public class AdministradorIT{

    private static final String COLLECTION = "Administrador_Tests.postman_collection";

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "s4_dispositivos-api.war")//War del modulo api
                // Se agrega las dependencias
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(RestConfig.class.getPackage()) //No importa cual recurso usar, lo importante es agregar el paquet
                .addPackage(CascaraDTO.class.getPackage()) //No importa cual dto usar, lo importante es agregar el paquete.
                .addPackage(BusinessLogicExceptionMapper.class.getPackage())
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"));
    }

    @Test
    @RunAsClient
    public void postman() throws IOException {
        PostmanTestBuilder tp = new PostmanTestBuilder();
        tp.setTestWithoutLogin(COLLECTION, "Entorno-Colecciones.postman_environment");
        String desiredResult = "0";
        System.out.println(tp.getIterations_failed());
        Assert.assertEquals("Error en Iterations de: " + COLLECTION, desiredResult, tp.getIterations_failed());

        Assert.assertEquals("Error en Requests de: " + COLLECTION, desiredResult, tp.getRequests_failed());

        Assert.assertEquals("Error en Test-Scripts de: " + COLLECTION, desiredResult, tp.getTest_scripts_failed());

        Assert.assertEquals("Error en Assertions de: " + COLLECTION, desiredResult, tp.getAssertions_failed());
    }
}

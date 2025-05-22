package org.example.parking;

import org.junit.Test;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class EstacionamientoTest {

    @Test
    public void testRetirarVehiculo() throws Exception {
        //TODO test
        Estacionamiento estacionamiento = new Estacionamiento();
        Vehiculo auto = new Vehiculo("ABC123", "ModeloAuto", Vehiculo.Tipo.AUTO);

        boolean ingresoExitoso = estacionamiento.ingresarVehiculo("12345678", "Juan", auto);
        assertTrue("El vehículo debería ingresar correctamente", ingresoExitoso);

        // Esperamos unos segundos para simular tiempo real (opcional)
        Thread.sleep(1000);

        Ticket ticket = estacionamiento.retirarVehiculo("ABC123");

        assertEquals("La patente del vehículo en el ticket debe coincidir",
                "ABC123", ticket.getVehiculo().getPatente());

        assertTrue("El importe debe ser mayor a cero", ticket.calcularPrecio() > 0);
        assertTrue("Debe registrarse la hora de salida", ticket.getHoraSalida() != null);
    }

    @Test
    public void testCalcularPrecio() throws Exception {
        Estacionamiento estacionamiento = new Estacionamiento();
        Vehiculo suv = new Vehiculo("XYZ789", "ModeloSUV", Vehiculo.Tipo.SUV);

        estacionamiento.ingresarVehiculo("98765432", "Maria", suv);

        Ticket ticket = estacionamiento.listarVehiculosEstacionados().get(0);

        Field fieldIngreso = Ticket.class.getDeclaredField("horaEntrada");
        fieldIngreso.setAccessible(true);
        fieldIngreso.set(ticket, LocalDateTime.now().minusHours(10));

        Field fieldSalida = Ticket.class.getDeclaredField("horaSalida");
        fieldSalida.setAccessible(true);
        fieldSalida.set(ticket, LocalDateTime.now());

        Ticket ticketFinal = estacionamiento.retirarVehiculo("XYZ789");

        double esperado = 10 * 130; // SUV tarifa por hora
        assertEquals("El importe debe ser igual a tarifa * horas", esperado, ticketFinal.calcularPrecio(), 0.1);
    }


}
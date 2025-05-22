package org.example.parking;

import junit.framework.TestCase;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
public class TicketTest extends TestCase {
    private Ticket ticket;
    private Vehiculo vehiculo;
    public void setUp() throws Exception {
        super.setUp();
        vehiculo = new Vehiculo("XYZ789", "ModeloSUV", Vehiculo.Tipo.SUV);
        Cliente cliente = new Cliente("98765432", "Maria");
        ticket = new Ticket(cliente, vehiculo);
    }

    public void tearDown() throws Exception {
        ticket = null;
        vehiculo = null;
    }

    public void testCalcularPrecio() throws Exception {
        // Simulamos que el ingreso fue hace 2 horas
        Field fieldIngreso = Ticket.class.getDeclaredField("horaEntrada");
        fieldIngreso.setAccessible(true);
        fieldIngreso.set(ticket, LocalDateTime.now().minusHours(2));

        // Marcar la salida para calcular el precio
        ticket.marcarSalida();

        // Calcular el precio esperado
        double esperado = 2 * 130; // SUV tarifa por hora

        // Verificar que el precio calculado es el esperado
        assertEquals("El importe debe ser igual a tarifa * horas", esperado, ticket.calcularPrecio(), 0.1);
    }

}
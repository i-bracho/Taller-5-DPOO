package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import uniandes.dpoo.hamburguesas.mundo.*;

public class PedidoTest {

    private Pedido pedido;
    private ProductoMenu hamburguesa;
    private ProductoMenu papas;

    @BeforeEach
    public void setUp() {
        pedido = new Pedido("Laura Mendez", "Calle 123");
        hamburguesa = new ProductoMenu("Hamburguesa Sencilla", 12000);
        papas = new ProductoMenu("Papas Medianas", 5000);
        pedido.agregarProducto(hamburguesa);
        pedido.agregarProducto(papas);
    }

    @Test
    public void testIdIncremental() {
        Pedido otroPedido = new Pedido("Otro", "Dir");
        assertTrue(otroPedido.getIdPedido() > pedido.getIdPedido(),
            "El id del nuevo pedido debe ser mayor que el anterior.");
    }

    @Test
    public void testCalculoPrecios() {
        int totalEsperado = 20230;
        assertEquals(totalEsperado, pedido.getPrecioTotalPedido(),
            "El precio total del pedido debería incluir IVA correctamente.");
    }
    
    @Test
    public void testGetNombre() {
        assertEquals("Laura Mendez", pedido.getNombreCliente(),
            "El nombre del cliente debe hacer parte de la unformación del pedido");
    }
      

    @Test
    public void testGenerarTextoFactura() {
        String factura = pedido.generarTextoFactura();
        assertTrue(factura.contains("Cliente: Laura Mendez"), "Debe incluir el nombre del cliente");
        assertTrue(factura.contains("Dirección: Calle 123"), "Debe incluir la dirección del cliente");
        assertTrue(factura.contains("Hamburguesa Sencilla"), "Debe listar los productos agregados");
        assertTrue(factura.contains("Papas Medianas"), "Debe listar todos los productos agregados");
        assertTrue(factura.contains("Precio Total:"), "Debe mostrar el precio total");
    }

    @Test
    public void testGuardarFactura() throws IOException {
        File temp = File.createTempFile("factura", ".txt");
        pedido.guardarFactura(temp);

        assertTrue(temp.exists(), "El archivo de factura debería haberse creado.");
        String contenido = new String(java.nio.file.Files.readAllBytes(temp.toPath()));

        assertTrue(contenido.contains("Cliente: Laura Mendez"), "El archivo debe contener la factura completa.");

        temp.deleteOnExit();
    }
}

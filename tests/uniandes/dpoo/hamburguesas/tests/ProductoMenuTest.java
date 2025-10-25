package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {

    private ProductoMenu producto;

    @BeforeEach
    public void setUp() {
        producto = new ProductoMenu("Hamburguesa Sencilla", 12000);
    }

    @Test
    public void testConstructorYGetters() {
        assertNotNull(producto, "El producto no debería ser nulo después de crearse.");
        assertEquals("Hamburguesa Sencilla", producto.getNombre(), "El nombre no es el esperado.");
        assertEquals(12000, producto.getPrecio(), "El precio no es el esperado.");
    }

    @Test
    public void testGenerarTextoFactura() {
        String esperado = "Hamburguesa Sencilla\n            12000\n";
        String actual = producto.generarTextoFactura();
        assertEquals(esperado, actual, "El texto de factura no coincide.");
    }
}
    

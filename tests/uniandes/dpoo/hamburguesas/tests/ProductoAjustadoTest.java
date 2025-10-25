package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import uniandes.dpoo.hamburguesas.mundo.*;

public class ProductoAjustadoTest {

    private ProductoMenu base;
    private ProductoAjustado ajustado;
    private Ingrediente queso;
    private Ingrediente cebolla;

    @BeforeEach
    public void setUp() {
        base = new ProductoMenu("Hamburguesa Sencilla", 12000);
        ajustado = new ProductoAjustado(base);

        queso = new Ingrediente("Queso", 2000);
        cebolla = new Ingrediente("Cebolla", 500);
    }

    @Test
    public void testGetNombre() {
        assertEquals("Hamburguesa Sencilla", ajustado.getNombre(), 
            "El nombre debe ser igual al del producto base");
    }

    @Test
    public void testGetPrecio() {
        ajustado.agregados.add(queso);
        ajustado.agregados.add(cebolla);

        assertEquals(14500, ajustado.getPrecio(),
            "El precio debería ser la suma de los ingredientes agregados y la base");
    }


    @Test
    public void testGenerarTextoFactura() {
    	ajustado.agregados.add(queso);
    	ajustado.eliminados.add(cebolla);

        String factura = ajustado.generarTextoFactura();
        assertTrue(factura.contains("Queso"), "Debe listar el ingrediente agregado");
        assertTrue(factura.contains("+Queso"), "Debe mostrar el '+' para ingredientes agregados");
        assertTrue(factura.contains("-Cebolla"), "Debe mostrar el '-' para ingredientes eliminados");
        assertTrue(factura.contains("14000"), 
            "Debe incluir el precio");
    }
}

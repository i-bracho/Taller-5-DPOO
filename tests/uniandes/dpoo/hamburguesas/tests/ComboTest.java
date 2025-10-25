package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import uniandes.dpoo.hamburguesas.mundo.*;

public class ComboTest {

    private ProductoMenu hamburguesa;
    private ProductoMenu papas;
    private ArrayList<ProductoMenu> items;
    private Combo combo;

    @BeforeEach
    public void setUp() {
        hamburguesa = new ProductoMenu("Hamburguesa Sencilla", 12000);
        papas = new ProductoMenu("Papas Medianas", 5000);

        items = new ArrayList<>();
        items.add(hamburguesa);
        items.add(papas);

        combo = new Combo("Combo Especial", 0.1, items); 
    }

    @Test
    public void testGetNombre() {
        assertEquals("Combo Especial", combo.getNombre(), "El nombre del combo no coincide");
    }

    @Test
    public void testGetPrecio() {
        int precioEsperado = (int)((12000 + 5000) * (1 - 0.1)); 
        int precioActual = combo.getPrecio();
        assertEquals(precioEsperado, precioActual,
            "El precio debería incluir el descuento");
    }

    @Test
    public void testGenerarTextoFactura() {
        String factura = combo.generarTextoFactura();

        assertTrue(factura.contains("Combo Especial"), "Debe contener el nombre del combo");
        assertTrue(factura.contains("Descuento"), "Debe mencionar el descuento");
        assertTrue(factura.contains(String.valueOf(combo.getPrecio())), 
            "Debe incluir el precio calculado del combo");
    }
}


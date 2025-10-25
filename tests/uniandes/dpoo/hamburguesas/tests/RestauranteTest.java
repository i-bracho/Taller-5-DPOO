package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import uniandes.dpoo.hamburguesas.mundo.*;
import uniandes.dpoo.hamburguesas.excepciones.*;

public class RestauranteTest {

    private Restaurante restaurante;
    private File ingredientesFile;
    private File menuFile;
    private File combosFile;

    @BeforeEach
    public void setUp() throws Exception {
        restaurante = new Restaurante();
        ingredientesFile = File.createTempFile("ingredientes", ".txt");
        try (PrintWriter pw = new PrintWriter(ingredientesFile)) {
            pw.println("Queso;2000");
            pw.println("Lechuga;500");
        }

        menuFile = File.createTempFile("menu", ".txt");
        try (PrintWriter pw = new PrintWriter(menuFile)) {
            pw.println("Hamburguesa Sencilla;12000");
            pw.println("Papas Medianas;5000");
        }

        combosFile = File.createTempFile("combos", ".txt");
        try (PrintWriter pw = new PrintWriter(combosFile)) {
            pw.println("Combo 1;10%;Hamburguesa Sencilla;Papas Medianas");
        }
    }

    @Test
    public void testCargarInformacionRestaurante() throws Exception {
        restaurante.cargarInformacionRestaurante(ingredientesFile, menuFile, combosFile);

        assertEquals(2, restaurante.getIngredientes().size(), "Debe cargar 2 ingredientes.");
        assertEquals(2, restaurante.getMenuBase().size(), "Debe cargar 2 productos base.");
        assertEquals(1, restaurante.getMenuCombos().size(), "Debe cargar 1 combo.");
        assertEquals("Queso", restaurante.getIngredientes().get(0).getNombre());
        assertEquals("Hamburguesa Sencilla", restaurante.getMenuBase().get(0).getNombre());
    }

    @Test
    public void testCargarIngredientesRepetidos() throws Exception {
        File f = File.createTempFile("ingredientes_rep", ".txt");
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("Queso;2000");
            pw.println("Queso;1500");
        }

        assertThrows(IngredienteRepetidoException.class, () -> {
            restaurante.cargarInformacionRestaurante(f, menuFile, combosFile);
        });
    }

    @Test
    public void testCargarMenuRepetido() throws Exception {
        File f = File.createTempFile("menu_rep", ".txt");
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("Hamburguesa Sencilla;12000");
            pw.println("Hamburguesa Sencilla;13000");
        }

        assertThrows(ProductoRepetidoException.class, () -> {
            restaurante.cargarInformacionRestaurante(ingredientesFile, f, combosFile);
        });
    }

    @Test
    public void testCargarComboProductoFaltante() throws Exception {
        File f = File.createTempFile("combos_faltantes", ".txt");
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("Combo 2;5%;ProductoInexistente");
        }

        assertThrows(ProductoFaltanteException.class, () -> {
            restaurante.cargarInformacionRestaurante(ingredientesFile, menuFile, f);
        });
    }

    @Test
    public void testIniciarYCerrarPedido() throws Exception {
        restaurante.iniciarPedido("Laura", "Calle 123");
        assertNotNull(restaurante.getPedidoEnCurso(), "Debe haber un pedido en curso.");

        File facturasDir = new File("./facturas/");
        if (!facturasDir.exists()) facturasDir.mkdirs();

        restaurante.cerrarYGuardarPedido();

        assertNull(restaurante.getPedidoEnCurso(), "El pedido en curso debe haberse cerrado.");
    }

    @Test
    public void testIniciarPedidoYaExistente() throws Exception {
        restaurante.iniciarPedido("Laura", "Calle 123");
        assertThrows(YaHayUnPedidoEnCursoException.class, () -> {
            restaurante.iniciarPedido("Pedro", "Carrera 5");
        });
    }

    @Test
    public void testCerrarPedidoSinIniciar() {
        assertThrows(NoHayPedidoEnCursoException.class, () -> {
            restaurante.cerrarYGuardarPedido();
        });
    }
    @Test
    public void testGettersIniciales() {
        assertNotNull(restaurante.getPedidos());
        assertNotNull(restaurante.getIngredientes());
        assertNotNull(restaurante.getMenuBase());
        assertNotNull(restaurante.getMenuCombos());
    }

    @AfterEach
    public void tearDown() {
        ingredientesFile.delete();
        menuFile.delete();
        combosFile.delete();
    }
}


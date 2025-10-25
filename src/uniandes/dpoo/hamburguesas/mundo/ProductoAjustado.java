package uniandes.dpoo.hamburguesas.mundo;

import java.util.ArrayList;

public class ProductoAjustado implements Producto
{
    private ProductoMenu productoBase;
    public ArrayList<Ingrediente> agregados;
    public ArrayList<Ingrediente> eliminados;

    public ProductoAjustado(ProductoMenu productoBase)
    {
        this.productoBase = productoBase;
        agregados = new ArrayList<>();
        eliminados = new ArrayList<>();
    }

    @Override
    public String getNombre()
    {
        return productoBase.getNombre();
    }

    @Override
    public int getPrecio()
    {
        int precio = productoBase.getPrecio();
        for (Ingrediente ingrediente : agregados)
        {
            precio += ingrediente.getCostoAdicional();
        }
        return precio;
    }

    @Override
    public String generarTextoFactura()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(productoBase.getNombre()).append("\n");

        for (Ingrediente ing : agregados)
        {
            sb.append("    +" + ing.getNombre());
            sb.append("                " + ing.getCostoAdicional() + "\n");
        }

        for (Ingrediente ing : eliminados)
        {
            sb.append("    -" + ing.getNombre() + "\n");
        }

        sb.append("            " + getPrecio() + "\n");

        return sb.toString();
    }
}




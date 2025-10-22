package crudpoo.service;

import crudpoo.model.Articulo;
import crudpoo.model.Categoria;
import crudpoo.model.Producto;
import crudpoo.model.Servicio;

import java.io.*;
import java.util.ArrayList;

public class ArchivoProductosHelper {

    private static final String RUTA_ARCHIVO = "src/crudpoo/data/productos.txt";

    public static void guardarProductos(ArrayList<Producto> productos) {
        try {
            File archivo = new File(RUTA_ARCHIVO);
            archivo.getParentFile().mkdirs();

            try (FileWriter fw = new FileWriter(archivo)) {
                for (Producto p : productos) {
                    if (p instanceof Articulo a) {
                        int idCat = (a.getCategoria() != null) ? a.getCategoria().getId() : 0;
                        fw.write("Articulo;" + a.getId() + ";" + a.getNombre() + ";" + a.getPrecio() + ";" + idCat + "\n");
                    } else if (p instanceof Servicio s) {
                        fw.write("Servicio;" + s.getId() + ";" + s.getNombre() + ";" + s.getPrecio() + ";" + s.getDuracionHoras() + "\n");
                    }
                }
            }

            System.out.println("Productos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }

    public static ArrayList<Producto> cargarProductos(ArrayList<Categoria> categorias) {
        ArrayList<Producto> productos = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("(No hay archivo de productos, se iniciará vacío)");
            return productos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length < 4) continue;

                String tipo = partes[0];
                int id = Integer.parseInt(partes[1]);
                String nombre = partes[2];
                double precio = Double.parseDouble(partes[3]);

                if (tipo.equals("Articulo")) {
                    int idCat = (partes.length >= 5) ? Integer.parseInt(partes[4]) : 0;
                    Categoria cat = null;
                    for (Categoria c : categorias) {
                        if (c.getId() == idCat) {
                            cat = c;
                            break;
                        }
                    }
                    Articulo a = new Articulo(nombre, precio, cat);
                    a.setId(id);
                    productos.add(a);
                } else if (tipo.equals("Servicio")) {
                    int duracion = (partes.length >= 5) ? Integer.parseInt(partes[4]) : 0;
                    Servicio s = new Servicio(nombre, precio, duracion);
                    s.setId(id);
                    productos.add(s);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
        }

        return productos;
    }
}

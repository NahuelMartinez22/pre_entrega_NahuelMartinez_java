package crudpoo.service;

import crudpoo.model.Categoria;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoCategoriasHelper {

    private static final String RUTA_ARCHIVO = "src/crudpoo/data/categorias.txt";

    public static void guardarCategorias(List<Categoria> categorias) {
        try {
            File archivo = new File(RUTA_ARCHIVO);
            archivo.getParentFile().mkdirs();

            try (FileWriter fw = new FileWriter(archivo)) {
                for (Categoria c : categorias) {
                    fw.write(c.getId() + ";" + c.getNombre() + "\n");
                }
            }

            System.out.println("Categorías guardadas correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar categorías: " + e.getMessage());
        }
    }

    public static ArrayList<Categoria> cargarCategorias() {
        ArrayList<Categoria> categorias = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("No hay archivo de categorías, se iniciará vacío");
            return categorias;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length < 2) continue;

                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];

                Categoria c = new Categoria(nombre);
                c.setId(id);

                categorias.add(c);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar categorías: " + e.getMessage());
        }

        return categorias;
    }
}

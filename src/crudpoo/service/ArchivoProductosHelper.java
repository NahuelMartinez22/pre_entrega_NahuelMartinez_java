package crudpoo.service;

import crudpoo.model.*;
import crudpoo.model.Componentes.PlacaDeVideo;
import crudpoo.model.Componentes.Procesador;

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

                    if (p instanceof PlacaDeVideo placa) {
                        fw.write("PlacaDeVideo;" +
                                p.getId() + ";" +
                                p.getNombre() + ";" +
                                p.getPrecio() + ";" +
                                (placa.getCategoria() != null ? placa.getCategoria().getId() : 0) + ";" +
                                placa.getVram() + ";" +
                                placa.getTipoMemoria() + ";" +
                                placa.getVelocidadMemoria() + ";" +
                                placa.getStock() + "\n");
                    }

                    else if (p instanceof Procesador cpu) {
                        fw.write("Procesador;" +
                                p.getId() + ";" +
                                p.getNombre() + ";" +
                                p.getPrecio() + ";" +
                                (cpu.getCategoria() != null ? cpu.getCategoria().getId() : 0) + ";" +
                                cpu.getNucleos() + ";" +
                                cpu.getHilos() + ";" +
                                cpu.getFrecuenciaBase() + ";" +
                                cpu.getFrecuenciaTurbo() + ";" +
                                cpu.getStock() + "\n");
                    }

                    else if (p instanceof Servicio s) {
                        fw.write("Servicio;" +
                                p.getId() + ";" +
                                p.getNombre() + ";" +
                                p.getPrecio() + ";" +
                                s.getDuracionHoras() + "\n");
                    }

                    else if (p instanceof Articulo a) {
                        fw.write("Articulo;" +
                                p.getId() + ";" +
                                p.getNombre() + ";" +
                                p.getPrecio() + ";" +
                                (a.getCategoria() != null ? a.getCategoria().getId() : 0) + ";" +
                                a.getStock() + "\n");
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
            System.out.println("No hay archivo de productos, se iniciará vacío.");
            return productos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length < 5) continue;

                String tipo = partes[0];
                String nombre = partes[2];
                double precio = Double.parseDouble(partes[3]);
                int idCat = Integer.parseInt(partes[4]);

                Categoria categoria = null;
                for (Categoria c : categorias) {
                    if (c.getId() == idCat) {
                        categoria = c;
                        break;
                    }
                }

                Producto producto;

                switch (tipo.toLowerCase()) {
                    case "placadevideo": {
                        int vram = (partes.length > 5) ? Integer.parseInt(partes[5]) : 0;
                        String tipoMemoria = (partes.length > 6) ? partes[6] : "Desconocido";
                        double velocidadMemoria = (partes.length > 7) ? Double.parseDouble(partes[7]) : 0;
                        int stock = (partes.length > 8) ? Integer.parseInt(partes[8]) : 0;
                        producto = new PlacaDeVideo(nombre, precio, categoria, vram, tipoMemoria, velocidadMemoria, stock);
                        break;
                    }
                    case "procesador": {
                        int nucleos = (partes.length > 5) ? Integer.parseInt(partes[5]) : 0;
                        int hilos = (partes.length > 6) ? Integer.parseInt(partes[6]) : 0;
                        double frecuenciaBase = (partes.length > 7) ? Double.parseDouble(partes[7]) : 0;
                        double frecuenciaTurbo = (partes.length > 8) ? Double.parseDouble(partes[8]) : 0;
                        int stock = (partes.length > 9) ? Integer.parseInt(partes[9]) : 0;
                        producto = new Procesador(nombre, precio, categoria, nucleos, hilos, frecuenciaBase, frecuenciaTurbo, stock);
                        break;
                    }
                    case "servicio": {
                        int duracionHoras = (partes.length > 5) ? Integer.parseInt(partes[5]) : 0;
                        producto = new Servicio(nombre, precio, duracionHoras);
                        break;
                    }
                    default: {
                        int stock = (partes.length > 5) ? Integer.parseInt(partes[5]) : 0;
                        producto = new Articulo(nombre, precio, categoria, stock);
                        break;
                    }
                }

                productos.add(producto);
            }

            System.out.println("Productos cargados correctamente (" + productos.size() + ").");

        } catch (IOException e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
        }

        return productos;
    }
}

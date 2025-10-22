package crudpoo.service;

import crudpoo.model.Articulo;
import crudpoo.model.Categoria;
import crudpoo.model.Componentes.PlacaDeVideo;
import crudpoo.model.Componentes.Procesador;
import crudpoo.model.Producto;
import crudpoo.model.Servicio;

import java.util.ArrayList;

public class CrudProductos extends CrudConsola<Producto> {

    private final ArrayList<Producto> productos;
    private final ArrayList<Categoria> categorias;

    public CrudProductos(ArrayList<Producto> productos, ArrayList<Categoria> categorias) {
        this.productos = productos;
        this.categorias = categorias;
    }

    @Override
    public void mostrarOpciones() {
        System.out.println("\n=== Menú Productos ===");
        System.out.println("1) Crear");
        System.out.println("2) Listar");
        System.out.println("3) Actualizar");
        System.out.println("4) Eliminar");
        System.out.println("0) Volver");
    }

    @Override
    public void crear() {
        System.out.println("\n=== Crear producto ===");

        System.out.println("¿Qué tipo de producto querés crear?");
        System.out.println("1) Artículo");
        System.out.println("2) Servicio");
        int tipo = leerEntero("Elegí una opción: ");

        if (tipo == 2) {
            String nombre = leerTextoNoVacio("Nombre del servicio: ");
            double precio = leerDoublePositivo("Precio: ");
            Servicio nuevoServicio = new Servicio(nombre, precio, 0);
            productos.add(nuevoServicio);
            ArchivoProductosHelper.guardarProductos(productos);
            System.out.println("Servicio creado: " + nuevoServicio);
            return;
        }

        if (tipo != 1) {
            System.out.println("Tipo inválido.");
            return;
        }

        System.out.println("¿Qué tipo de artículo querés crear?");
        System.out.println("1) Placa de video");
        System.out.println("2) Procesador");
        int opcion = leerEntero("Elegí una opción: ");

        String nombre = leerTextoNoVacio("Nombre del artículo: ");
        double precio = leerDoublePositivo("Precio: ");

        Producto nuevoProducto = switch (opcion) {
            case 1 -> {
                Categoria categoria = new Categoria(1, "Placa de video");
                int vram = leerEnteroPositivo("VRAM (GB): ");
                String tipoMemoria = leerTextoNoVacio("Tipo de memoria (ej: GDDR6): ");
                double velocidadMemoria = leerDoublePositivo("Velocidad de memoria (MHz): ");
                yield new PlacaDeVideo(nombre, precio, categoria, vram, tipoMemoria, velocidadMemoria);
            }
            case 2 -> {
                Categoria categoria = new Categoria(2, "Procesador");
                int nucleos = leerEnteroPositivo("Cantidad de núcleos: ");
                int hilos = leerEnteroPositivo("Cantidad de hilos: ");
                double frecuenciaBase = leerDoublePositivo("Frecuencia base (GHz): ");
                double frecuenciaTurbo = leerDoublePositivo("Frecuencia turbo (GHz): ");
                yield new Procesador(nombre, precio, categoria, nucleos, hilos, frecuenciaBase, frecuenciaTurbo);
            }
            default -> {
                System.out.println("Opción inválida.");
                yield null;
            }
        };

        if (nuevoProducto == null) return;

        productos.add(nuevoProducto);
        ArchivoProductosHelper.guardarProductos(productos);
        System.out.println("Producto creado correctamente: " + nuevoProducto);
    }

    @Override
    public void listar() {
        if (productos.isEmpty()) {
            System.out.println("(sin productos)");
            return;
        }

        System.out.println("\n=== Lista de productos ===");
        System.out.printf("%-5s %-20s %-18s %-10s %-70s%n",
                "ID", "Nombre", "Categoría", "Precio", "Detalles");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        for (Producto p : productos) {
            String nombre = p.getNombre();
            String categoria = "";
            String detalles = "";

            if (p instanceof Articulo articulo) {
                categoria = (articulo.getCategoria() != null)
                        ? articulo.getCategoria().getNombre()
                        : "(sin categoría)";

                if (p instanceof PlacaDeVideo placa) {
                    detalles = String.format("VRAM: %dGB | Tipo de memoria: %s | Velocidad de memoria: %.0f MHz",
                            placa.getVram(), placa.getTipoMemoria(), placa.getVelocidadMemoria());
                }
                else if (p instanceof Procesador cpu) {
                    detalles = String.format("Núcleos: %d | Hilos: %d | Frecuencia base: %.2f GHz | Frecuencia turbo: %.2f GHz",
                            cpu.getNucleos(), cpu.getHilos(), cpu.getFrecuenciaBase(), cpu.getFrecuenciaTurbo());
                }
                else {
                    detalles = "(Artículo genérico)";
                }

            } else if (p instanceof Servicio) {
                categoria = "Servicio";
                detalles = "(Servicio sin atributos adicionales)";
            }

            System.out.printf("%-5d %-20s %-18s $%-9.2f %-70s%n",
                    p.getId(), nombre, categoria, p.getPrecio(), detalles);
        }
    }

    @Override
    public void actualizar() {
        System.out.println("\n=== Lista de productos ===");
        listar();

        int id = leerEntero("\nId del producto que querés actualizar: ");

        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);

            if (p.getId() == id) {
                System.out.println("\n----------------------------------------");
                System.out.println("Actualizar producto");
                System.out.println("----------------------------------------");
                System.out.println("Producto actual: " + p.getNombre() + " | Precio actual: $" + p.getPrecio());
                System.out.println("Si dejás un campo vacío, se mantendrá el valor actual.\n");

                String nuevoNombre = leerTextoOpcional("Nuevo nombre", p.getNombre());
                double nuevoPrecio = leerDoubleOpcional("Nuevo precio", p.getPrecio());

                Categoria categoriaActual = null;
                if (p instanceof Articulo articulo) {
                    categoriaActual = articulo.getCategoria();
                    System.out.println("\nCategoría actual: " +
                            (categoriaActual != null ? categoriaActual.getNombre() : "(sin categoría)"));
                }

                System.out.println("\n¿Querés cambiar la categoría?");
                System.out.println("1) Mantener actual");
                System.out.println("2) Placa de video");
                System.out.println("3) Procesador");
                System.out.println("4) Servicio");
                int opCat = leerEntero("Elegí una opción: ");

                Producto nuevoProducto = p;

                switch (opCat) {
                    case 1 -> {
                        p.setNombre(nuevoNombre);
                        p.setPrecio(nuevoPrecio);

                        if (p instanceof PlacaDeVideo placa) {
                            System.out.println("\nEditar atributos de Placa de video");
                            System.out.println("Dejá vacío un campo para mantener su valor actual.\n");

                            int nuevaVram = leerEnteroOpcional("Nueva VRAM (GB)", placa.getVram());
                            String nuevoTipoMemoria = leerTextoOpcional("Nuevo tipo de memoria", placa.getTipoMemoria());
                            double nuevaVelocidad = leerDoubleOpcional("Nueva velocidad de memoria (MHz)", placa.getVelocidadMemoria());

                            placa.setVram(nuevaVram);
                            placa.setTipoMemoria(nuevoTipoMemoria);
                            placa.setVelocidadMemoria(nuevaVelocidad);

                            System.out.println("Placa de video actualizada correctamente.");
                        } else if (p instanceof Procesador cpu) {
                            System.out.println("\nEditar atributos de Procesador");
                            System.out.println("Dejá vacío un campo para mantener su valor actual.\n");

                            int nuevosNucleos = leerEnteroOpcional("Nuevos núcleos", cpu.getNucleos());
                            int nuevosHilos = leerEnteroOpcional("Nuevos hilos", cpu.getHilos());
                            double nuevaFrecuenciaBase = leerDoubleOpcional("Nueva frecuencia base (GHz)", cpu.getFrecuenciaBase());
                            double nuevaFrecuenciaTurbo = leerDoubleOpcional("Nueva frecuencia turbo (GHz)", cpu.getFrecuenciaTurbo());

                            cpu.setNucleos(nuevosNucleos);
                            cpu.setHilos(nuevosHilos);
                            cpu.setFrecuenciaBase(nuevaFrecuenciaBase);
                            cpu.setFrecuenciaTurbo(nuevaFrecuenciaTurbo);

                            System.out.println("Procesador actualizado correctamente.");
                        } else if (p instanceof Servicio) {
                            System.out.println("Servicio actualizado correctamente.");
                        }
                    }
                    case 2 -> {
                        Categoria cat = new Categoria(1, "Placa de video");
                        int vram = leerEnteroPositivo("VRAM (GB): ");
                        String tipoMemoria = leerTextoNoVacio("Tipo de memoria (ej: GDDR6): ");
                        double velocidad = leerDoublePositivo("Velocidad de memoria (MHz): ");
                        nuevoProducto = new PlacaDeVideo(nuevoNombre, nuevoPrecio, cat, vram, tipoMemoria, velocidad);
                    }
                    case 3 -> {
                        Categoria cat = new Categoria(2, "Procesador");
                        int nucleos = leerEnteroPositivo("Cantidad de núcleos: ");
                        int hilos = leerEnteroPositivo("Cantidad de hilos: ");
                        double frecuenciaBase = leerDoublePositivo("Frecuencia base (GHz): ");
                        double frecuenciaTurbo = leerDoublePositivo("Frecuencia turbo (GHz): ");
                        nuevoProducto = new Procesador(nuevoNombre, nuevoPrecio, cat, nucleos, hilos, frecuenciaBase, frecuenciaTurbo);
                    }
                    case 4 -> nuevoProducto = new Servicio(nuevoNombre, nuevoPrecio, 0);
                    default -> {
                        if (opCat < 1 || opCat > 4)
                            System.out.println("Opción inválida, se mantendrá el tipo original.");
                    }
                }

                productos.set(i, nuevoProducto);
                ArchivoProductosHelper.guardarProductos(productos);
                System.out.println("Cambios guardados correctamente.");
                return;
            }
        }

        System.out.println("No se encontró producto con id " + id);
    }


    @Override
    public void eliminar() {
        System.out.println("\n=== Lista de productos ===");
        listar();

        int id = leerEntero("\nIngrese el ID del producto a eliminar: ");

        Producto productoAEliminar = productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (productoAEliminar == null) {
            System.out.println("No se encontró ningún producto con ese ID.");
            return;
        }

        System.out.printf("Seleccionó: [ID: %d] %s%n", productoAEliminar.getId(), productoAEliminar.getNombre());
        String confirmacion = leerTexto("¿Está seguro que desea eliminar este producto? (s/n): ").toLowerCase();

        if (confirmacion.equals("s")) {
            productos.remove(productoAEliminar);
            ArchivoProductosHelper.guardarProductos(productos);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Operación cancelada. No se eliminó ningún producto.");
        }
    }

    public void menu() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = leerEntero("Elegí una opción: ");

            switch (opcion) {
                case 1 -> crear();
                case 2 -> listar();
                case 3 -> actualizar();
                case 4 -> eliminar();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
}

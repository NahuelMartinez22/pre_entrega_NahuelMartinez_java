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
        System.out.println("\n=== Menú de productos ===");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Actualizar producto");
        System.out.println("4) Eliminar producto");
        System.out.println("0) Volver al menú principal");
        System.out.print("Opción: ");
    }

    @Override
    public void agregar() {
        System.out.println("\n=== Agregar producto ===");

        System.out.println("¿Qué tipo de producto querés agregar?");
        System.out.println("1) Artículo");
        System.out.println("2) Servicio");
        int tipo = leerEntero("Elegí una opción: ");

        if (tipo == 2) {
            String nombre = leerTextoNoVacio("Nombre del servicio: ");
            double precio = leerDoublePositivo("Precio: ");
            int duracionHoras = leerEnteroPositivo("Duración del servicio (horas): ");

            Servicio nuevoServicio = new Servicio(nombre, precio, duracionHoras);
            productos.add(nuevoServicio);
            ArchivoProductosHelper.guardarProductos(productos);
            System.out.println("Servicio agregado: " + nuevoServicio);
            return;
        }

        if (tipo != 1) {
            System.out.println("Tipo inválido.");
            return;
        }

        System.out.println("¿Qué tipo de artículo querés agregar?");
        System.out.println("1) Placa de video");
        System.out.println("2) Procesador");
        int opcion = leerEntero("Elegí una opción: ");

        String nombre = leerTextoNoVacio("Nombre del artículo: ");
        double precio = leerDoublePositivo("Precio: ");
        int stock = leerEnteroPositivo("Cantidad en stock: ");

        Producto nuevoProducto = switch (opcion) {
            case 1 -> {
                Categoria categoria = new Categoria(1, "Placa de video");
                int vram = leerEnteroPositivo("VRAM (GB): ");
                String tipoMemoria = leerTextoNoVacio("Tipo de memoria (ej: GDDR6): ");
                double velocidadMemoria = leerDoublePositivo("Velocidad de memoria (MHz): ");
                yield new PlacaDeVideo(nombre, precio, categoria, vram, tipoMemoria, velocidadMemoria, stock);
            }
            case 2 -> {
                Categoria categoria = new Categoria(2, "Procesador");
                int nucleos = leerEnteroPositivo("Cantidad de núcleos: ");
                int hilos = leerEnteroPositivo("Cantidad de hilos: ");
                double frecuenciaBase = leerDoublePositivo("Frecuencia base (GHz): ");
                double frecuenciaTurbo = leerDoublePositivo("Frecuencia turbo (GHz): ");
                yield new Procesador(nombre, precio, categoria, nucleos, hilos, frecuenciaBase, frecuenciaTurbo, stock);
            }
            default -> {
                System.out.println("Opción inválida.");
                yield null;
            }
        };

        if (nuevoProducto == null) return;

        productos.add(nuevoProducto);
        ArchivoProductosHelper.guardarProductos(productos);
        System.out.println("Producto agregado correctamente: " + nuevoProducto);
    }


    @Override
    public void listar() {
        if (productos.isEmpty()) {
            System.out.println("(sin productos)");
            return;
        }

        System.out.println("\n=== Lista de productos ===");
        System.out.printf("%-5s %-25s %-18s %-10s %-8s %-70s%n",
                "ID", "Nombre", "Categoría", "Precio", "Stock", "Detalles");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        for (Producto p : productos) {
            String categoria = "";
            String detalles = "";
            String stock = "-";

            if (p instanceof Articulo articulo) {
                categoria = articulo.getCategoria() != null ? articulo.getCategoria().getNombre() : "(sin categoría)";
                stock = String.valueOf(articulo.getStock());

                if (articulo instanceof crudpoo.model.Componentes.PlacaDeVideo placa) {
                    detalles = String.format("VRAM: %dGB | Tipo de memoria: %s | Velocidad de memoria: %.0f MHz",
                            placa.getVram(), placa.getTipoMemoria(), placa.getVelocidadMemoria());
                } else if (articulo instanceof crudpoo.model.Componentes.Procesador cpu) {
                    detalles = String.format("Núcleos: %d | Hilos: %d | Frecuencia base: %.2f GHz | Frecuencia turbo: %.2f GHz",
                            cpu.getNucleos(), cpu.getHilos(), cpu.getFrecuenciaBase(), cpu.getFrecuenciaTurbo());
                } else {
                    detalles = "(Artículo genérico)";
                }
            }
            else if (p instanceof Servicio s) {
                categoria = "Servicio";
                detalles = String.format("Duración: %d horas", s.getDuracionHoras());
            }

            System.out.printf("%-5d %-25s %-18s $%-9.2f %-8s %-70s%n",
                    p.getId(), p.getNombre(), categoria, p.getPrecio(), stock, detalles);
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
                System.out.println("Producto actual: " + p);
                System.out.println("----------------------------------------");

                System.out.println("1) Actualizar todo");
                System.out.println("2) Actualizar solo stock");
                System.out.println("3) Volver");
                int opcion = leerEntero("Elegí una opción: ");

                if (opcion == 3) {
                    System.out.println("Operación cancelada.");
                    return;
                }

                if (opcion == 2) {
                    if (p instanceof Articulo articulo) {
                        System.out.println("Stock actual: " + articulo.getStock());
                        String entrada = leerTexto("Nuevo stock (Enter para mantener): ").trim();

                        int nuevoStock;
                        if (entrada.isEmpty()) {
                            nuevoStock = articulo.getStock();
                        } else {
                            try {
                                nuevoStock = Integer.parseInt(entrada);
                                if (nuevoStock < 0) {
                                    System.out.println("El stock no puede ser negativo.");
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Debe ser un número entero válido.");
                                return;
                            }
                        }

                        articulo.setStock(nuevoStock);
                        productos.set(i, articulo);
                        ArchivoProductosHelper.guardarProductos(productos);
                        System.out.println("Stock actualizado correctamente. Nuevo stock: " + articulo.getStock());
                    } else {
                        System.out.println("Este tipo de producto no tiene stock.");
                    }
                    return;
                }

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
                            int nuevoStock = leerEnteroOpcional("Nuevo stock", placa.getStock());

                            placa.setVram(nuevaVram);
                            placa.setTipoMemoria(nuevoTipoMemoria);
                            placa.setVelocidadMemoria(nuevaVelocidad);
                            placa.setStock(nuevoStock);

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
                        } else if (p instanceof Servicio s) {
                            int nuevaDuracion = leerEnteroOpcional("Nueva duración (horas)", s.getDuracionHoras());
                            s.setDuracionHoras(nuevaDuracion);
                            System.out.println("Servicio actualizado correctamente.");
                        } else if (p instanceof Articulo a) {
                            int nuevoStock = leerEnteroOpcional("Nuevo stock", a.getStock());
                            a.setStock(nuevoStock);
                            System.out.println("Artículo actualizado correctamente.");
                        }
                    }
                    case 2 -> {
                        Categoria cat = new Categoria(1, "Placa de video");
                        int vram = leerEnteroPositivo("VRAM (GB): ");
                        String tipoMemoria = leerTextoNoVacio("Tipo de memoria (ej: GDDR6): ");
                        double velocidad = leerDoublePositivo("Velocidad de memoria (MHz): ");
                        int stock = leerEnteroPositivo("Stock: ");
                        nuevoProducto = new PlacaDeVideo(nuevoNombre, nuevoPrecio, cat, vram, tipoMemoria, velocidad, stock);
                    }
                    case 3 -> {
                        Categoria cat = new Categoria(2, "Procesador");
                        int nucleos = leerEnteroPositivo("Cantidad de núcleos: ");
                        int hilos = leerEnteroPositivo("Cantidad de hilos: ");
                        double frecuenciaBase = leerDoublePositivo("Frecuencia base (GHz): ");
                        double frecuenciaTurbo = leerDoublePositivo("Frecuencia turbo (GHz): ");
                        int stock = leerEnteroPositivo("Cantidad en stock: ");
                        nuevoProducto = new Procesador(nuevoNombre, nuevoPrecio, cat, nucleos, hilos, frecuenciaBase, frecuenciaTurbo, stock);
                    }
                    case 4 -> {
                        int duracionHoras = leerEnteroPositivo("Duración (horas): ");
                        nuevoProducto = new Servicio(nuevoNombre, nuevoPrecio, duracionHoras);
                    }
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
                case 1 -> agregar();
                case 2 -> listar();
                case 3 -> actualizar();
                case 4 -> eliminar();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
}

package crudpoo.service;

import java.util.Scanner;

public abstract class CrudConsola<T> {

    protected final Scanner scanner = new Scanner(System.in);

    public abstract void agregar();
    public abstract void listar();
    public abstract void actualizar();
    public abstract void eliminar();

    public void mostrarOpciones() {
        System.out.println("\n=== Menú CRUD ===");
        System.out.println("1) Agregar");
        System.out.println("2) Listar");
        System.out.println("3) Actualizar");
        System.out.println("4) Eliminar");
        System.out.println("0) Salir");
        System.out.print("Opción: ");
    }

    public int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine();
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número entero válido.");
            }
        }
    }

    protected double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine();
                return Double.parseDouble(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número válido (use punto decimal).");
            }
        }
    }

    protected String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    protected String leerTextoNoVacio(String mensaje) {
        String texto;
        do {
            texto = leerTexto(mensaje).trim();
            if (texto.isBlank()) {
                System.out.println("Este campo no puede estar vacío. Intentá nuevamente.");
            }
        } while (texto.isBlank());
        return texto;
    }

    protected int leerEnteroPositivo(String mensaje) {
        int valor;
        do {
            valor = leerEntero(mensaje);
            if (valor <= 0) {
                System.out.println("El valor debe ser mayor que 0. Intentá nuevamente.");
            }
        } while (valor <= 0);
        return valor;
    }

    protected double leerDoublePositivo(String mensaje) {
        double valor;
        do {
            valor = leerDouble(mensaje);
            if (valor <= 0) {
                System.out.println("El valor debe ser mayor que 0. Intentá nuevamente.");
            }
        } while (valor <= 0);
        return valor;
    }

    protected String leerTextoOpcional(String mensaje, String valorActual) {
        System.out.print(mensaje + " [" + valorActual + "]: ");
        String entrada = scanner.nextLine().trim();
        return entrada.isEmpty() ? valorActual : entrada;
    }

    protected double leerDoubleOpcional(String mensaje, double valorActual) {
        System.out.print(mensaje + " [" + valorActual + "]: ");
        String entrada = scanner.nextLine().trim();
        if (entrada.isEmpty()) return valorActual;

        try {
            double valor = Double.parseDouble(entrada);
            if (valor <= 0) {
                System.out.println("El valor debe ser mayor que 0. Se mantiene el anterior.");
                return valorActual;
            }
            return valor;
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Se mantiene el anterior.");
            return valorActual;
        }
    }

    protected int leerEnteroOpcional(String mensaje, int valorActual) {
        System.out.print(mensaje + " [" + valorActual + "]: ");
        String entrada = scanner.nextLine().trim();
        if (entrada.isEmpty()) return valorActual;

        try {
            int valor = Integer.parseInt(entrada);
            if (valor <= 0) {
                System.out.println("El valor debe ser mayor que 0. Se mantiene el anterior.");
                return valorActual;
            }
            return valor;
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Se mantiene el anterior.");
            return valorActual;
        }
    }
}

package crudpoo.model.Componentes;

import crudpoo.model.Articulo;
import crudpoo.model.Categoria;

public class Procesador extends Articulo {
    private int nucleos;
    private int hilos;
    private double frecuenciaBase;
    private double frecuenciaTurbo;

    public Procesador(String nombre, double precio, Categoria categoria, int nucleos, int hilos, double frecuenciaBase, double frecuenciaTurbo, int stock) {
        super(nombre, precio, categoria, stock);
        this.nucleos = nucleos;
        this.hilos = hilos;
        this.frecuenciaBase = frecuenciaBase;
        this.frecuenciaTurbo = frecuenciaTurbo;
    }

    public int getNucleos() {
        return nucleos;
    }

    public int getHilos() {
        return hilos;
    }

    public double getFrecuenciaBase() {
        return frecuenciaBase;
    }

    public double getFrecuenciaTurbo() {
        return frecuenciaTurbo;
    }

    public void setNucleos(int nucleos) {
        this.nucleos = nucleos;
    }

    public void setHilos(int hilos) {
        this.hilos = hilos;
    }

    public void setFrecuenciaBase(double frecuenciaBase) {
        this.frecuenciaBase = frecuenciaBase;
    }

    public void setFrecuenciaTurbo(double frecuenciaTurbo) {
        this.frecuenciaTurbo = frecuenciaTurbo;
    }

    @Override
    public String toString() {
        return super.toString() +
                " [Núcleos: " + nucleos +
                " | Hilos: " + hilos +
                " | Frecuencia base: " + frecuenciaBase + " GHz" +
                " | Frecuencia turbo: " + frecuenciaTurbo + " GHz]";
    }
}

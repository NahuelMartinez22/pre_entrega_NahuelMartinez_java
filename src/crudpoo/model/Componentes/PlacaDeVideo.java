package crudpoo.model.Componentes;

import crudpoo.model.Articulo;
import crudpoo.model.Categoria;

public class PlacaDeVideo extends Articulo {
    private int vram;
    private String tipoMemoria;
    private double velocidadMemoria;

    public PlacaDeVideo(String nombre, double precio, Categoria categoria, int vram, String tipoMemoria, double velocidadMemoria) {
        super(nombre, precio, categoria);
        this.vram = vram;
        this.tipoMemoria = tipoMemoria;
        this.velocidadMemoria = velocidadMemoria;
    }

    public int getVram() {
        return vram;
    }

    public String getTipoMemoria() {
        return tipoMemoria;
    }

    public double getVelocidadMemoria() {
        return velocidadMemoria;
    }

    public void setVram(int vram) {
        this.vram = vram;
    }

    public void setTipoMemoria(String tipoMemoria) {
        this.tipoMemoria = tipoMemoria;
    }

    public void setVelocidadMemoria(double velocidadMemoria) {
        this.velocidadMemoria = velocidadMemoria;
    }

    @Override
    public String toString() {
        return super.toString() +
                " [VRAM: " + vram + " GB | Tipo de memoria: " + tipoMemoria + " | Velocidad de memoria: " + velocidadMemoria + " MHz]";
    }
}

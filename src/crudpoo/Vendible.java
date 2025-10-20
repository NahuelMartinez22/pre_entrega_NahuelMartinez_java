package crudpoo;

// ============================================================================
// Interfaz Vendible
// ----------------------------------------------------------------------------
// Esta interfaz define un "contrato" que cualquier clase que se venda debe cumplir.
// Sirve para practicar "interfaces" (de la guía de Clase 7).
// Toda clase que la implemente tendrá que proveer su versión del método
// calcularDescuento().
// ============================================================================
public interface Vendible {
    // Método del contrato: devuelve el precio con descuento aplicado.
    double calcularDescuento();
}

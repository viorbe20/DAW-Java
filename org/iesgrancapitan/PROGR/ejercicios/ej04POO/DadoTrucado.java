package org.iesgrancapitan.PROGR.ejercicios.ej04POO;

/**
 * Dado trucado, para que los dados generados puedan pasar por un dado trucado 
 * hacemos que la clase derive de Dado.
 * 
 * En el estado tendremos los siguientes datos:
 * 
 * - Valor del dado trucado (distinto del atributo de la superclase).
 * 
 * - Vector que indicará qué valores del dado "trucamos" y su probabilidad de aparición:
 *      * Si en una posición hay una probabilidad menor que cero, ese valor no lo "trucamos".
 *      * Otro probabilidad positiva indica que "trucamos" ese valor.
 * 
 * @author Rafael del Castillo Gomariz
 *
 */

public class DadoTrucado extends Dado {

  private int caraTrucada;
  private double[] probabilidades;

  public DadoTrucado() {}   // no sería necesario ponerlo

  /**
   * Cambia la probabilidad de aparición de uno de los valores del dado.
   * @param cara cara del dado a trucar (entre 1 y 6).
   * @param probabilidad entre 0 y 1. La suma de probabilidades de los valores trucados debe ser inferior a 1.
   * @return éxito de la operación.
   */
  public boolean trucar(int cara, double probabilidad) {

    // ¿valor del dado y/o probabilidad erróneo(s)?
    if (cara < 1 || cara > 6 || probabilidad < 0 || probabilidad > 1) {   
      return false;
    }

    // Si es el primer valor a trucar creamos el array de probabilidades
    if (this.probabilidades == null) {
      this.probabilidades = new double[6];
      for (int i=0; i<6; i++) {
        this.probabilidades[i] = -1;
      }  
    // Si ya estaba creado comprobamos si la suma de probabilidades anteriores, con esta, no es > 1
    } else if (! this.esProbabilidadCorrecta(cara, probabilidad)) { 
      return false;
    }

    this.probabilidades[cara-1] = probabilidad;
    return true;
  }

  /**
   * Comprobamos si las probabilidades son correctas para trucar la cara del dado.
   * @param valor
   * @param probabilidad
   * @return si es posible trucar.
   */
  private boolean esProbabilidadCorrecta(int valor, double probabilidad) {

    // Calculamos la suma de probabilidades y vemos si es menor que 1
    double sumaProbabilidadesTrucadas = probabilidad;
    for (double p: this.probabilidades) {
      if (p >= 0) {
        sumaProbabilidadesTrucadas += p;
      }
    }
    if (this.probabilidades[valor-1] > 0) { // esta cara ya estaba trucada de antes y se ha sumado de más
      sumaProbabilidadesTrucadas -= this.probabilidades[valor-1];
    }

    return sumaProbabilidadesTrucadas <= 1;
  }

  @Override
  public int getCara() {
    return caraTrucada;
  }

  @Override
  public void tirar() {

    if (this.probabilidades == null) {  // no hay trucados
      super.tirar();
      this.caraTrucada = super.getCara();
      return;
    }

    // Necesitamos conocer la probabilidad de cada número, trucados o no, para ello tengo que saber
    // primero cuantos números hay trucados y la suma de sus probabilidades. 
    // Con esto puedo calcular la probabilidad de aparición de los números no trucados.

    int numerosTrucados = 0;
    double sumaProbalidadesTrucadas = 0;
    double probabilidadNoTrucado = 0;

    for (double p: this.probabilidades) {   // cálculo de la suma números y probabilidades trucadas
      if (p >= 0) {
        numerosTrucados++;
        sumaProbalidadesTrucadas += p;
      }
    }
    if (numerosTrucados < 6) {  // por si estuvieran todos trucados
      probabilidadNoTrucado = (1-sumaProbalidadesTrucadas) / (6-numerosTrucados);
    }

    double aleatorio = Math.random();   // me servirá para escoger el valor del dado

    // Me quedo con la cara del dado cuya probabilidad de aparición, sumada a las anteriores, 
    // supere el valor aleatorio
    double sumaProbabilidades = 0;
    this.caraTrucada = 0;
    do {
      ++this.caraTrucada;
      if (this.probabilidades[this.caraTrucada-1] < 0) {    // no es una cara del dado trucada
        sumaProbabilidades += probabilidadNoTrucado;
      } else {
        sumaProbabilidades += this.probabilidades[this.caraTrucada-1];
      }
    } while (sumaProbabilidades < aleatorio && caraTrucada < 6);
  }

}
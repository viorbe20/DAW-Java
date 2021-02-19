package org.iesgrancapitan.PROGR.ejercicios.ej04POO;

/**
 * Prueba de la clase Dado.
 * 
 * Tiramos un dado 1000 veces y analizamos los resultados.
 * 
 * @author Rafael del Castillo Gomariz
 *
 */

public class TestDado {

  static final int TIRADAS = 1000;

  public static void main(String[] args) {

    int[] ocurrenciasDado1 = {0,0,0,0,0,0};
    int[] ocurrenciasDado2 = {0,0,0,0,0,0};
    Dado dado1 = new Dado();
    Dado dado2 = new DadoTrucado();
    
    // trucamos valores 5 y 6
    DadoTrucado dadoTrucado = (DadoTrucado) dado2;  // También: ((DadoTrucado) dado2).trucarValor(3,0.3)
    dadoTrucado.trucar(5, 0.3);
    dadoTrucado.trucar(6, 0.4);
    dadoTrucado.trucar(1, 0.01);

    // Tiramos el dado TIRADAS veces
    for (int i = 1; i <= TIRADAS; i++) {
      dado1.tirar(); ++ocurrenciasDado1[dado1.getCara()-1];
      dado2.tirar(); ++ocurrenciasDado2[dado2.getCara()-1];
    }  

    // Resultados
    System.out.println("Resultados después de tirar los dados " + TIRADAS + " veces:\n");
    System.out.println("\tDado1\t\t\tDado2");
    for (int tirada = 1; tirada <= 6; tirada++) {
      System.out.println("Cara " + tirada + ":\t" 
          + ocurrenciasDado1[tirada-1] + " veces - " 
          + (double) 100*ocurrenciasDado1[tirada-1]/TIRADAS + "%\t"
          + ocurrenciasDado2[tirada-1] + " veces - " 
          + (double) 100*ocurrenciasDado2[tirada-1]/TIRADAS + "%\t");
    }
  }

}

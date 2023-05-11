package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Fondo {
  Entorno entorno;
  Juego juego;
  Image fondo, fondo2, suelo;

  public Fondo(Entorno e, Juego juego) {
    // moverFondo();
    dibujar(e);
  }

  // //Movimiento del fondo
  // public void moverFondo() {
  // x -=2;
  // x2 -=2;
  // if(x == -620) {
  // x = 1250;
  //
  // }
  // if(x2== -620) {
  // x2= 1250;
  // }
  //
  // }

  // Dibujar el fondo en pantalla
  public void dibujar(Entorno e) {
    fondo = Herramientas.cargarImagen("fondo.jpg");

    e.dibujarImagen(fondo, 0, 0, 0, 1);

  }
}

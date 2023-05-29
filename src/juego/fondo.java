package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class fondo {
  Entorno entorno;
  Juego juego;
  Image fondo, fondo2, suelo;

  public fondo(Entorno e, Juego juego) {
    // moverFondo();
    dibujar(e);
  }

  // Dibujar el fondo en pantalla
  /**
   * @param e
   */
  public void dibujar(Entorno e) {
    fondo = Herramientas.cargarImagen("fondo.jpg");

    e.dibujarImagen(fondo, 0, 500, 0, 1);

  }
}

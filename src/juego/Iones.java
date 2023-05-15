package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Iones {
  private int x;
  private int y;
  private double velocidad = 5;
  Image img;

  public Iones(int startX, int startY) {
    this.x = startX;
    this.y = startY;
  }

  public double getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void mover() {
    y += velocidad;
  }

  public void dibujarIones(Entorno e) {
    img = Herramientas.cargarImagen("ion.png");
    e.dibujarImagen(img, x, y, 0, 0.10);

  }

  public Rectangle ionHitbox() {
    return new Rectangle(x, y, 13, 26);

  }

  public Iones fueraDePantalla(Iones Iones) {
    if ((int) Iones.getY() >= 1600) {
      Iones = null;
    }
    return Iones;
  }
}

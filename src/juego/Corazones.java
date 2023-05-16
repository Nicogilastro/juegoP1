package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Corazones {
	private int x;
	  private int y;
	  private double velocidad = 1;
	  Image img;

	  public Corazones(int startX, int startY) {
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

	  public void dibujarCorazones(Entorno e) {
	    img = Herramientas.cargarImagen("corazon.png");
	    e.dibujarImagen(img, x, y, 0, 0.03);

	  }

	  public Rectangle corazonHitbox() {
	    return new Rectangle(x, y, 13, 26);

	  }
}

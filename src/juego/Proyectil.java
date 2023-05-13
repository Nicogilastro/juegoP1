package juego;

import java.awt.*;

import entorno.Entorno;
import entorno.Herramientas;

public class Proyectil {
	private int x;
	int y;
	private double velocidad = 5;
	Image img;

	public Proyectil(int startX, int startY) {
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
		y -= velocidad;
	}

	public void moverDestructores() {
		y += velocidad;
	}

	public void dibujarProyectil(Entorno e) {
		// e.dibujarCirculo(x, y, 10, Color.GREEN);
		img = Herramientas.cargarImagen("proyectil.png");
		e.dibujarImagen(img, x, y, 0, 0.10);

	}

	public void rayoDestructor(Entorno e) {
		// e.dibujarCirculo(x, y, 10, Color.GREEN);
		img = Herramientas.cargarImagen("rayo.png");
		e.dibujarImagen(img, x, y, 0, 0.10);

	}

	public Rectangle proyectilHitbox() {
		return new Rectangle(x, y, 20, 20);
	}

}
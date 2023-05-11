package juego;

import java.awt.*;

import entorno.Entorno;

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
		e.dibujarCirculo(x, y, 10, Color.GREEN);

	}

	public Rectangle proyectilHitbox() {
		return new Rectangle(x, y, 20, 20);
	}

}
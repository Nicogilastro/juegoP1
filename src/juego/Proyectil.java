package juego;

import java.awt.*;

import entorno.Entorno;

public class Proyectil {
	int x,y;
	private int speed = 5;
	Image img;

	public Proyectil (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void mover() {
		x = x + speed;
	}
	
	public void dibujarProyectil(Entorno e) {
		e.dibujarCirculo(x, y, 20,Color.GREEN);
	}
	
	public Rectangle proyectilHitbox() {
		return new Rectangle(x, y, 20, 20);
	}
}

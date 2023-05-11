package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Destructores {
	private double x;
	private double y;
	private double velocidad;
	private int movimiento = 0;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public int getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(int movimiento) {
		this.movimiento = movimiento;
	}

	Image imagenDest = Herramientas.cargarImagen("destructor.png");

	public Destructores(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
	}

	public void mover(double mod) {
		if (movimiento == 50) {
			movimiento = -50;
		}

		if (movimiento < 50 && movimiento >= 0) {
			x = x + mod;
			movimiento += 1;
		}

		if (movimiento < 0 && movimiento >= -50) {
			x = x - mod;
			movimiento += 1;
		}

		y += velocidad;
	}

	public void acelerar() {
		velocidad += 0.05;
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(imagenDest, this.x, this.y, 0, 0.4);

	}

	public Rectangle destructoresHitbox() {
		return new Rectangle((int) this.x, (int) this.y, 20, 20);
	}

	public void fueraDePantalla(int x, int y) {
		if (this.getX() > x + 70) {
			this.setX(x - 90);
		}

		if (this.getX() < x - 70) {
			this.setX(this.getX() + 80);
		}
	}

	public boolean colision(Spaceship navecita, Destructores[] destructoresArr) {
		for (int i = 0; i < destructoresArr.length; i++) {
			if (destructoresArr[i] != null) {
				if (navecita.navecitaHitbox().intersects(destructoresArr[i].destructoresHitbox())) {
					int navecitaVidas = navecita.getVidas();
					destructoresArr[i] = null;
					navecitaVidas -= 1;
					navecita.setVidas(navecitaVidas);
					return true;
				}
			}
		}
		return false;
	}

	public void superponen(Destructores[] destructoresArr, Asteroides[] asteroidesArr) {
		for (int i = 0; i < destructoresArr.length; i++) {
			for (int j = 0; j < asteroidesArr.length; j++) {
				if (destructoresArr[i] == null || asteroidesArr[j] == null) {
					continue;
				} else {
					if (destructoresArr[i].destructoresHitbox().intersects(asteroidesArr[j].asteroideHitbox())) {
						destructoresArr[i].setY(destructoresArr[i].getY() - 30);
					}
				}

			}
		}
		for (int i = 0; i < destructoresArr.length; i++) {
			if (destructoresArr[i] == null) {
				continue;
			} else {
				try {
					if (destructoresArr[i].destructoresHitbox().intersects(destructoresArr[i + 1].destructoresHitbox())) {
						destructoresArr[i + 1].setY(destructoresArr[i + 1].getY() - 30);
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
	}

	public void destruccion(Destructores[] destructoresArr, Proyectil[] proyectilesArr, Spaceship navecita) {
		for (int i = 0; i < destructoresArr.length; i++) {
			for (int j = 0; j < proyectilesArr.length; j++) {
				if (destructoresArr[i] == null || proyectilesArr[j] == null) {
					continue;
				} else {
					if (destructoresArr[i].destructoresHitbox().intersects(proyectilesArr[j].proyectilHitbox())) {
						navecita.setPuedeDisparar(true);
						navecita.setPuntaje((int) navecita.getPuntaje() + 1);
						destructoresArr[i] = null;
						proyectilesArr[0] = null;

					}
				}

			}
		}
	}
}

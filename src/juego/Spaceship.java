package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Spaceship {
	private double x;
	private double y;
	private double velocidad;
	private int vidas;
	private boolean puedeDisparar = true;
	private int puntaje;
	Proyectil p;
	public Proyectil proyectiles[] = new Proyectil[1];
	Image img1;

	// spaceship
	public Spaceship(double x, double y, int vidas, double velocidad) {
		this.x = x;
		this.y = y;
		this.vidas = vidas;
		this.velocidad = velocidad;
		img1 = Herramientas.cargarImagen("nave.png");
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1.25);

	}

	public void aumentarVelocidad() {
		this.velocidad += this.velocidad * 1.1;
	}

	// getters y setters
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
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

	public boolean puedeDisparar() {
		return puedeDisparar;
	}

	public void setPuedeDisparar(boolean puedeDisparar) {
		this.puedeDisparar = puedeDisparar;
	}

	// movimiento

	public void moverDerecha(Entorno entorno) {
		if (this.x < entorno.ancho() - 30) {
			this.x += this.velocidad * 2;
		}
	}

	public void moverIzquierda(Entorno entorno) {
		if (this.x > 35) {
			this.x -= this.velocidad * 2;
		}

	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public Rectangle navecitaHitbox() {
		return new Rectangle((int) this.x, (int) this.y - 70, 60, 100);
	}

	public void disparar(Entorno entorno) {
		if (puedeDisparar) {
			Proyectil p = new Proyectil((int) x, (int) y);
			proyectiles[0] = p;
		}
		puedeDisparar = false;
	}

	public Proyectil fueraDePantalla(Proyectil proyectil) {
		if ((int) proyectil.getY() <= 0) {
			proyectil = null;
		}
		return proyectil;
	}

}

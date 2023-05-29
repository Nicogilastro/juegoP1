package juego;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img1, this.x, this.y, 0, 2);

	}

	public void aumentarVelocidad() {
		this.velocidad += 0.25;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
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

	// vidas

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	// hitbox

	public Rectangle navecitaHitbox() {
		return new Rectangle((int) this.x, (int) this.y, 32, 64);
	}

	// disparo

	public void disparar() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		if (puedeDisparar) {
			p = new Proyectil((int) x, (int) y);
			proyectiles[0] = p;
			// Se declara y usa sonido proyectil
			File file2 = new File("src/proyectil.wav");
			AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(file2);
			Clip clipProyectil = AudioSystem.getClip();
			clipProyectil.open(audioStream2);
			clipProyectil.start();
		}
		puedeDisparar = false;
	}

	public Proyectil fueraDePantalla(Proyectil proyectil) {
		if ((int) proyectil.getY() <= 0) {
			proyectil = null;
		}
		return proyectil;
	}

	public boolean colisionConIon(Spaceship navecita, Iones[] ionesArr) {
		boolean bandera = false;

		for (int i = 0; i < ionesArr.length; i++) {
			if (ionesArr[i] != null) {
				if (navecita.navecitaHitbox().intersects(ionesArr[i].ionHitbox())) {
					ionesArr[i] = null;
					bandera = true;
				}
			} else if (ionesArr[i] == null) {
				continue;
			}
		}
		return bandera;
	}

}

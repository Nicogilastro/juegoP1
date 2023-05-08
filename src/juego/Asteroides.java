package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Asteroides {
	private double x;
	private double y;
	private double velocidad;
	private double angulo;
	private int radio;
	
	Image imagenAst = Herramientas.cargarImagen("asteroide.png");;
	
	public Asteroides(double x, double y, double velocidad, double angulo, int radio) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.angulo = angulo;
		this.setRadio(radio);
	}
	
	public Rectangle astHitBox() {
		return new Rectangle((int) this.x, (int) this.y, 20, 20);
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagenAst, this.x, this.y, -1.55, 1);
	}

	public void mover(int mod) {
		y += velocidad * Math.sin(angulo);
		x += velocidad * Math.cos(angulo) * mod;
	}
	
	public void cambiarTrayectoria() {
		angulo += Math.PI/2;
	}
	
	public void acelerar() {
		velocidad += 0.05;
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(imagenAst, this.x, this.y, -1.55, 0.3);
		
	}

	public int getRadio() {
		return radio;
	}

	public void setRadio(int radio) {
		this.radio = radio;
	}
	
	public Rectangle asteroideHitbox() {
		return new Rectangle((int) this.x,(int) this.y, 20, 20);
	}

	public boolean colision(Spaceship navecita, Asteroides[] asteroidesArr) {
		for(int i = 0; i < asteroidesArr.length; i++) {
			if(asteroidesArr[i] != null) {
				if(navecita.navecitaHitbox().intersects(asteroidesArr[i].asteroideHitbox())) {
					asteroidesArr[i] = null;
					int navecitaVidas = navecita.getVidas();
					navecitaVidas -= 1;
					navecita.setVidas(navecitaVidas);
					return true;
				}
			}
		}
		return false;
	}

}

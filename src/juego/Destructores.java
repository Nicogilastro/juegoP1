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
	
	
	Image imagenDest = Herramientas.cargarImagen("destructor.png");;
	
	public Destructores(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
	}
	
	public Rectangle destHitBox() {
		return new Rectangle((int) this.x, (int) this.y, 20, 20);
	}

	public void mover(double mod) {
		if(movimiento == 100) {
			movimiento = -100;
		}
		
		if(movimiento < 100 && movimiento >= 0) {
			x = x + mod;
			movimiento += 1;
		} 
		
		if(movimiento < 0 && movimiento >= -100) {
			x = x - mod;
			movimiento += 1;
		}
		
		y += velocidad;
	}
	
	public void acelerar() {
		velocidad += 0.05;
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(imagenDest, this.x, this.y, 0, 0.3);
		
	}
	
	public Rectangle destrucoresHitbox() {
		return new Rectangle((int) this.x,(int) this.y, 20, 20);
	}

	public boolean colision(Spaceship navecita, Destructores[] destrucoresArr) {
		for(int i = 0; i < destrucoresArr.length; i++) {
			if(destrucoresArr[i] != null) {
				if(navecita.navecitaHitbox().intersects(destrucoresArr[i].destrucoresHitbox())) {
					destrucoresArr[i] = null;
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

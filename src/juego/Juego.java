package juego;

import java.awt.Color;
import java.util.Random;

import java.util.Timer;
import java.util.TimerTask;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Spaceship navecita;
	Asteroides asteroide;
	Asteroides[] asteroidesArr;
	Destructores[] destructoresArr;
	Destructores destructor;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Lost Galaxian - Grupo 3 - v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		
		navecita = new Spaceship(entorno.ancho()/2, entorno.alto() - 100, 3, 2);
		
//		genera un numero random de asteroides		
		
		Random random = new Random();
		int rand = 0;
		while (true){
		    rand = random.nextInt(4, 7);
		    if(rand !=0) break;
		}
//		array de asteroides
		asteroidesArr = new Asteroides[rand];
		
		for(int i=1; i <= rand; i++) {
			asteroide = new Asteroides(random.nextInt(0, 800), random.nextInt(0, 200), 1, Math.PI/4, 30);
			asteroidesArr[i-1] = asteroide;
		}
		
//		genera un numero random de destructores	
		
		Random random2 = new Random();
		int rand2 = 0;
		while (true){
		    rand2 = random2.nextInt(4, 7);
		    if(rand !=0) break;
		}
//		array de destructores
		destructoresArr = new Destructores[rand2];
		
		for(int i=1; i <= rand2; i++) {
			destructor = new Destructores(random.nextInt(rand2*5, 800), random.nextInt(rand2*3, 300), 1);
			destructor.fueraDePantalla(random.nextInt(rand2*5, 800), random.nextInt(rand2*3, 300));
			destructoresArr[i-1] = destructor;
			
		}

		
		// Inicia el juego!
		this.entorno.iniciar();
		
	}
	
	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		
//		Comportamiento de las teclas
		
		if (entorno.estaPresionada(entorno.TECLA_ESPACIO))
			navecita.disparar();
		
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d'))
			navecita.moverDerecha(entorno);

		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a'))
			navecita.moverIzquierda(entorno);
		
//		dibujo la nave
		navecita.dibujarse(entorno);
		
//		dibujo cada asteroide
		for(int i=0; i < asteroidesArr.length; i++) {
			if(asteroidesArr[i] == null) {
				continue;
			} else {
				asteroidesArr[i].dibujarse(entorno);
				asteroidesArr[i].mover(i%2 == 0 ? 1 : -1);
				
			}
		}
		
//		dibujo cada destructor

		for(int i=1; i <= destructoresArr.length; i++) {
			if(destructoresArr[i-1] == null) {
				continue;
			} else {
				destructoresArr[i-1].dibujarse(entorno);
				
			}
		}

//		muevo los destructores de izq a der

	    for(int i=0; i < destructoresArr.length; i++) {
			if(destructoresArr[i] == null) {
				continue;
			} else {
				if(i % 2 == 0)
					destructoresArr[i].mover(1.5);
				else 
					destructoresArr[i].mover(1.5);
				
			}
		}

		
// muevo los destructores		
		
		
		
//		dibujo lost textos
//		vidas
		entorno.cambiarFont("Arial", 18, Color.white);
		entorno.escribirTexto("Vidas: " + navecita.getVidas(), 50, 50);

		asteroide.colision(navecita, asteroidesArr);
		destructor.colision(navecita, destructoresArr);
		destructor.superponen(destructoresArr, asteroidesArr);

		
//		estado de la navecita vidas, proyectiles etc
		if(navecita.getVidas() == 0) {
			System.exit(0);
		}
	
	}	
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}

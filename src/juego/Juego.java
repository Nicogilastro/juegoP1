package juego;

import java.awt.Color;
import java.util.Random;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;

	// Variables y métodos propios de cada grupo
	// ...
	Spaceship navecita;
	Asteroides[] asteroidesArr;
	Asteroides asteroide;
	Destructores[] destructoresArr;
	Destructores destructor;
	Iones[] ionesArr;
	Iones ion;
	Fondo fondo;
	int tiempoDest = 0;
	int tiempoAst = 0;
	int tiempoDestDisparo = 0;

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Lost Galaxian - Grupo 3 - v1", 600, 1000);

		// Inicializar lo que haga falta para el juego

		navecita = new Spaceship(entorno.ancho() / 2, entorno.alto() - 100, 3, 2);

		generarAsteroides();

		generarDestructores();

		generarIones(destructoresArr);

		fondo = new Fondo(entorno, this);

		// Inicia el juego!
		this.entorno.iniciar();

	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y
	 * por lo tanto es el método más importante de esta clasentorno. Aquí se debe
	 * actualizar el estado interno del juego para simular el paso del tiempo
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo
		// ...

		tiempoDest++;

		tiempoAst++;

		tiempoDestDisparo++;

		// dibujo el fondo
		fondo.dibujar(entorno);

		// Comportamiento de las teclas

		if (entorno.estaPresionada(entorno.TECLA_ESPACIO))
			navecita.disparar();

		if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d'))
			navecita.moverDerecha(entorno);

		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a'))
			navecita.moverIzquierda(entorno);

		// dibujo la nave
		navecita.dibujarse(entorno);

		// disparo de la nave

		dibujarProyectiles(entorno);

		// disparo de los destructores

		dibujarIones(entorno);

		// dibujo cada asteroide

		Random random = new Random();
		int rand = 0;
		while (true) {
			rand = random.nextInt(7 - 4) + 4;
			if (rand != 0)
				break;
		}

		for (int i = 0; i < asteroidesArr.length; i++) {
			if (asteroidesArr[i] == null) {
				asteroide = new Asteroides(random.nextInt(800), random.nextInt(200), 1, Math.PI / 4, 30);
				asteroidesArr[i] = asteroide;
			} else {
				asteroidesArr[i].dibujarse(entorno);
				asteroidesArr[i].mover(i % 2 == 0 ? 1 : -1);

			}
		}

		// dibujo cada destructor

		for (int i = 0; i < destructoresArr.length; i++) {
			if (destructoresArr[i] == null) {
				continue;
			} else {
				destructoresArr[i].dibujarse(entorno);
			}
		}

		// muevo los destructores de izq a der

		for (int i = 0; i < destructoresArr.length; i++) {
			if (destructoresArr[i] != null) {
				if (i % 2 == 0) {
					destructoresArr[i].mover(1.5);
				} else {
					destructoresArr[i].mover(1.5);
				}
			}
		}

		// disparos destructores

		Random random3 = new Random();
		int probDisparo = random3.nextInt(100 - 1) + 1;
		if (tiempoDest / 100 == 2 % 1)
			destructoresDisparos(probDisparo);

		// regenero los destructores que se destruyen

		for (int i = 0; i < destructoresArr.length; i++) {
			if (destructoresArr[i] == null) {
				destructoresArr[i] = new Destructores(random.nextInt(550) + 100,
						random.nextInt(200 - 50) + (50), 1);

			} else {
				destructoresArr[i].destruccion(destructoresArr, navecita.proyectiles, navecita);

			}
		}

		// regenero los iones que impactan o estan fuera de pantalla

		for (int i = 0; i < ionesArr.length; i++) {
			if (ionesArr[i] == null) {
				ionesArr[i] = new Iones((int) destructoresArr[i].getX(), (int) destructoresArr[i].getY());

			} else {
				//ionesArr[i].fueraDePantalla(ionesArr[i]);
				Iones ionNew = ionesArr[i];
				ionesArr[i].fueraDePantalla(ionesArr[i]);
				ionesArr[i] = ionNew;
			}
		}

		// dibujo los textos

		// vidas
		entorno.cambiarFont("Arial", 18, Color.white);
		entorno.escribirTexto("Vidas: " + navecita.getVidas(), 25, 50);

		// enemigos eliminados

		entorno.cambiarFont("Arial", 20, Color.RED);
		entorno.escribirTexto("Enemigos Eliminados: " + navecita.getPuntaje(), 25, 975);

		// colisiones

		asteroide.colision(navecita, asteroidesArr);

		if (ionesArr != null) {
			if (navecita.colisionConIon(navecita, ionesArr)) {
				int navecitaVidas = navecita.getVidas();
				navecitaVidas -= 1;
				navecita.setVidas(navecitaVidas);
			}
		}

		// colision destructor con navecita

		destructor.colision(navecita, destructoresArr);

		// colision destructor con asteroide

		destructor.superponenAst(destructoresArr, asteroidesArr);

		// colision destructor con destructor

		destructor.superponenDest(destructoresArr);

		// estado de las vidas de la navecita, tiempos de respawn de asteroides y
		// destructores
		if (navecita.getVidas() == 0) {
			System.exit(0);
		}

		if (tiempoDest > 1200) {
			generarDestructores();
			tiempoDest = 0;
		}

		if (tiempoAst > 600) {
			generarAsteroides();
			tiempoAst = 0;
		}

		// me fijo si el jugador gano

		if (navecita.getPuntaje() == 15) {
			entorno.cambiarFont("Microsoft Yahei", 40, Color.red);
			entorno.escribirTexto("YOU WIN!!", entorno.ancho() / 2 - 125, entorno.alto() / 2);

			entorno.cambiarFont("Microsoft Yahei", 30, Color.red);
			entorno.escribirTexto("Puntaje: " + navecita.getPuntaje(), entorno.ancho() / 2 - 125, entorno.alto() / 2 + 50);

		}

	}

	public void escribirTextoPantalla(String text, int x, int y, Entorno e) {
		entorno.escribirTexto(text, x, y);
	}

	public void dibujarProyectiles(Entorno e) {
		// disparos de navecita

		if (navecita.proyectiles[0] != null) {
			navecita.proyectiles[0].dibujarProyectil(e);
			navecita.proyectiles[0].mover();
			navecita.proyectiles[0] = navecita.fueraDePantalla(navecita.proyectiles[0]);

		} else {
			navecita.setPuedeDisparar(true);

		}

	}

	public void dibujarIones(Entorno e) {
		if (ionesArr != null) {
			for (int i = 0; i < ionesArr.length; i++) {
				if (ionesArr[i] != null) {
					ionesArr[i].dibujarIones(e);
					ionesArr[i].mover();
					ionesArr[i] = ionesArr[i].fueraDePantalla(ionesArr[i]);

				}
			}
		}
	}

	public void destructoresDisparos(int probDisparo) {
		if (navecita.getVidas() != 0) {
			if (probDisparo == 10) {
				Random random = new Random();
				int rand = 0;
				while (true) {
					rand = random.nextInt(destructoresArr.length - 1);
					if (rand != 0)
						break;
				}

				for (int j = 0; j < ionesArr.length; j++) {
					if (destructoresArr[rand] != null && ionesArr[j] != null) {
						destructoresArr[rand].disparo(destructoresArr[rand], ionesArr[rand]);
						break;
					} else {
						continue;
					}
				}

			}
		}
	}

	// genero destructores

	public void generarDestructores() {
		// genera un numero random de destructores
		Random random = new Random();
		int rand2 = 0;
		while (true) {
			rand2 = random.nextInt(6 - 4) + 4;
			if (rand2 != 0)
				break;
		}
		// array de destructores
		destructoresArr = new Destructores[rand2];

		for (int i = 0; i < rand2; i++) {
			destructor = new Destructores((random.nextInt(400)) + 100,
					random.nextInt(200), 1);
			destructoresArr[i] = destructor;

		}
	}

	// genero asteroides

	public void generarAsteroides() {
		// genera un numero random de asteroides

		Random random = new Random();
		int rand = 0;
		while (true) {
			rand = random.nextInt(6 - 4) + 4;
			if (rand != 0)
				break;
		}

		// array de asteroides
		asteroidesArr = new Asteroides[rand];

		for (int i = 0; i < rand; i++) {
			if (asteroidesArr[i] == null) {
				asteroide = new Asteroides(random.nextInt(800), random.nextInt(200), 1, Math.PI / 4, 30);
				asteroidesArr[i] = asteroide;
			} else {
				continue;
			}

		}
	}

	// genero iones

	public void generarIones(Destructores[] destructoresArr) {
		// array de iones
		ionesArr = new Iones[destructoresArr.length];

		for (int i = 0; i < destructoresArr.length; i++) {
			if (destructoresArr[i] != null) {
				Iones ion = new Iones((int) destructoresArr[i].getX(), (int) destructoresArr[i].getY());
				ionesArr[i] = ion;
			}

			if (ionesArr[i] == null) {
				Iones ion = new Iones((int) destructoresArr[i].getX(), (int) destructoresArr[i].getY());
				ionesArr[i] = ion;
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();

	}
}

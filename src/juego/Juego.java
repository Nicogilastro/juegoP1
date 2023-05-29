package juego;

import java.awt.Color;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
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
	Corazones[] corazonesArr;
	Corazones corazon;
	RayoVeloz[] rayoArr;
	RayoVeloz rayo;
	Iones ion;
	fondo fondo;
	int tiempoDest = 0;
	int tiempoAst = 0;
	int tiempoDestDisparo = 0;
	int tiempoCorazon = 0;
	String gameState = "jugando";
	String gameStage = "jugando";

	Juego() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Lost Galaxian - Grupo 3 - v1", 600, 1000);

		// Inicializar lo que haga falta para el juego

		navecita = new Spaceship(entorno.ancho() / 2, entorno.alto() - 100, 3, 2);

		generarAsteroides();

		generarDestructores();

		generarIones(destructoresArr);

		fondo = new fondo(entorno, this);

		// Musica de fondo , declaro e inicializo
		File file = new File("src/espacio.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clipFondo = AudioSystem.getClip();
		clipFondo.open(audioStream);
		clipFondo.start();

		// Inicia el juego!
		this.entorno.iniciar();

	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clasentorno. Aquí se debe
	 * actualizar el estado interno del juego para simular el paso del tiempo (ver
	 * el enunciado del TP para mayor detalle).
	 */

	public void tick() {
		// Procesamiento de un instante de tiempo

		tiempoDest++;

		tiempoAst++;

		tiempoDestDisparo++;

		tiempoCorazon++;

		// dibujo el fondo
		fondo.dibujar(entorno);

		// Comportamiento de las teclas

		if (entorno.estaPresionada(entorno.TECLA_ESPACIO))
			try {
				navecita.disparar();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {

				e.printStackTrace();
			}

		if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d'))
			navecita.moverDerecha(entorno);

		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a'))
			navecita.moverIzquierda(entorno);

		// disparos destructores

		Random random3 = new Random();
		int probDisparo = random3.nextInt(100 - 1) + 1;
		if (tiempoDest / 100 == 2 % 1) {
			try {
				destructoresDisparos(probDisparo);
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

				e.printStackTrace();
			}
		}
		// regenero los destructores que se destruyen

		if (navecita.getPuntaje() < 10) {
			generarDestructoresMuertos(random3);

		}

		for (int i = 0; i < destructoresArr.length; i++) {
			if (destructoresArr[i] != null) {
				try {
					destructoresArr[i].destruccion(destructoresArr, navecita.proyectiles, navecita);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {

					e.printStackTrace();
				}

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

		if (tiempoDest > 1200 && navecita.getPuntaje() < 5) {
			generarDestructores();
			tiempoDest = 0;
		}

		if (tiempoAst > 600) {
			generarAsteroides();
			tiempoAst = 0;
		}

		if (tiempoCorazon > 1400) {
			generarCorazon();
			generarRayo();
			tiempoCorazon = 0;
		}

		// me fijo si los destructores estan fuera de pantalla

		for (int i = 0; i < destructoresArr.length; i++) {
			if (destructoresArr[i] != null) {
				destructoresArr[i].fueraDePantalla(destructoresArr, entorno);

			}
		}

		Random random = new Random();
		int rand = 0;
		while (true) {
			rand = random.nextInt(7 - 4) + 4;
			if (rand != 0)
				break;
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

		// regenero los iones que impactan o estan fuera de pantalla

		for (int i = 0; i < ionesArr.length; i++) {
			try {
				if (ionesArr[i] == null && destructoresArr[i] != null) {
					ionesArr[i] = new Iones((int) destructoresArr[i].getX(), (int) destructoresArr[i].getY());

				} else {
					Iones ionNew = ionesArr[i];
					ionesArr[i].fueraDePantalla(ionesArr[i]);
					ionesArr[i] = ionNew;

				}
			} catch (Exception ArrayIndexOutOfBounds) {

			}
		}

		// me fijo en que momento del juego esta el jugador
		if (navecita.getPuntaje() < 10) {
			gameStage = "fin";
		}
		// me fijo si el jugador gano

		int sumadorDestructoresNull = 0;
		for (int i = 0; i < destructoresArr.length; i++) {
			if (destructoresArr[i] == null) {
				sumadorDestructoresNull++;
			}
		}

		if (sumadorDestructoresNull == destructoresArr.length) {
			gameState = "gano";
		}

		if (gameState != "gano") {
			// dibujo la nave
			navecita.dibujarse(entorno);

			// disparo de la nave

			dibujarProyectiles(entorno);

			// disparo de los destructores

			try {
				dibujarIones(entorno);
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {

				e1.printStackTrace();
			}

			// dibujo cada asteroide

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
			// dibujo los corazones

			if (corazonesArr != null) {
				if (corazonesArr[0] != null) {
					corazonesArr[0].dibujarCorazones(entorno);
					corazonesArr[0].mover();
					if (corazonesArr[0].corazonHitbox().intersects(navecita.navecitaHitbox())) {
						navecita.setVidas(navecita.getVidas() + 1);
						corazonesArr[0] = null;

						// SONIDO CORAZON AL AGARRAR
						File file4 = new File("src/corazon.wav");
						AudioInputStream audioStream4 = null;
						try {
							audioStream4 = AudioSystem.getAudioInputStream(file4);
						} catch (UnsupportedAudioFileException | IOException e) {

							e.printStackTrace();
						}
						Clip clipCorazon = null;
						try {
							clipCorazon = AudioSystem.getClip();
						} catch (LineUnavailableException e) {

							e.printStackTrace();
						}
						try {
							clipCorazon.open(audioStream4);
						} catch (LineUnavailableException | IOException e) {

							e.printStackTrace();
						}
						clipCorazon.start();
					}
				}
			}

			// dibujo los rayos

			if (rayoArr != null) {
				if (rayoArr[0] != null) {
					rayoArr[0].dibujarRayos(entorno);
					rayoArr[0].mover();
					if (rayoArr[0].rayoHitbox().intersects(navecita.navecitaHitbox())) {
						navecita.aumentarVelocidad();
						navecita.p.setVelocidad(navecita.p.getVelocidad() + 0.25);
						rayoArr[0] = null;
						File file6 = new File("src/rayo.wav");
						AudioInputStream audioStream6 = null;
						try {
							audioStream6 = AudioSystem.getAudioInputStream(file6);
						} catch (UnsupportedAudioFileException | IOException e) {

							e.printStackTrace();
						}
						Clip clipRayo = null;
						try {
							clipRayo = AudioSystem.getClip();
						} catch (LineUnavailableException e) {

							e.printStackTrace();
						}
						try {
							clipRayo.open(audioStream6);
						} catch (LineUnavailableException | IOException e) {

							e.printStackTrace();
						}
						clipRayo.start();

					}
				}
			}
		} else {
			entorno.cambiarFont("Microsoft Yahei", 40, Color.red);
			entorno.escribirTexto("YOU WIN!!", entorno.ancho() / 2 - 125, entorno.alto() / 2);

			entorno.cambiarFont("Microsoft Yahei", 30, Color.red);
			entorno.escribirTexto("Puntaje: " + navecita.getPuntaje(), entorno.ancho() / 2 - 125,
					entorno.alto() / 2 + 50);

			entorno.cambiarFont("Microsoft Yahei", 20, Color.red);
			entorno.escribirTexto("Salir (s)", entorno.ancho() / 2 - 125, entorno.alto() / 2 + 150);

			entorno.cambiarFont("Microsoft Yahei", 20, Color.red);
			entorno.escribirTexto("Volver a jugar (r)", entorno.ancho() / 2 - 25, entorno.alto() / 2 + 150);

			if (entorno.estaPresionada('s'))
				System.exit(0);

			if (entorno.estaPresionada('r')) {
				gameStage = "jugando";
				gameState = "jugando";
				navecita.setPuntaje(0);
				navecita.setVidas(3);
				generarAsteroides();
				generarDestructores();
				try {
					generarIones(destructoresArr);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {

					e.printStackTrace();
				}
				corazon = null;
				rayo = null;
				navecita.setX(entorno.ancho() / 2);
				navecita.setY(entorno.alto() - 100);
			}

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

	public void dibujarIones(Entorno e) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
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

	public void destructoresDisparos(int probDisparo)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {
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

	// genero corazones

	public void generarCorazon() {
		Random randomCorazones = new Random();
		corazonesArr = new Corazones[11];
		corazon = new Corazones((randomCorazones.nextInt(400)) + 100, randomCorazones.nextInt(200));
		corazonesArr[0] = corazon;
	}

	// genero rayos

	public void generarRayo() {
		Random randomRayo = new Random();
		rayoArr = new RayoVeloz[11];
		rayo = new RayoVeloz((randomRayo.nextInt(400)) + 100, randomRayo.nextInt(200));
		rayoArr[0] = rayo;
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
			destructor = new Destructores((random.nextInt(400)) + 100, random.nextInt(200), 1);
			destructoresArr[i] = destructor;

		}
	}

	public void generarDestructoresMuertos(Random random3) {
		for (int i = 0; i < destructoresArr.length; i++) {
			if (destructoresArr[i] == null) {
				destructoresArr[i] = new Destructores(random3.nextInt(350) + 100, random3.nextInt(200 - 50) + (50), 1);

			}
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

	public void generarIones(Destructores[] destructoresArr)
			throws LineUnavailableException, IOException, UnsupportedAudioFileException {
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
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		Juego juego = new Juego();

	}

}

package dkeep.logic;

import java.util.Random;

public class Game {
	private final int SUCCESS = 1;
	private final int EXIT_ELEMENT = 2;
	private final int SWORD_ELEMENT = 3;
	private final int DRAGON_AREA = 4;
	private final int DRAGON_KILLED = 5;
	private final int KNOCKDOOR = 6;
	private final int DRAGON_IN_SWORD = 7;
	private final int ERROR_WALL = -1;
	private final int ERROR_ELEMENT = -2;

	private Map map;
	private boolean running;

	public Game() {
		this.map = new Map();
		running = true;
	}

	public Map getMap() {
		return map;
	}

	/* verifica a logica do jogo */
	private int gameLogic(int x, int y, char element) {
		

		/* morre para dragao, posiçao adjacente */
		if ((adjacentPositionDragon() && map.getDragon().isAlive() && !map.getHero().isHaveSword())) {
			this.running = false;
			return DRAGON_AREA;
		}
		/* heroi posiçao adjacente com espada -> MATA DRAGAO */
		else if (adjacentPositionDragon() && map.getDragon().isAlive() && map.getHero().isHaveSword()) {
			map.getDragon().setAlive(false);// mata dragao
			map.setToMap(map.getDragon().getX(), map.getDragon().getY(), ' ');// elimina dragao do mapa
			map.getHero().setHaveKey(true);// mete chaves no heroi
			return DRAGON_KILLED;
		}
		/* HEROI apanha ESPADA */
		else if (map.getHero().getX() == map.getSword().getX() && map.getHero().getY() == map.getSword().getY()) {
			map.getHero().setHaveSword(true);
			map.getHero().setSkin('A');// atualiza variavel skin no hero
			map.setToMap(map.getHero().getX(), map.getHero().getY(), map.getHero().getSkin());// Atualiza skin no mapa
			return SWORD_ELEMENT;
		}
		/* HEROI tenta sair sem chaves */
		else if (heroToExit() && !map.getHero().isHaveKey()) {// tenta sair sem chave
			/*
			 * o heroi entra na saida, entao temos de dar reset e voltar a meter para trás
			 */
			resetExitAndHero(map.getExit().getSkin()); // coloca o "E" no mesmo sitio, nao altera saida
			return ERROR_WALL;
		}
		/* HEROI sai com chaves, bate à porta */
		else if (heroToExit() && map.getHero().isHaveKey() && !map.getHero().isKnockDoor()) {
			resetExitAndHero(' ');// abre porta da saida
			map.getHero().setKnockDoor(true);
			return KNOCKDOOR;
		}
		/* HEROI sai com sucesso */
		else if (heroToExit() && map.getHero().isHaveKey() && map.getHero().isKnockDoor()) {
			this.running = false;
			return EXIT_ELEMENT;
		} 
		/*DRAGAO em cima da ESPADA*/
		else if(map.getDragon().getX() == map.getSword().getX() && map.getDragon().getY() == map.getSword().getY()) {
			map.getDragon().setSkin('F');
			map.setToMap(map.getDragon().getX(), map.getDragon().getY(), 'F');
			return DRAGON_IN_SWORD;
		}
		/*DRAGAO NAO ESTÁ em cima da ESPADA 	OU		movido com sucesso*/
		else if ((map.getDragon().getX() != map.getSword().getX() || map.getDragon().getY() != map.getSword().getY()) && map.getCurrentMap()[y][x] == element) // está tudo a correr bem
			{
			if(map.getDragon().isAlive()) {
				map.getDragon().setSkin('D');
				map.setToMap(map.getDragon().getX(), map.getDragon().getY(), 'D');	
			}
			if(!map.getHero().isHaveSword())
				map.setToMap(map.getSword().getX(), map.getSword().getY(), map.getSword().getSkin());
			return SUCCESS;
			}

		
		/* HOUVE ALGUM ERRO */
		return ERROR_ELEMENT;
	}

	/* Faz mover HEROI */
	public int moveHero(char key) {
		if (!running)
			return 2;
		int option = 0;
		map.getHero().setOldXY(map.getHero().getX(), map.getHero().getY());// guarda coordenadas antigas
		switch (Character.toUpperCase(key)) {
		case 'W':
			/* Move with success, go to a sword, kill dragon, or go to exit */
			if (canMove(map.getHero().getX(), map.getHero().getY() - 1, map.getHero().getSkin())) {
				map.setToMap(map.getHero().getX(), map.getHero().getY() - 1, map.getHero().getSkin());
				map.resetPosition(map.getHero().getX(), map.getHero().getY()); // put the ' ' in the past position
				map.getHero().moveUp();// update position of hero
			}
			break;

		case 'D':
			if (canMove(map.getHero().getX() + 1, map.getHero().getY(), map.getHero().getSkin())) {
				map.setToMap(map.getHero().getX() + 1, map.getHero().getY(), map.getHero().getSkin());
				map.resetPosition(map.getHero().getX(), map.getHero().getY());
				map.getHero().moveRight();
			}
			break;

		case 'S':
			if (canMove(map.getHero().getX(), map.getHero().getY() + 1, map.getHero().getSkin())) {
				map.setToMap(map.getHero().getX(), map.getHero().getY() + 1, map.getHero().getSkin());
				map.resetPosition(map.getHero().getX(), map.getHero().getY());
				map.getHero().moveDown();// update position of hero
			}
			break;

		case 'A':
			if (canMove(map.getHero().getX() - 1, map.getHero().getY(), map.getHero().getSkin())) {
				map.setToMap(map.getHero().getX() - 1, map.getHero().getY(), map.getHero().getSkin());
				map.resetPosition(map.getHero().getX(), map.getHero().getY());
				map.getHero().moveLeft();
			}
			break;
		}
		option = gameLogic(map.getHero().getX(), map.getHero().getY(), map.getHero().getSkin());
		/************* DEBUG AREA **************/
		System.out.println("Have SWORD: " + map.getHero().isHaveSword());
		System.out.println("current: " + map.getHero().getX() + " - " + map.getHero().getY() + " | old:"
				+ map.getHero().getOldX() + " - " + map.getHero().getOldY());
		System.out.println("hero option: " + option);
		/*************************************/
		return option;

	}

	/* Faz mover DRAGAO */
	public int moveDragon() {
		if (!map.getDragon().isAlive())
			return -1;
		Random rand = new Random();
		int option;
		map.getDragon().setOldXY(map.getDragon().getX(), map.getDragon().getY());

		while (true) {
			int random = rand.nextInt(4);
			map.getDragon().setXY(map.getDragon().getOldX(), map.getDragon().getOldY());
			switch (random) {
			case 0:
				map.getDragon().moveUp();
				break;
			case 1:
				map.getDragon().moveDown();
				break;
			case 2:
				map.getDragon().moveLeft();
				break;
			case 3:
				map.getDragon().moveRight();
				break;
			}
			/* So move se estiver espaço vazio! */
			if (canMove(map.getDragon().getX(), map.getDragon().getY(), map.getDragon().getSkin())) // move com sucesso
				break;
		}
		map.setToMap(map.getDragon().getX(), map.getDragon().getY(), map.getDragon().getSkin());
		map.resetPosition(map.getDragon().getOldX(), map.getDragon().getOldY());

		option = gameLogic(map.getDragon().getX(), map.getDragon().getY(), map.getDragon().getSkin());
		/************* DEBUG AREA **************/
		System.out.println("dragon option: " + option);
		System.out.println("coord: " + map.getDragon().getX() + " - " + map.getDragon().getY() + " | old: "
				+ map.getDragon().getOldX() + " - " + map.getDragon().getOldY());
		System.out.println("dragon alive: " + map.getDragon().isAlive());
		/*************************************/

		return option;
	}

	/* retorna o estado do jogo */
	public boolean isRunning() {
		return running;
	}

	/*******************************************
	 * FUNÇOES AUXILIARES
	 **********************************************/

	/* retorna true ou false se DRAGAO ou HEROI podem se mover! */
	private boolean canMove(int x, int y, char element) {

		/* HERO */
		if (element == map.getHero().getSkin()) {
			if (map.getCurrentMap()[y][x] == ' ' || map.getCurrentMap()[y][x] == 'E'
					|| map.getCurrentMap()[y][x] == map.getSword().getSkin() || map.getCurrentMap()[y][x] == 'D')
				return true;
		} /* DRAGON */
		else if (element == map.getDragon().getSkin()) {
			if (map.getCurrentMap()[y][x] == ' ' || map.getCurrentMap()[y][x] == map.getSword().getSkin())
				return true;
		}
		return false;
	}

	/* se HEROI estiver numa posiçao adjacente ao DRAGAO ou EM CIMA DELE */
	private boolean adjacentPositionDragon() {
		return ((map.getDragon().getY() == map.getHero().getY() && map.getDragon().getX() == map.getHero().getX())
				|| (map.getDragon().getY() == map.getHero().getY() - 1
						&& map.getDragon().getX() == map.getHero().getX())
				|| (map.getDragon().getY() == map.getHero().getY() + 1
						&& map.getDragon().getX() == map.getHero().getX())
				|| (map.getDragon().getX() == map.getHero().getX() - 1
						&& map.getDragon().getY() == map.getHero().getY())
				|| (map.getDragon().getX() == map.getHero().getX() - 1
						&& map.getDragon().getY() == map.getHero().getY()));
	}

	private boolean heroToExit() {
		return map.getHero().getX() == map.getExit().getX() && map.getHero().getY() == map.getExit().getY();
	}

	private void resetExitAndHero(char elementToExit) {
		map.setToMap(map.getExit().getX(), map.getExit().getY(), elementToExit); // abre porta
		map.setToMap(map.getHero().getOldX(), map.getHero().getOldY(), map.getHero().getSkin()); // reset ao icon da
																									// exit
		map.getHero().setXY(map.getHero().getOldX(), map.getHero().getOldY());// reset às coordenadas do HEROI
	}
	/*************************************************************************************************************/

}

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

	public Game(int numDragons) {
		this.map = new Map(numDragons);
		running = true;
	}

	public Map getMap() {
		return map;
	}

	/*
	 * verifica a logica do jogo, dragao morre, dragao mata heroi, heroi apanha
	 * espada...etc
	 */
	private int gameLogic() {
		int returnValue = ERROR_ELEMENT;
		boolean dragonPickedSword = false;
		for (int i = 0; i < map.getDragonList().size(); i++) {
			Dragon dragon_i = map.getDragonList().get(i);
			/* morre para dragao, posiçao adjacente */
			if ((adjacentPositionDragon(dragon_i) && dragon_i.isAlive() && !map.getHero().isHaveSword())) {
				this.running = false;
				return DRAGON_AREA;
			}
			/* heroi posiçao adjacente com espada -> MATA DRAGAO */
			else if (adjacentPositionDragon(dragon_i) && dragon_i.isAlive() && map.getHero().isHaveSword()) {
				if(chanceOfTeleport(2)) {
					teleport(dragon_i);
					i--;//verifica a logica do game para este que teletransportou
				}else {
					dragon_i.setAlive(false);// mata dragao
					map.setToMap(dragon_i.getX(), dragon_i.getY(), ' ');// elimina dragao do mapa
					map.getDragonList().remove(i);
					if (map.getDragonList().size() == 0)
						map.getHero().setHaveKey(true);// mete chaves no heroi
					returnValue = DRAGON_KILLED;
				}
				returnValue = SUCCESS;
				
			}
			/* DRAGAO em cima da ESPADA */
			else if (dragon_i.getX() == map.getSword().getX() && dragon_i.getY() == map.getSword().getY() && map.getSword().isInMap()) {
				dragon_i.setSkin('F');
				dragonPickedSword = true;
				map.setToMap(dragon_i.getX(), dragon_i.getY(), 'F');
				returnValue = DRAGON_IN_SWORD;
			}
			/* DRAGAO NAO ESTÁ em cima da ESPADA OU movido com sucesso */
			else if ((dragon_i.getX() != map.getSword().getX() || dragon_i.getY() != map.getSword().getY()))																					
			{
				if (dragon_i.isAlive()) {
					dragon_i.setSkin('D');
					map.setToMap(dragon_i.getX(), dragon_i.getY(), 'D');
				}
				if (!map.getHero().isHaveSword() && !dragonPickedSword)
					map.setToMap(map.getSword().getX(), map.getSword().getY(), map.getSword().getSkin());
				returnValue = SUCCESS;
			}
		}
		/* HEROI apanha ESPADA */
		if (map.getHero().getX() == map.getSword().getX() && map.getHero().getY() == map.getSword().getY() && map.getSword().isInMap()) {
			map.getHero().setHaveSword(true);
			map.getHero().setSkin('A');// atualiza variavel skin no hero
			map.setToMap(map.getHero().getX(), map.getHero().getY(), map.getHero().getSkin());// Atualiza skin
			map.getSword().setInMap(false);
			returnValue = SWORD_ELEMENT;
		}
		/* HEROI tenta sair sem chaves */
		else if (heroToExit() && !map.getHero().isHaveKey()) {// tenta sair sem chave
			/* o heroi entra na saida, entao temos de dar reset e voltar a meter para trás*/
			resetExitAndHero(map.getExit().getSkin()); // coloca o "E" no mesmo sitio, nao altera saida
			returnValue = ERROR_WALL;
		}
		/* HEROI sai com chaves, bate à porta */
		else if (heroToExit() && map.getHero().isHaveKey() && !map.getHero().isKnockDoor()) {
			resetExitAndHero(' ');// abre porta da saida
			map.getHero().setKnockDoor(true);
			returnValue = KNOCKDOOR;
		}
		/* HEROI sai com sucesso */
		else if (heroToExit() && map.getHero().isHaveKey() && map.getHero().isKnockDoor()) {
			this.running = false;
			returnValue = EXIT_ELEMENT;
		}

		/* HOUVE ALGUM ERRO */
		return returnValue;
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
		option = gameLogic();
		return option;

	}

	/* Faz mover DRAGAO */
	public int moveDragon() {
		if (map.getDragonList().size() == 0)
			return -1;
		int option;
		
		/* 	Começa ciclo para todos os DRAGOES	*/
		for (int i = 0; i < map.getDragonList().size(); i++) {
			Dragon dragon_i = map.getDragonList().get(i);
			
			if(chanceOfTeleport(1)) {//dá teleporte
				teleport(dragon_i);
			}else {					//move normal
				moveDragonNormal(dragon_i);
			}

		}

		/* termina ciclo */

		option = gameLogic();

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
		else if (element == 'D' || element == 'F') {
			if (map.getCurrentMap()[y][x] == ' ' || map.getCurrentMap()[y][x] == map.getSword().getSkin())
				return true;
		}
		return false;
	}

	/* se HEROI estiver numa posiçao adjacente ao DRAGAO ou EM CIMA DELE */
	private boolean adjacentPositionDragon(Dragon dragon_i) {
		return ((dragon_i.getY() == map.getHero().getY() && dragon_i.getX() == map.getHero().getX())
				|| (dragon_i.getY() == map.getHero().getY() - 1 && dragon_i.getX() == map.getHero().getX())
				|| (dragon_i.getY() == map.getHero().getY() + 1 && dragon_i.getX() == map.getHero().getX())
				|| (dragon_i.getX() == map.getHero().getX() + 1 && dragon_i.getY() == map.getHero().getY())
				|| (dragon_i.getX() == map.getHero().getX() - 1 && dragon_i.getY() == map.getHero().getY()));
	}

	/* se HEROI estiver na saida */
	private boolean heroToExit() {
		return map.getHero().getX() == map.getExit().getX() && map.getHero().getY() == map.getExit().getY();
	}

	/*
	 * Dá reset aos elementos HEROI , SAIDA, pois heroi tem de abrir a porta
	 * primeiro
	 */
	private void resetExitAndHero(char elementToExit) {
		map.setToMap(map.getExit().getX(), map.getExit().getY(), elementToExit); // abre porta
		map.setToMap(map.getHero().getOldX(), map.getHero().getOldY(), map.getHero().getSkin()); // reset ao icon da //
																									// exit
		map.getHero().setXY(map.getHero().getOldX(), map.getHero().getOldY());// reset às coordenadas do HEROI
	}

	/* 1 para verificar se tem 20% chance | 2 para verificar se tem 35% chance */
	private boolean chanceOfTeleport(int value) {
		Random rand = new Random();
		int x = rand.nextInt(100) + 1;// 1-100
		if (value == 1) {
			return (x <= 20);
		} else if (value == 2) {
			return (x <= 35);
		}
		return false;
	}

	private boolean teleport(Dragon dragon_i) {
		Random rand = new Random();
		int aux=0;
		int x = dragon_i.getX();
		int y = dragon_i.getY();
		boolean validation=true;
		dragon_i.setOldXY(dragon_i.getX(), dragon_i.getY());//guarda variaveis antigas

		while (true) {
			aux++;
			x = rand.nextInt(8) + 1;
			y = rand.nextInt(8) + 1;

			/* So move se estiver espaço vazio! */
			if (canMove(x, y, dragon_i.getSkin())) // move com sucesso
			{
				break;
			} else if (aux >= 50) {
				validation = false;
				break;
			}
		}
				/* Significa que nao excedeu o limite no ciclo (100) */
		if (validation) {
			dragon_i.setXY(x, y);
			map.setToMap(dragon_i.getX(), dragon_i.getY(), dragon_i.getSkin());
			map.resetPosition(dragon_i.getOldX(), dragon_i.getOldY());
		}

		return validation;
	}
	private boolean moveDragonNormal(Dragon dragon_i) {
		Random rand = new Random();
		int x = 0;
		boolean validation = true;
		dragon_i.setOldXY(dragon_i.getX(), dragon_i.getY());//guarda variaveis antigas

		while (true) {
			int random = rand.nextInt(4);
			dragon_i.setXY(dragon_i.getOldX(), dragon_i.getOldY());
			switch (random) {
			case 0:
				dragon_i.moveUp();
				break;
			case 1:
				dragon_i.moveDown();
				break;
			case 2:
				dragon_i.moveLeft();
				break;
			case 3:
				dragon_i.moveRight();
				break;
			}
			x++;
			/* So move se estiver espaço vazio! */
			if (canMove(dragon_i.getX(), dragon_i.getY(), dragon_i.getSkin())) // move com sucesso
			{
				break;
			} else if (x >= 50) {
				dragon_i.setXY(dragon_i.getOldX(), dragon_i.getOldY());
				validation = false;
				break;
			}

		}
		/* Significa que nao excedeu o limite no ciclo (100) */
		if (validation) {
			map.setToMap(dragon_i.getX(), dragon_i.getY(), dragon_i.getSkin());
			map.resetPosition(dragon_i.getOldX(), dragon_i.getOldY());
		}
		return validation;
	}
	/*************************************************************************************************************/

}

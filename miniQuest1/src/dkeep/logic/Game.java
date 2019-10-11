package dkeep.logic;

public class Game {

	private final int SUCCESS = 1;
	private final int EXIT_ELEMENT = 2;
	private final int SWORD_ELEMENT = 3;
	private final int DRAGON_AREA = 4;
	private final int DRAGON_KILLED = 5;
	private final int KNOCKDOOR = 6;
	private final int ERROR_WALL = -1;
	private final int ERROR_ELEMENT = -2;
	private final int DRAGON_ELEMENT = -3;
	
	private Map map;
	private boolean running;// estado do jogo, a correr ou fim de jogo

	public Game() {
		this.map = new Map();

		running = true;
	}

	public Map getMap() {
		return map;
	}

	private int gameLogic(int x, int y, char element) {

		if (((map.getDragon().getY() == y - 1 && map.getDragon().getX() == x) || (map.getDragon().getY() == y + 1 && map.getDragon().getX() == x)
				|| (map.getDragon().getX() == x - 1 && map.getDragon().getY() == y) || (map.getDragon().getX() == x - 1 && map.getDragon().getY() == y) 
				&& element != map.getSword().getSkin()) && !map.getHero().isHaveSword()) {// morre para dragao, posiçao adjacente
			this.running=false;
			System.out.println(map.getHero().isHaveSword());
			return DRAGON_AREA;
		}else if ((map.getDragon().getY() == y - 1 && map.getDragon().getX() == x) || (map.getDragon().getY() == y + 1 && map.getDragon().getX() == x)
				|| (map.getDragon().getX() == x - 1 && map.getDragon().getY() == y) || (map.getDragon().getX() == x - 1 && map.getDragon().getY() == y) 
				&& element != map.getSword().getSkin() && map.getHero().isHaveSword()) {// mata dragao
			map.getDragon().setAlive(false);
			map.setToMap(map.getDragon().getX(), map.getDragon().getY(), ' ');
			map.getHero().setHaveKey(true);
			return DRAGON_KILLED;
		} else if (map.getCurrentMap()[y][x] == ' ') { // move com sucesso
			return SUCCESS;
		} else if (map.getCurrentMap()[y][x] == 'x') {//move contra parede
			return ERROR_WALL;
		} else if (map.getCurrentMap()[y][x] == map.getDragon().getSkin()) {// morre para dragao, posiçao do dragon
			this.running=false;
			return DRAGON_ELEMENT;
		} else if (map.getCurrentMap()[y][x] == map.getSword().getSkin()) {// pega espada
			map.getHero().setHaveSword(true);
			map.getHero().setSkin('A');
			return SWORD_ELEMENT;
		} else if (map.getCurrentMap()[y][x] == 'E' && !map.getHero().isHaveKey()) {//tenta sair sem chave
			return ERROR_WALL;
		} else if (map.getCurrentMap()[y][x] == 'E' && map.getHero().isHaveKey() && !map.getHero().isKnockDoor()) {// dá knock na porta com chave
			map.getHero().setKnockDoor(true);
			return KNOCKDOOR;
		} else if (map.getCurrentMap()[y][x] == 'E' && map.getHero().isHaveKey() && map.getHero().isKnockDoor()) {//sai com sucesso
			this.running=false;
			return EXIT_ELEMENT;
		}
		return ERROR_ELEMENT;
	}

	/*Move hero*/
	public int moveHero(char key) {
		/*
		 * heroCoord[0] -> X heroCoord[1] -> y
		 */
		System.out.println("Have SWORD: " + map.getHero().isHaveSword());
		if (!running)
			return 2;
		int option = 0;
		switch (Character.toUpperCase(key)) {
		case 'W':
			option = gameLogic(map.getHero().getX(), map.getHero().getY() - 1, map.getHero().getSkin()); // move to
																											// the new																					// position
			/* Move with success, go to a sword, kill dragon, or go to exit */
			if (option >= 1 && option <= 5) {
				map.setToMap(map.getHero().getX(), map.getHero().getY() - 1, map.getHero().getSkin());
				map.resetPosition(map.getHero().getX(), map.getHero().getY()); // put the ' ' in the past position
				map.getHero().moveUp();// update position of hero
			}

			break;

		case 'D':
			option = gameLogic(map.getHero().getX() + 1, map.getHero().getY(), map.getHero().getSkin());

			if (option >= 1 && option <= 5) {
				map.setToMap(map.getHero().getX() + 1, map.getHero().getY(), map.getHero().getSkin());
				map.resetPosition(map.getHero().getX(), map.getHero().getY());
				map.getHero().moveRight();
			}

			break;

		case 'S':
			option = gameLogic(map.getHero().getX(), map.getHero().getY() + 1, map.getHero().getSkin());

			if (option >= 1 && option <= 5) {
				map.setToMap(map.getHero().getX(), map.getHero().getY() + 1, map.getHero().getSkin());
				map.resetPosition(map.getHero().getX(), map.getHero().getY());
				map.getHero().moveDown();// update position of hero
			}
			break;
		case 'A':
			option = gameLogic(map.getHero().getX() - 1, map.getHero().getY(), map.getHero().getSkin());

			if (option >= 1 && option <= 5) {
				map.setToMap(map.getHero().getX() - 1, map.getHero().getY(), map.getHero().getSkin());
				map.resetPosition(map.getHero().getX(), map.getHero().getY());
				map.getHero().moveLeft();
			}
			break;
		}
		System.out.println(option);
		return option;

	}


	public boolean isRunning() {
		return running;
	}

}

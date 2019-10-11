package dkeep.logic;

import java.util.Random;

public class Map {


	private char initialMap[][] = { { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
			{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' }, { 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
			{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', 'E' }, { 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
			{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' }, { 'x', ' ', 'x', 'x', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };
	private char currentMap[][];
	private Dragon dragon;
	private Hero hero;
	private Item sword;

	/* Constructor */
	public Map() {
		this.currentMap = new char[10][10];
		resetCurrentMap();
		this.hero = new Hero('H');
		this.dragon = new Dragon('D');
		this.sword = new Item('S');
		putHero();
		putDragon();
		putSword();

	}
	/* put the hero in the map */
	private void putHero() {
		/* first coordinates */
		setToMap(1, 1, getHero().getSkin());
		getHero().setXY(1, 1);
		getHero().setAlive(true);
	}

	/* put the dragon in the map */
	private void putDragon() {
		setToMap(1, 3, getDragon().getSkin());
		getDragon().setXY(1, 3);
		getDragon().setAlive(true);

	}

	/* put the key in the map */
	private void putSword() {
		Random rand = new Random();
		int x;
		int y;
		do {
			// Obtain a number between [0 - 9].
			x = rand.nextInt(10);
			y = rand.nextInt(10);
			System.out.println("is free: "+ isFree(x, y));
			System.out.println("is isCloseFromDragon: "+ isCloseFromDragon(x, y));
			System.out.println(x+ " - "+ y);
		} while (!isFree(x, y) || isCloseFromDragon(x, y));// fazer se nao tiver espaço em branco, ou se estiver perto do dragon
		setToMap(x, y, sword.getSkin());
		sword.setXY(x, y);
	}
	public char[][] getInitialMap() {
		return initialMap;
	}

	public char[][] getCurrentMap() {
		return currentMap;
	}

	public void resetPosition(int x, int y) {
		this.currentMap[y][x] = ' ';
	}

	/* Put element in the Map */
	public int setToMap(int x, int y, char element) {
		this.currentMap[y][x] = element;
		return 1;
	}

	/* COPY initialMap TO currentMap */
	private void resetCurrentMap() {
		for (int i = 0; i < this.initialMap.length; i++) {
			for (int j = 0; j < this.initialMap[0].length; j++) {
				this.currentMap[i][j] = this.initialMap[i][j];
			}
		}
	}

	public Dragon getDragon() {
		return dragon;
	}

	public Hero getHero() {
		return hero;
	}

	public Item getSword() {
		return sword;
	}

	private boolean isCloseFromDragon(int x, int y) {
		return ((dragon.getY() == y && dragon.getX() == x) || (dragon.getY() - 1 == y && dragon.getX() == x) || (dragon.getY() + 1 == y && dragon.getX() == x)
				|| (dragon.getX() + 1 == x && dragon.getY() == y) || (dragon.getX() - 1 == x && dragon.getY() == y));
		
	}
	private boolean isFree(int x, int y) {
		return currentMap[y][x] == ' ';
	}
	
}

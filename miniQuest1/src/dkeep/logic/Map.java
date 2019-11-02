package dkeep.logic;

import java.util.ArrayList;
import java.util.Random;

public class Map {


	private char initialMap[][] = { { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
									{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, 
									{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
									{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' }, 
									{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
									{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', 'E' }, 
									{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
									{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' }, 
									{ 'x', ' ', 'x', 'x', ' ', ' ', ' ', ' ', ' ', 'x' },
									{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };
	private char currentMap[][];
 	private Hero hero;
	private Item sword;
	private Item exit;
	private ArrayList<Dragon> dragonList;

	/* Constructor */
	public Map(int dragonsNumber) {
		this.currentMap = new char[10][10];
		resetCurrentMap();
		dragonList = new ArrayList<>();//Cria lista de dragoes
		
		this.hero = new Hero('H');
		this.exit = new Item('E');
		this.sword = new Item('S');

		putHero();
		putDragons(dragonsNumber);// coloca na lista de dragoes
		putSword();
		putExit();
	}
	/* put the hero in the map */
	private void putHero() {
		/* first coordinates */
		setToMap(1, 1, this.hero.getSkin());
		this.hero.setXY(1, 1);
		this.hero.setAlive(true);
	}

	/* put the dragon in the map */
	private void putDragons(int dragonsNumber) {
		Random rand = new Random();

		for(int i = 0; i < dragonsNumber; i++) {
			dragonList.add(new Dragon('D'));

			int x;
			int y;
			do {
				// Obtain a number between [1 - 8].
				x = rand.nextInt(8)+1;
				y = rand.nextInt(8)+1;
			} while (!isFree(x, y) || isCloseFromHero(x, y));// fazer se nao tiver espaço em branco, ou se estiver perto do dragon
			
			
			setToMap(x, y, dragonList.get(i).getSkin());
			dragonList.get(i).setXY(x, y);
			dragonList.get(i).setAlive(true);
		}


	}

	/* put the key in the map */
	private void putSword() {
		Random rand = new Random();
		int x;
		int y;
		do {
			// Obtain a number between [0 - 9].
			x = rand.nextInt(8)+1;
			y = rand.nextInt(8)+1;
		} while (!isFree(x, y) || isCloseFromDragon(x, y));// fazer se nao tiver espaço em branco, ou se estiver perto do dragon
		setToMap(x, y, sword.getSkin());
		sword.setXY(x, y);
		sword.setInMap(true);
	}
	/*coloca coordenadas da saida*/
	private void putExit() {
		exit.setXY(9, 5);
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

	
	public ArrayList<Dragon> getDragonList() {
		return dragonList;
	}
	public Hero getHero() {
		return hero;
	}
	public Item getSword() {
		return sword;
	}
	public Item getExit() {
		return exit;
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


	private boolean isCloseFromDragon(int x, int y) {
		for(int i = 0; i < dragonList.size(); i++) {
			Dragon dragon_i = dragonList.get(i);
			if((dragon_i.getY() == y && dragon_i.getX() == x) || (dragon_i.getY() - 1 == y && dragon_i.getX() == x) || (dragon_i.getY() + 1 == y && dragon_i.getX() == x)
					|| (dragon_i.getX() + 1 == x && dragon_i.getY() == y) || (dragon_i.getX() - 1 == x && dragon_i.getY() == y))
				return true;
		}
		return false;
		
	}
	private boolean isCloseFromHero(int x, int y) {
		return ((hero.getY() == y && hero.getX() == x) || (hero.getY() - 1 == y && hero.getX() == x) || (hero.getY() + 1 == y && hero.getX() == x)
				|| (hero.getX() + 1 == x && hero.getY() == y) || (hero.getX() - 1 == x && hero.getY() == y));
	}
	public boolean isFree(int x, int y) {
		return currentMap[y][x] == ' ';
	}
	
}

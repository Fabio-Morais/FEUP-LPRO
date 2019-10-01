package miniQuest1;

import java.lang.*;
import java.util.Random;

public class Game {
	private char map[][] = { { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
							 { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, 
							 { 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
						  	 { 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' }, 
							 { 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
						 	 { 'x', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', 'E' }, 
							 { 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
							 { 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' }, 
							 { 'x', ' ', 'x', 'x', ' ', ' ', ' ', ' ', ' ', 'x' },
							 { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };
	private int sizeMap;
	/*
	 * 0-> X 1-> Y
	 */
	private int[] heroCoord;
	private boolean running;
	private boolean key;
	private boolean knockDoor;
	
	public Game() {
		this.heroCoord = new int[2];
		this.sizeMap = 10;
		putHero();
		putDragon();
		putKey();
		running = true;
		key= false;
		knockDoor=false;
	}
	
	/* print all map to the user*/
	public void printMap() {
		System.out.println("\t\t\tMini Quest 1");
		for (int i = 0; i < sizeMap; i++) {
			System.out.print("\t\t");

			for (int j = 0; j < sizeMap; j++) {
				System.out.print(map[i][j] + "  ");
			}
			System.out.println();
		}
	}
	
	/*put the hero in the map*/
	private void putHero() {

		this.map[1][1] = 'H';
		/* first coordinates */
		this.heroCoord[0] = 1;
		this.heroCoord[1] = 1;
	}
	/*put the dragon in the map*/
	private void putDragon() {
		this.map[3][1] = 'D';
	}
	/*put the key in the map*/
	private void putKey() {
		Random rand = new Random();
		int x;
		int y;
		do{
			// Obtain a number between [0 - 9].
		x = rand.nextInt(10);
		y = rand.nextInt(10);
		}while(!freeSpace(x, y) || !isAwayDragon(x,y));
		this.map[y][x] = 'K';
	}

	/*
	 *  0-> mover contra parede 
		1-> movido com sucesso 
		2-> chegou à saida
		3-> saida sem chave
		4-> morreu para dragao
	 */
	private int action(int x, int y) {
		if (map[y][x] == 'x')
			return 0;
		else if ((map[y][x] == 'E') && this.key && !this.knockDoor) {
			System.out.println("YOU knock");
			map[5][9]=' ';
			this.knockDoor = true;
			return 0;
		}else if ((y==5 && x==9) && this.key && this.knockDoor) {
			System.out.println("YOU WIN");
			this.running = false;
			return 2;
		}else if (map[y][x] == 'E' && !this.key) {
			System.out.println("YOU DON'T HAVE THE KEY");
			return 3;
		} else if (map[y][x] == 'K') {
			this.key = true;
			return 1;
		} else if(map[y+1][x] == 'D' || map[y-1][x] == 'D' || map[y][x+1] == 'D' || map[y][x-1] == 'D') {
			System.out.println("DRAGON KILLS YOU");
			this.running = false;

			return 4;
		}
		return 1;
	}
	
	/*
	 *  0-> mover contra parede 
		1-> movido com sucesso 
		2-> chegou à saida
	 */
	public int moveHero(char key) {
		/*
		 * heroCoord[0] -> X heroCoord[1] -> y
		 */
		if (!running)
			return 2;
		int option=0;
		switch (Character.toUpperCase(key)) {
		case 'W':
			option = action(this.heroCoord[0], this.heroCoord[1] - 1);
			/*mover com sucesso ou pegar chave ou ir para dragao*/
			if(option == 1 || option == 2 || option == 4) {
				/* 					X,				 Y, 				NEW X, 				NEW Y */
				updateCoord(this.heroCoord[0], this.heroCoord[1], this.heroCoord[0], this.heroCoord[1] - 1);
				this.heroCoord[1]--;// atualiza coordenadas hero
			}
			return option;

		case 'D':
			option = action(this.heroCoord[0] + 1 , this.heroCoord[1]);
			/*mover com sucesso ou pegar chave*/
			if(option == 1 || option == 2 || option == 4) {
				/* 					X,				 Y, 				NEW X, 				NEW Y */
				updateCoord(this.heroCoord[0], this.heroCoord[1], this.heroCoord[0] + 1, this.heroCoord[1]);
				this.heroCoord[0]++;// atualiza coordenadas hero
			}
			return option;

		case 'S':
			option = action(this.heroCoord[0], this.heroCoord[1] + 1);
			/*mover com sucesso ou pegar chave*/
			if(option == 1 || option == 2 || option == 4)  {
				/* 					X,				 Y, 				NEW X, 				NEW Y */
				updateCoord(this.heroCoord[0], this.heroCoord[1], this.heroCoord[0], this.heroCoord[1] + 1);
				this.heroCoord[1]++;// atualiza coordenadas hero
			}
			return option;
		case 'A':
			option = action(this.heroCoord[0] - 1 , this.heroCoord[1]);
			/*mover com sucesso ou pegar chave*/
			if(option == 1 || option == 2 || option == 4) {
				/* 					X,				 Y, 				NEW X, 				NEW Y */
				updateCoord(this.heroCoord[0], this.heroCoord[1], this.heroCoord[0] - 1, this.heroCoord[1]);
				this.heroCoord[0]--;// atualiza coordenadas hero
			}
			return option;
		}

			return 0;

	}

	private boolean freeSpace(int x, int y) {
		return (map[y][x] == ' ');
	}
	private boolean isAwayDragon(int x, int y) {
		/*[3][1]-> dragon*/
		return ((y<3-1 || y>3+1) && (x>1+1));
	}
	
	private void updateCoord(int x, int y, int xNew, int yNew) {
		map[y][x] = ' ';
		map[yNew][xNew] = 'H';
	}

	public boolean isRunning() {
		return running;
	}
	
}

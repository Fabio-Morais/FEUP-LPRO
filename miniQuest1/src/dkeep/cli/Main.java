package dkeep.cli;

import java.util.Scanner;
import dkeep.logic.Game;

public class Main {
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args){
		
		Game miniQuest = new Game(readUserDragons());

		printMap(miniQuest);
		
		while (true) {
			statsOfGame(miniQuest);
			miniQuest.moveHero(readUserDirection());
			if(!miniQuest.isRunning()) {
				printMap(miniQuest);
				break;

			}
			miniQuest.moveDragon();
			printMap(miniQuest);

			if(!miniQuest.isRunning())
				break;
		}
		System.out.println("THANKS FOR PLAYING\n Fábio Morais");
	}
	
	/* print all map to the user*/
	private static void printMap(Game miniQuest) {
		System.out.println("\t\t\tMini Quest 1");
		for (int i = 0; i < 10; i++) {
			System.out.print("\t\t");
			for (int j = 0; j < 10; j++) {
				System.out.print(miniQuest.getMap().getCurrentMap()[i][j] + "  ");
			}
			System.out.println();
		}
	}

	private static char readUserDirection() {
		String c;
		do {
			c = scanner.nextLine();
		}while(c.length()<=0);
		//scanner.close();
		return c.charAt(0);
	}
	private static int readUserDragons() {
		int x;
		do {
			System.out.println("Enter number of dragons (1-10) ");
			while (!scanner.hasNextInt()) 
				scanner.nextLine();
			x = scanner.nextInt();
		}while(!(x>=1 && x<=10));
		return x;
		
	}
	private static void statsOfGame(Game miniQuest) {
		System.out.println("Have KEY: " + miniQuest.getMap().getHero().isHaveKey());
		System.out.println("Dragons left: "+ miniQuest.getMap().getDragonList().size());

	}
}

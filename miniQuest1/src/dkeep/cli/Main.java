package dkeep.cli;

import java.util.Scanner;
import dkeep.logic.Game;

public class Main {
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args){
		Game miniQuest = new Game();

		printMap(miniQuest);
		
		while (true) {
			miniQuest.moveHero(readUserDirection());
			//printMap(miniQuest);
			miniQuest.moveDragon();
			printMap(miniQuest);

			if(!miniQuest.isRunning())
				break;
		}
		System.out.println("THANKS FOR PLAYING\n Fábio Morais");
	}
	
	/* print all map to the user*/
	public static void printMap(Game miniQuest) {
		System.out.println("\t\t\tMini Quest 1");
		for (int i = 0; i < 10; i++) {
			System.out.print("\t\t");
			for (int j = 0; j < 10; j++) {
				System.out.print(miniQuest.getMap().getCurrentMap()[i][j] + "  ");
			}
			System.out.println();
		}
	}

	public static char readUserDirection() {
		String c;
		do {
			c = scanner.nextLine();
		}while(c.length()<=0);
		//scanner.close();
		return c.charAt(0);
	}
}

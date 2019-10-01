package miniQuest1;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Game miniQuest = new Game();

		miniQuest.printMap();
		
		while (true) {
			miniQuest.moveHero(readUserDirection());
			miniQuest.printMap();
			if(!miniQuest.isRunning())
				break;
		}
		System.out.println("THANKS FOR PLAYING\n Fábio Morais");
	}

	public static char readUserDirection() {
		Scanner scanner = new Scanner(System.in);
		String c;
		do {
			c = scanner.nextLine();
		}while(c.length()<=0);
		return c.charAt(0);
	}
}

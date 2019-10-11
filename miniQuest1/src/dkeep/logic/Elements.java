package dkeep.logic;

public class Elements {
	private int x;
	private int y;
	private char skin;
	



	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public char getSkin() {
		return skin;
	}

	public void setSkin(char skin) {
		this.skin = skin;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
			
	}

	public void moveLeft() {
		this.x--;

	}

	public void moveRight() {
		this.x++;
	
	}

	public void moveUp() {
		this.y--;
		
	}

	public void moveDown() {
		this.y++;

	}

}

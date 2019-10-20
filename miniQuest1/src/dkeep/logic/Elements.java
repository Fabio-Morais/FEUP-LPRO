package dkeep.logic;

public class Elements {
	private int x;
	private int y;
	private int oldX;
	private int oldY;
	private char skin;
	

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
	public int getOldX() {
		return oldX;
	}



	public int getOldY() {
		return oldY;
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
	public void setOldXY(int oldX, int oldY) {
		this.oldX = oldX;
		this.oldY = oldY;

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

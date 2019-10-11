package dkeep.logic;

public class Dragon extends Elements{
	private boolean isAlive;

	public Dragon(char skin) {
		setSkin(skin);
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	
	
	
}

package dkeep.logic;

public class Item extends Elements{
	private boolean isInMap;
	
	public boolean isInMap() {
		return isInMap;
	}

	public void setInMap(boolean isInMap) {
		this.isInMap = isInMap;
	}

	public Item(char skin) {
		setSkin(skin);
	}
}

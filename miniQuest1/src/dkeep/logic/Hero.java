package dkeep.logic;

public class Hero extends Elements {
	private boolean isAlive;
	private boolean haveKey;
	private boolean haveSword;
	private boolean knockDoor;

	
	public Hero(char skin) {
		setSkin(skin);
		this.haveKey = false;
		this.haveSword = false;
		this.isAlive = true;
		this.knockDoor = false;
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isHaveKey() {
		return haveKey;
	}

	public void setHaveKey(boolean haveKey) {
		this.haveKey = haveKey;
	}

	public boolean isHaveSword() {
		return haveSword;
	}

	public void setHaveSword(boolean haveSword) {
		this.haveSword = haveSword;
	}

	public boolean isKnockDoor() {
		return knockDoor;
	}

	public void setKnockDoor(boolean knockDoor) {
		this.knockDoor = knockDoor;
	}
	
	
}

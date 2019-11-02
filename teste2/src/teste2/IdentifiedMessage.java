package teste2;

public class IdentifiedMessage extends Message {

	private String user;
	
	public IdentifiedMessage(String string1, String string2) {
		super(string1, string2);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		setUserMessage(user);
		this.user = user;
	}


}

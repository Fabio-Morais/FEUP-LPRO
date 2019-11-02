package teste2;

import java.util.ArrayList;
import java.util.HashMap;

public class Conversation {
	private int maxNumberOfUsers;
	private HashMap<String,String> users;
	private ArrayList<Message> message;

	public Conversation(int maxNumberOfUsers) {
		this.maxNumberOfUsers = maxNumberOfUsers;
		this.users = new HashMap<>();
		this.message = new ArrayList<>();
	}

	public int getMaxNumberOfUsers() {
		return maxNumberOfUsers;
	}

	public void setMaxNumberOfUsers(int maxNumberOfUsers) {
		this.maxNumberOfUsers = maxNumberOfUsers;
	}

	public int getUserCount() {
		return users.size();
	}

	public void addUser(String user) {
		
		if(this.users.get(user) == null && this.users.size()<maxNumberOfUsers)
			this.users.put(user,user);
	}

	public void addMessage(IdentifiedMessage identifiedMessage) {
		if(this.users.get(identifiedMessage.getUserMessage()) != null)
			{
			this.message.add(identifiedMessage);
			}
	}

	public int getMessageCount() {
		return this.message.size();
	}

	public void removeUser(String string) {
		this.users.remove(string);
		
	}

	public ArrayList<Message> getUserMessages(String string) {
		ArrayList<Message> aux = new ArrayList<>();
		
		for(int i=0; i< message.size(); i++) {
			if(message.get(i).getUserMessage().equals(string)) {
				aux.add(new Message(message.get(i).getMessage2()));
			}
		}
		return aux;
	}






	
	
}

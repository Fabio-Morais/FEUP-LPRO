package teste2;

public class Message implements Comparable<Message>{
	private String message;
	private String usermessage;
	private String message2;
	private MessageFormatter formattingStrategy;
	private int printOption;
	
	public Message(String message) {
		this.message = message;
		usermessage="";
		printOption=1;
	}
	public Message(String user, String message2) {
		this.usermessage = user;
		this.message2 = message2;
		this.message = "["+user +"]: "+message2;
		printOption=2;
	}
	public Message(Message message) {
		this.message = message.getMessage();
		usermessage="";
		printOption=1;
	}
	
	public String getMessage() {
		if(usermessage.length()==0) {
			return message;
		}
		else {
			this.message = "["+usermessage +"]: "+message2;
			return message;
		}
			
	}

	public String getUserMessage() {
		return usermessage;
	}
	public void setUserMessage(String user) {
		this.usermessage = user;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageFormatter getFormattingStrategy() {
		return formattingStrategy;
	}
	public void setFormattingStrategy(MessageFormatter formattingStrategy) {
		this.formattingStrategy = formattingStrategy;
		if(formattingStrategy instanceof Shout) {
			if(printOption == 2)
				this.message2 = this.message2.toUpperCase();
			else
				this.message = this.message.toUpperCase();
		}else if(formattingStrategy instanceof Post) {
			this.message2 = this.message2.toLowerCase();
		}else if(formattingStrategy instanceof Capitalize) {
			String aux[];
			if(printOption == 2) {
				this.message2=this.message2.toLowerCase();
				aux=this.message2.split(" ");
			}
				
			else {
				this.message = this.message.toLowerCase();
				aux=this.message.split(" ");
			}
				

			
			this.message2="";
			for(int i=0; i<aux.length; i++) {
				aux[i]=aux[i].replaceFirst(aux[i].charAt(0)+"", Character.toUpperCase(aux[i].charAt(0))+"");

				if(i<aux.length-1)
					this.message2+=aux[i]+ " ";
				else
					this.message2+=aux[i];

			}

			if(printOption==1)
				this.message= this.message2;
		}
	}
	@Override
	public boolean equals(Object obj) {
		return this.message.equals(((Message)obj).getMessage()) ;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(printOption==1)
			return "msg:["+this.message+"]";
		else
			return "user:["+usermessage+"], msg:["+message2+"]";
	}
	public int compareTo(Message anotherString) {
		return message.compareTo(anotherString.getMessage());
	}
	public String getMessage2() {
		return message2;
	}

	
}

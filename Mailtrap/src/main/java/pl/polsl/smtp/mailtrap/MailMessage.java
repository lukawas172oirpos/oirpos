package pl.polsl.smtp.mailtrap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MailMessage {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String sender;
	private String receiver;
	@Column(length = 1337)
	private String body="";
	private String date;
	
	
	public void appendBody(String params){
		body+=params + "\n";
	}
	
	public String getBody(){
		return body;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public void setBody(String body){
		this.body = body;
	}

	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public void setDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		this.date = dateFormat.format(date);
		
	}

}
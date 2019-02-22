/*
 * Startup class. Here user decides on which port SMTP server will be listening
 */
package pl.polsl.smtp.mailtrap;

public class Main {

	public static void main(String[] args) {	
		ServerOptions serverOptions = new ServerOptions(25);
        SmtpServerCreator.startServer(serverOptions);   		
	}

}

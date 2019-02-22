/*
 * Class which represents possible server configuration options 
 */
package pl.polsl.smtp.mailtrap;

public class ServerOptions {
    public int port = SmtpServer.DEFAULT_SMTP_PORT;
    public boolean threaded = true;    
    public boolean valid = true;

    public ServerOptions()
    {

    }
    public ServerOptions(int port)
    {
        this.port = port;
    }
}

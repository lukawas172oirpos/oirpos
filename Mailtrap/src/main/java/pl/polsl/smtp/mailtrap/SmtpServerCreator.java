package pl.polsl.smtp.mailtrap;


public class SmtpServerCreator {  

    public static SmtpServer startServer(ServerOptions options) {
        SmtpServer server = wireUpServer(options);
        
        startServerThread(server);
        System.out.println("SMTP Server started on port " + options.port + ".\n");
        return server;
    }

    private static SmtpServer wireUpServer(ServerOptions options) {
        SmtpServer server = new SmtpServer();
        server.setPort(options.port);
        server.setThreaded(options.threaded);        
        return server;
    }

    private static void startServerThread(SmtpServer server) {
        new Thread(server).start();
        int timeout=1000;
        while(! server.isReady()) {
            try {
                Thread.sleep(1);
                timeout--;
                if (timeout < 1) {
                    throw new RuntimeException("Server could not be started.");
                }
            } catch (InterruptedException ignored) {
            }
        }
    }
}

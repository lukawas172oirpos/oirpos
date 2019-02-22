/*
 * Class representing SMTP server. Here, in serverLoop() application is waiting
 * for client to connect. If there is a connection ClientSession is started.
 * In this class objects responsible for communication with data base are 
 * being created. When SMTP session is finished Entity manager is being closed.
 * 
 */
package pl.polsl.smtp.mailtrap;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class SmtpServer implements Runnable {
    public static final int DEFAULT_SMTP_PORT = 25;    
    private static final int MAX_THREADS = 10;
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("mails");
	private EntityManager em = emf.createEntityManager();	

    private volatile boolean stopped = true;
    private volatile boolean ready = false;
    private volatile boolean threaded = false;

    private ServerSocket serverSocket;
    private int port;

    public void run() {
        stopped = false;
        try {
            initializeServerSocket();
            serverLoop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ready = false;
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initializeServerSocket() throws Exception {
        serverSocket = new ServerSocket(port);        
    }

    private void serverLoop() throws IOException {
        int poolSize = threaded ? MAX_THREADS : 1;
        ExecutorService threadExecutor = Executors.newFixedThreadPool(poolSize);
        while (!isStopped()) {
            Socket clientSocket;
            try {
                clientSocket = clientSocket();
            } catch(IOException ex) {
                if (isStopped()) {
                	em.close();
                	emf.close();
                    break;
                } else {
                    throw ex;
                }
            }
            SocketWrapper source = new SocketWrapper(clientSocket);
            ClientSession session = new ClientSession(source, em);            
            threadExecutor.execute(session);
        }
        threadExecutor.shutdown();
        ready = false;
    }

    private Socket clientSocket() throws IOException {
        Socket socket = null;
        while (socket == null) {
            socket = accept();
        }
        return socket;
    }

    private Socket accept() throws IOException {
        ready = true;
        return serverSocket.accept();
    }

    public boolean isStopped() {
        return stopped;
    }

    public synchronized void stop() {
        stopped = true;
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new SmtpServerException(e);
        }
    }

    public static class SmtpServerException extends RuntimeException {
       
		private static final long serialVersionUID = 1L;

		public SmtpServerException(Throwable cause) {
            super(cause);
        }
    }   


    public boolean isReady() {
        return ready;
    }

  
    public void setThreaded(boolean threaded) {
        this.threaded = threaded;
    }    

    public void setPort(int port) {
        this.port = port;
    }    
}

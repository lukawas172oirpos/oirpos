/*
 * Class that handles communication with the client. If certain
 * part of email was received successfully it is saved in adequate part in
 * entity representing mail message.
 * When entirety of email is received, the message is stored in database.
 * */
package pl.polsl.smtp.mailtrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;

import pl.polsl.smtp.mailtrap.util.Parser;

public class ClientSession implements Runnable {

    private IOSource socket;    
    private MailMessage msg;
    private Response smtpResponse;
    private PrintWriter out;
    private BufferedReader input;
    private SmtpState smtpState;
    private String line;
    private EntityManager em;
    
    public ClientSession(IOSource socket, EntityManager em) {
        this.socket = socket;        
        this.msg = new MailMessage();
        this.em = em;
        Request request = Request.initialRequest();
        smtpResponse = request.execute(msg);
    }

    public void run() {
        try {
            prepareSessionLoop();
            sessionLoop();
        } catch (Exception ignored) {
        }
    }

    private void prepareSessionLoop() throws IOException {
        prepareOutput();
        prepareInput();
        sendResponse();
        updateSmtpState();
    }

    private void prepareOutput() throws IOException {
        out = socket.getOutputStream();
        out.flush();
    }

    private void prepareInput() throws IOException {
        input = socket.getInputStream();
    }

    private void sendResponse() {
        if (smtpResponse.getCode() > 0) {
            int code = smtpResponse.getCode();
            String message = smtpResponse.getMessage();
            out.print(code +" "+ message + "\r\n");
            
            out.flush();
        }
    }

    private void updateSmtpState() {
        smtpState = smtpResponse.getNextState();
    }

    private void sessionLoop() throws IOException {
        while (smtpState != SmtpState.CONNECT && readNextLineReady()) {
            Request request = Request.createRequest(smtpState, line);
            smtpResponse = request.execute(msg);
            storeInputInMessage(request);
            sendResponse();
            updateSmtpState();
            saveAndRefreshMessageIfComplete();
        }
    }

    private boolean readNextLineReady() throws IOException {
        readLine();
        return line != null;
    }

    private void readLine() throws IOException {
        line = input.readLine();
    }

    private void saveAndRefreshMessageIfComplete() {
        if (smtpState == SmtpState.QUIT) {    	
    		em.getTransaction().begin();
    		em.persist(msg);
    		em.getTransaction().commit();    		
            msg = new MailMessage();
        }
    }

    private void storeInputInMessage(Request request) {
        String params = request.getParams();
        if (null == params)
            return;
        if (SmtpState.MAIL.equals(smtpState)) {
            msg.setSender(params);
            msg.setDate();
            return;
        }        
        if (SmtpState.RCPT.equals(smtpState)) {
            msg.setReceiver(params);
            return;
        }
        if (SmtpState.DATA_BODY.equals(smtpResponse.getNextState())) {        	
            msg.appendBody(Parser.parseBody(params));        	
            return;
        }
    }

}

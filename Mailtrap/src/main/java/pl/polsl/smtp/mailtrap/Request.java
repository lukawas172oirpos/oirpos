/*
 * Class responsible for creating SMTP commands from the 
 * input provided by client. Also, class ensures that all the 
 * commands are given in correct order.
 * */
package pl.polsl.smtp.mailtrap;

import pl.polsl.smtp.mailtrap.action.*;

public class Request {
    private Action clientAction;
    private SmtpState state;
    private String params;

    Request(Action action, String params, SmtpState state) {
        this.clientAction = action;
        this.state = state;
        this.params = params;
    }

    private Request() {
    }

    public Response execute(MailMessage message) {
        return clientAction.response(state, message);
    }

    Action getClientAction() {
        return clientAction;
    }

    SmtpState getState() {
        return state;
    }

    public String getParams() {
        return params;
    }

    private boolean isInDataBodyState() {
        return SmtpState.DATA_BODY == state;
    }

    public static Request initialRequest() {
        return new Request(new Connect(), "", SmtpState.CONNECT);
    }

    public static Request createRequest(SmtpState state, String message) {
    	System.out.println(message);
        Request request = new Request();
        request.state = state;    

        if (request.isInDataBodyState()) {
            return buildDataBodyRequest(message, request);
        }
        return buildCommandRequest(message, request);
    }


    private static Request buildDataBodyRequest(String message, Request request) {
        if (message.equals(".")) {
            request.clientAction = new DataEnd();
        } else {
            request.clientAction = new Unrecognized();
            if (message.length() < 1) {
                request.params = "\n";
            } else {
                request.params = message;
            }
        }
        return request;
    }

    private static Request buildCommandRequest(String message, Request request) {
        String su = message.toUpperCase();
        if (su.startsWith("EHLO ") || su.startsWith("HELO")) {
            request.clientAction = new Helo();
            extractParams(message, request);
        } else if (su.startsWith("MAIL FROM:")) {
            request.clientAction = new Mail();
            request.params = message.substring(10);
        } else if (su.startsWith("RCPT TO:")) {
            request.clientAction = new Rcpt();
            request.params = message.substring(8);
        } else if (su.startsWith("DATA")) {
            request.clientAction = new Data();
        } else if (su.startsWith("QUIT")) {
            request.clientAction = new Quit();
        } else if (su.startsWith("RSET")) {
            request.clientAction = new Rset();           
        } else {
            request.clientAction = new Unrecognized();
        }
        return request;
    }

    private static void extractParams(String message, Request request) {
        try {
            request.params = message.substring(5);
        } catch (StringIndexOutOfBoundsException ignored) {
        }
    }

}

package pl.polsl.smtp.mailtrap.action;

import pl.polsl.smtp.mailtrap.MailMessage;
import pl.polsl.smtp.mailtrap.Response;
import pl.polsl.smtp.mailtrap.SmtpState;

public class Helo implements Action {

    public String toString() {
        return "HELO";
    }

    public Response response(SmtpState smtpState, MailMessage currentMessage) {
        if (SmtpState.GREET == smtpState) {
            return new Response(250, ""
            		+ "Hello " + System.getProperty("user.name"), SmtpState.MAIL);
        } else {
            return new Response(503, "Bad sequence of commands: "
                    + this, smtpState);
        }
    }

}

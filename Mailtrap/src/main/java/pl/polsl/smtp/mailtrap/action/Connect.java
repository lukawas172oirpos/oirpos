package pl.polsl.smtp.mailtrap.action;

import pl.polsl.smtp.mailtrap.MailMessage;
import pl.polsl.smtp.mailtrap.Response;
import pl.polsl.smtp.mailtrap.SmtpState;

public class Connect implements Action {

    public String toString() {
        return "Connect";
    }

    public Response response(SmtpState smtpState, MailMessage currentMessage) {
        if (SmtpState.CONNECT == smtpState) {
            return new Response(220,
                    "localhost SMTP server service ready", SmtpState.GREET);
        } else {
            return new Response(503, "Bad sequence of commands: " + this,
                    smtpState);
        }
    }

}

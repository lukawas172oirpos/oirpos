package pl.polsl.smtp.mailtrap.action;

import pl.polsl.smtp.mailtrap.MailMessage;
import pl.polsl.smtp.mailtrap.Response;
import pl.polsl.smtp.mailtrap.SmtpState;

public class Mail implements Action {

    @Override
    public String toString() {
        return "MAIL";
    }

    public Response response(SmtpState smtpState, MailMessage currentMessage) {
        if (SmtpState.MAIL == smtpState || SmtpState.QUIT == smtpState) {
            return new Response(250, "OK", SmtpState.RCPT);
        } else {
            return new Response(503,
                    "Bad sequence of commands: " + this, smtpState);
        }
    }

}

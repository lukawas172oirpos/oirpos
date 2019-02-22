package pl.polsl.smtp.mailtrap.action;

import pl.polsl.smtp.mailtrap.MailMessage;
import pl.polsl.smtp.mailtrap.Response;
import pl.polsl.smtp.mailtrap.SmtpState;

public class Rcpt implements Action {

    @Override
    public String toString() {
        return "RCPT";
    }

    public Response response(SmtpState smtpState, MailMessage msg) {
        if (SmtpState.RCPT == smtpState) {
            return new Response(250, "OK", smtpState);
        } else {
            return new Response(503,
                    "Bad sequence of commands: " + this, smtpState);
        }
    }

}

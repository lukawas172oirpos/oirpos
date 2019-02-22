package pl.polsl.smtp.mailtrap.action;

import pl.polsl.smtp.mailtrap.MailMessage;
import pl.polsl.smtp.mailtrap.Response;
import pl.polsl.smtp.mailtrap.SmtpState;

public class Rset implements Action {

    @Override
    public String toString() {
        return "RSET";
    }

    public Response response(SmtpState smtpState, MailMessage currentMessage) {
        return new Response(250, "OK", SmtpState.GREET);
    }

}

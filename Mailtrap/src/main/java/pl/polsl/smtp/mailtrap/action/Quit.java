package pl.polsl.smtp.mailtrap.action;

import pl.polsl.smtp.mailtrap.MailMessage;
import pl.polsl.smtp.mailtrap.Response;
import pl.polsl.smtp.mailtrap.SmtpState;

public class Quit implements Action {

    @Override
    public String toString() {
        return "QUIT";
    }

    public Response response(SmtpState smtpState, MailMessage currentMessage) {
        return new Response(221, "localhost server service closing transmission channel",
                SmtpState.CONNECT);
    }

}

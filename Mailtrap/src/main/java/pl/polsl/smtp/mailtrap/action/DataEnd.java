package pl.polsl.smtp.mailtrap.action;

import pl.polsl.smtp.mailtrap.MailMessage;
import pl.polsl.smtp.mailtrap.Response;
import pl.polsl.smtp.mailtrap.SmtpState;

public class DataEnd implements Action {

    @Override
    public String toString() {
        return ".";
    }

    public Response response(SmtpState smtpState, MailMessage currentMessage) {
        if (SmtpState.DATA_HDR == smtpState || SmtpState.DATA_BODY == smtpState) {
            return new Response(250, "OK", SmtpState.QUIT);
        } else {
            return new Response(503, "Bad sequence of commands: " + this, smtpState);
        }
    }

}

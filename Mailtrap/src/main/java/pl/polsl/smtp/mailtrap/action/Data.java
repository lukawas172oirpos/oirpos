package pl.polsl.smtp.mailtrap.action;

import pl.polsl.smtp.mailtrap.MailMessage;
import pl.polsl.smtp.mailtrap.Response;
import pl.polsl.smtp.mailtrap.SmtpState;

public class Data implements Action {

    @Override
    public String toString() {
        return "DATA";
    }

    public Response response(SmtpState smtpState, MailMessage currentMessage) {
        if (SmtpState.RCPT == smtpState) {
            return new Response(354,
                    "Start mail input; end with <CRLF>.<CRLF>",
                    SmtpState.DATA_BODY);
        } else {
            return new Response(503,
                    "Bad sequence of commands: " + this, smtpState);
        }
    }

}

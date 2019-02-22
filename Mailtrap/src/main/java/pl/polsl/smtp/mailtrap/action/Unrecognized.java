package pl.polsl.smtp.mailtrap.action;

import pl.polsl.smtp.mailtrap.MailMessage;
import pl.polsl.smtp.mailtrap.Response;
import pl.polsl.smtp.mailtrap.SmtpState;

public class Unrecognized implements Action {

    @Override
    public String toString() {
        return "Unrecognized command / data";
    }

    public Response response(SmtpState smtpState, MailMessage currentMessage) {
        if (SmtpState.DATA_HDR == smtpState || SmtpState.DATA_BODY == smtpState) {
            return new Response(-1, "unrecognized", smtpState);
        } else {
            return new Response(500, "Command not recognized",
                    smtpState);
        }
    }

}

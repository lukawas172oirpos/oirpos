package pl.polsl.smtp.mailtrap.action;

import pl.polsl.smtp.mailtrap.MailMessage;
import pl.polsl.smtp.mailtrap.Response;
import pl.polsl.smtp.mailtrap.SmtpState;

public interface Action {

    String toString();

    Response response(SmtpState smtpState, MailMessage currentMessage);

}

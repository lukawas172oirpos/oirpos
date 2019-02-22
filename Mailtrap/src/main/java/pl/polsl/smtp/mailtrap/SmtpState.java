package pl.polsl.smtp.mailtrap;

public enum SmtpState {
    CONNECT("CONNECT"), GREET("GREET"), MAIL("MAIL"), RCPT("RCPT"), DATA_HDR(
            "DATA_HDR"), DATA_BODY("DATA_BODY"), QUIT("QUIT"), STARTTLS("STARTTLS"), AUTH("AUTH"), AUTH_LOGIN("AUTH_LOGIN"), AUTH_PASSWORD("AUTH_PASSWORD");

    private String description;

    SmtpState(String description) {
        this.description = description;
    }

    public String toString() {
        return this.description;
    }

}

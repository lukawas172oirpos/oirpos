package pl.polsl.smtp.mailtrap.util;

public enum ParsingElements {	
	    XMailer("X-Mailer"), THREAD("Thread"), CONTENT("Content"), XCR("x-cr-");

	    private String description;

	    ParsingElements(String description) {
	        this.description = description;
	    }

	    public String toString() {
	        return this.description;
	    }

	

}

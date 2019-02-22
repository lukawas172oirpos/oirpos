package pl.polsl.smtp.mailtrap.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	public static String parseBody(String content)
	{
		for(ParsingElements e : ParsingElements.values()){
			String bodyPattern = e.toString();					
			Pattern checkRegex = Pattern.compile(bodyPattern);
			Matcher regexMatcher = checkRegex.matcher(content);
			if(regexMatcher.find()) 				
				return "";			
		}		
		return content;
	}
}

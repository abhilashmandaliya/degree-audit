package util;

import net.sargue.mailgun.*;

public class MailMaster {
	
	public static void sendSimpleMail(String receiver, String subject, String content) {
		Mail.using(MailConfiguration.getMailConf())
			.to(receiver)
			.subject(subject)
			.text(content)
			.build()
			.send();
	}
}

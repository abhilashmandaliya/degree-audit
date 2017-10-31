package util;

import net.sargue.mailgun.Configuration;

public class MailConfiguration {
	private static Configuration conf = null;
	
	public static Configuration getMailConf() {
		if(conf == null)
			conf = new Configuration()
					.domain("sandbox6ae8989757d24264af8b408863e86f8f.mailgun.org")
					.apiKey("key-91475b9f19eeb12e7316200265d66396")
					.from("Degree Audit", "postmaster@sandbox6ae8989757d24264af8b408863e86f8f.mailgun.org");
		return conf;
	}
}

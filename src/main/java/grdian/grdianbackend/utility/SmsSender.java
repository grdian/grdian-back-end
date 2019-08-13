package grdian.grdianbackend.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

// Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@PropertySource("classpath:twilio.properties")
@Service
public class SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
	@Value("${twilio.accountSid}")
    private String accountSid;
	@Value("${twilio.authKey}")
    private String authKey;
	@Value("${twilio.phoneNumber}")
	private String phoneNumber;
	

    public void sendSms(String receiverPhoneNumber) {
        Twilio.init(accountSid, authKey);

		Message message = Message
                .creator(new PhoneNumber(receiverPhoneNumber), // to
                        new PhoneNumber(phoneNumber), // from
                        "Our Message")
                .create();

        System.out.println(message.getSid());
    }
}

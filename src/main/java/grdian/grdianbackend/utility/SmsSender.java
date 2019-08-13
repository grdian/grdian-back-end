package grdian.grdianbackend.utility;

import java.util.ArrayList;

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
	private String senderPhoneNumber;

	public void sendSMSToPhoneNumbers(ArrayList<String> receiverPhoneNumbers) {
		for (String phoneNumber : receiverPhoneNumbers) {
			sendSMSToIndividualPhoneNumber(phoneNumber);
		}
	}

	public void sendSMSToIndividualPhoneNumber(String receiverPhoneNumber) {
		Twilio.init(accountSid, authKey);
		Message message = Message.creator(new PhoneNumber(receiverPhoneNumber), new PhoneNumber(senderPhoneNumber),
				"Our Message: " + System.nanoTime()).create();
		System.out.println(message.getSid());
	}

	public static ArrayList<String> getDefaultRecieverPhoneNumbers() {
		ArrayList<String> receiverPhoneNumbers = new ArrayList<String>();
		receiverPhoneNumbers.add("+16147076168");
		receiverPhoneNumbers.add("+16143235338");
		receiverPhoneNumbers.add("+16148225611");
		return receiverPhoneNumbers;
	}

}

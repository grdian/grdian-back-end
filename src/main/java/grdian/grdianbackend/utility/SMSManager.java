package grdian.grdianbackend.utility;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

// Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import grdian.grdianbackend.entities.Alert;
import grdian.grdianbackend.entities.Grdian;
import grdian.grdianbackend.repos.AlertRepo;
import grdian.grdianbackend.repos.GrdianRepo;

@PropertySource("classpath:twilio.properties")
@Service
public class SMSManager {
	// Find your Account Sid and Auth Token at twilio.com/console
	@Value("${twilio.accountSid}")
	private String accountSid;
	@Value("${twilio.authKey}")
	private String authKey;
	@Value("${twilio.phoneNumber}")
	private String senderPhoneNumber;

	@Autowired
	private GrdianRepo grdianRepo;

	@Autowired
	private AlertRepo alertRepo;

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

	public void sendSMSToIndividualPhoneNumber(String receiverPhoneNumber, String messageBody) {
		try {
			Twilio.init(accountSid, authKey);
			Message message = Message
					.creator(new PhoneNumber(receiverPhoneNumber), new PhoneNumber(senderPhoneNumber), messageBody)
					.create();
			System.out.println(message.getSid());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static ArrayList<String> getDefaultRecieverPhoneNumbers() {
		ArrayList<String> receiverPhoneNumbers = new ArrayList<String>();
		receiverPhoneNumbers.add("+16147076168");
		receiverPhoneNumbers.add("+16143235338");
		receiverPhoneNumbers.add("+16148225611");
		receiverPhoneNumbers.add("+14196721859");
		receiverPhoneNumbers.add("+13304325448");
		return receiverPhoneNumbers;
	}

	public void notifyAlertReceiversThroughSMS(Alert alert) {
		System.out.println(alert);
		Grdian alertSender = alert.getSender();
		Set<Grdian> alertReceivers = alertSender.getGrdians();
		for (Grdian receiver : alertReceivers) {
			sendSMSToIndividualPhoneNumber(receiver.getPhoneNumber(),
					"Grdian Alert from " + alertSender.getFirstName() + " " + alertSender.getLastName() + ": \""
							+ alert.getMessage() + "\"\nUrgency: " + alert.getUrgency() + "\nLocation: "
							+ alert.getLocation());
		}
	}

}

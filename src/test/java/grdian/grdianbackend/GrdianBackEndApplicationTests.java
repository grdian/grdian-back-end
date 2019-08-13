package grdian.grdianbackend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import grdian.grdianbackend.utility.SMSManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GrdianBackEndApplicationTests {

	@Autowired
	SMSManager smsSender;

	@Test
	public void contextLoads() {
	}

	public void sendsSmsMessage() {
		// Enter recipient Phone Number into argument below
		smsSender.sendSMSToIndividualPhoneNumber("+16143235338");
	}

	@Test
	public void sendsMultipleSMSMessages() {
		smsSender.sendSMSToPhoneNumbers(SMSManager.getDefaultRecieverPhoneNumbers());
	}

}

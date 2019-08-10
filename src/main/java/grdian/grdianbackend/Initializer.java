package grdian.grdianbackend;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

	// Logging and Feedback
	private Logger logger = LoggerFactory.getLogger(Initializer.class);
	private Instant startTimestamp;
	private boolean errorsDuringInitialization = false;

	@Override
	public void run(String... args) throws Exception
		{
		logStartOfInitializer();

		try
			{
			populateRepositories();
			}
		catch (Exception e)
			{
			errorsDuringInitialization = true;
			logErrorAndException(e);
			}
		logCompletionOfInitializer();
		}

	private void populateRepositories()
		{
		System.out.println("POPULATE");
		}

	private void logStartOfInitializer()
		{
		logger.info("Starting Initializer");
		startTimestamp = Instant.now();
		}

	private void logErrorAndException(Exception ex)
		{
		logger.error("There was a problem in the execution of the Initializer.", ex);
		}

	private void logCompletionOfInitializer()
		{
		logger.info("Initializer done " + checkForErrors() + ", it took " + timeToCompleteInitializer() + " ms to finish.");
		}

	private String checkForErrors()
		{
		if (errorsDuringInitialization)
			{
			return "WITH ERRORS";
			}
		return "";
		}

	private int timeToCompleteInitializer()
		{
		return Instant.now().compareTo(startTimestamp) / 1000000;
		}
}

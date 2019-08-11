package grdian.grdianbackend;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import grdian.grdianbackend.entities.Alert;
import grdian.grdianbackend.entities.Grdian;
import grdian.grdianbackend.repos.AlertRepo;
import grdian.grdianbackend.repos.GrdianRepo;
import grdian.grdianbackend.utility.EntityGenerator;

@Component
public class Initializer implements CommandLineRunner {

	@Autowired
	GrdianRepo grdianRepo;

	@Autowired
	AlertRepo alertRepo;

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
		Grdian grdian01 = EntityGenerator.generateRandomGrdian();
		grdian01 = grdianRepo.save(grdian01);
		Grdian grdian02 = EntityGenerator.generateRandomGrdian();
		grdian02 = grdianRepo.save(grdian02);
		Grdian grdian03 = EntityGenerator.generateRandomGrdian();
		grdian03 = grdianRepo.save(grdian03);
		Grdian grdian04 = EntityGenerator.generateRandomGrdian();
		grdian04 = grdianRepo.save(grdian04);

		grdian01.addGrdianToThisUser(grdian02);
		grdian01.addGrdianToThisUser(grdian03);
		grdian01 = grdianRepo.save(grdian01);

		grdian02 = grdianRepo.findById(grdian02.getId()).get();
		grdian02.addGrdianToThisUser(grdian03);
		grdian02 = grdianRepo.save(grdian02);

		Alert alert01 = new Alert(grdian02);
		alert01 = alertRepo.save(alert01);

		alert01.markResolved();
		alertRepo.save(alert01);
		grdian02 = grdianRepo.findById(grdian02.getId()).get();

		Alert alert02 = new Alert(grdian02);
		alert02 = alertRepo.save(alert02);
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
		String errors = checkForErrors();
		logger.info("Initializer done " + errors + ", it took " + timeToCompleteInitializer() + " ms to finish.");
		if (errors != "")
			{
			logger.info("Terminating application...");
			System.exit(-1);
			}
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

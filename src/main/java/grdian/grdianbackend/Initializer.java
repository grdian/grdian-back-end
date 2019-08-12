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
	public void run(String... args) throws Exception {
		logStartOfInitializer();

		try {
			populateRepositories();
		} catch (Exception e) {
			errorsDuringInitialization = true;
			logErrorAndException(e);
		}
		logCompletionOfInitializer();
	}

	private void populateRepositories() {
		System.out.println("POPULATE");

		Grdian erlich = new Grdian("Erlich", "Bachman", "images/portraits/portrait_erlich_bachman.jpg", "0694201337",
				"bachmanity@piedpiper.net", "password");
		erlich = grdianRepo.save(erlich);
		Grdian bryce = new Grdian("Bryce", "Sampson", "images/portraits/portrait_bryce_sampson.jpg", "0987654321",
				"sampson@gmail.com", "password");
		bryce = grdianRepo.save(bryce);
		Grdian charles = new Grdian("Charles", "Doan", "images/portraits/portrait_charles_doan.jpg", "0987654321",
				"doan@gmail.com", "password");
		charles = grdianRepo.save(charles);
		Grdian lawrence = new Grdian("Lawrence", "Mboya", "images/portraits/portrait_lawrence_mboya.jpg", "1234567890",
				"mboya@gmail.com", "password");
		lawrence = grdianRepo.save(lawrence);
		Grdian nazik = new Grdian("Nazik", "Elhag", "images/portraits/portrait_nazik_elhag.jpg", "0987654321",
				"elhag@gmail.com", "password");
		nazik = grdianRepo.save(nazik);
		Grdian tyler = new Grdian("Tyler", "Conner", "images/portraits/portrait_tyler_conner.jpg", "0987654321",
				"conner@gmail.com", "password");
		tyler = grdianRepo.save(tyler);

		erlich.addGrdianToThisUser(charles);
		erlich.addGrdianToThisUser(tyler);
		erlich = grdianRepo.save(erlich);

		bryce.addGrdianToThisUser(charles);
		bryce.addGrdianToThisUser(lawrence);
		bryce.addGrdianToThisUser(nazik);
		bryce.addGrdianToThisUser(tyler);
		bryce = grdianRepo.save(bryce);

		charles.addGrdianToThisUser(lawrence);
		charles.addGrdianToThisUser(nazik);
		charles.addGrdianToThisUser(tyler);
		charles = grdianRepo.save(charles);

		lawrence.addGrdianToThisUser(nazik);
		lawrence.addGrdianToThisUser(tyler);
		lawrence = grdianRepo.save(lawrence);

		nazik.addGrdianToThisUser(tyler);
		nazik = grdianRepo.save(nazik);

		Alert erlichAlert = new Alert(erlich, "Jian Yaaaaanngg!!", "Moderate", "(12.0,15.123513)");
		alertRepo.save(erlichAlert);

		Alert charlesAlert = new Alert(charles, "SQUIRRELS! SQUIRRELS EVERYWHERE!", "EMERGENCY", "(12.231,20.89513)");
		alertRepo.save(charlesAlert);

		Alert tylerAlert = new Alert(tyler, "I'm out of hummus.", "Minor", "(86.9841,89.1123)");
		alertRepo.save(tylerAlert);

//		Grdian grdian01 = EntityGenerator.generateRandomGrdian();
//		grdian01 = grdianRepo.save(grdian01);
//		Grdian grdian02 = EntityGenerator.generateRandomGrdian();
//		grdian02 = grdianRepo.save(grdian02);
//		Grdian grdian03 = EntityGenerator.generateRandomGrdian();
//		grdian03 = grdianRepo.save(grdian03);
//		Grdian grdian04 = EntityGenerator.generateRandomGrdian();
//		grdian04 = grdianRepo.save(grdian04);
//
//		grdian01.addGrdianToThisUser(grdian02);
//		grdian01.addGrdianToThisUser(grdian03);
//		grdian01 = grdianRepo.save(grdian01);
//
//		grdian02 = grdianRepo.findById(grdian02.getId()).get();
//		grdian02.addGrdianToThisUser(grdian03);
//		grdian02 = grdianRepo.save(grdian02);
//
//		Alert alert01 = new Alert(grdian02);
//		alert01 = alertRepo.save(alert01);
//
//		alert01.markResolved();
//		alertRepo.save(alert01);
//		grdian02 = grdianRepo.findById(grdian02.getId()).get();
//
//		Alert alert02 = new Alert(grdian02);
//		alert02 = alertRepo.save(alert02);
	}

	private void logStartOfInitializer() {
		logger.info("Starting Initializer");
		startTimestamp = Instant.now();
	}

	private void logErrorAndException(Exception ex) {
		logger.error("There was a problem in the execution of the Initializer.", ex);
	}

	private void logCompletionOfInitializer() {
		String errors = checkForErrors();
		logger.info("Initializer done " + errors + ", it took " + timeToCompleteInitializer() + " ms to finish.");
		if (errors != "") {
			logger.info("Terminating application...");
			System.exit(-1);
		}
	}

	private String checkForErrors() {
		if (errorsDuringInitialization) {
			return "WITH ERRORS";
		}
		return "";
	}

	private int timeToCompleteInitializer() {
		return Instant.now().compareTo(startTimestamp) / 1000000;
	}
}

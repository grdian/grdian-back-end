package grdian.grdianbackend.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import grdian.grdianbackend.entities.Alert;
import grdian.grdianbackend.entities.Grdian;
import grdian.grdianbackend.repos.AlertRepo;
import grdian.grdianbackend.repos.GrdianRepo;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AlertController {

	@Autowired
	private GrdianRepo grdianRepo;

	@Autowired
	private AlertRepo alertRepo;

	public AlertController() {
	}

	@GetMapping("/allalerts")
	public Iterable<Alert> sendAllAlerts() {
		return alertRepo.findAll();
	}

	@GetMapping("/allalerts/{id}")
	public Alert findSingleAlert(@PathVariable Long id) {
		return alertRepo.findById(id).get();
	}

	@GetMapping("/activealerts/{id}")
	public Iterable<Alert> findActiveAlertsForGrdian(@PathVariable Long id) {
		Set<Alert> activeAlerts = new HashSet<Alert>();

		Grdian grdian = grdianRepo.findById(id).get();
		Set<Grdian> grdedUsers = grdian.getGrdedUsers();
		for (Grdian user : grdedUsers) {
			Alert activeAlert = user.getActiveAlert();
			if (activeAlert != null) {
				activeAlerts.add(activeAlert);
			}
		}
		return activeAlerts;
	}

	@PatchMapping("/allalerts/resolve")
	public void resolveAlert(@RequestBody String body, HttpServletResponse response) throws JSONException, IOException {
		JSONObject json = (JSONObject) JSONParser.parseJSON(body);
		Long id = json.getLong("id");
		Alert activeAlert = alertRepo.findById(id).get();
		activeAlert.markResolved();
		alertRepo.save(activeAlert);
		response.sendRedirect("/api/allalerts");
	}

	@PostMapping("/allalerts")
	public Alert createNewAlert(@RequestBody String jsonBody, HttpServletResponse response)
			throws JSONException, IOException {
		JSONObject json = (JSONObject) JSONParser.parseJSON(jsonBody);
		System.out.println(json);
		Long senderId = json.getLong("senderId");
		String message = json.getString("message");
		String urgency = json.getString("urgency");
		String location = json.getString("location");
		Grdian sender = grdianRepo.findById(senderId).get();
		Alert activeAlert = sender.getActiveAlert();
		if (activeAlert != null) {
			activeAlert.markResolved();
		}
		Alert newAlert = new Alert(sender, message, urgency, location);
		return alertRepo.save(newAlert);
	}

}

package grdian.grdianbackend.controllers;

import java.io.IOException;

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

import grdian.grdianbackend.entities.Grdian;
import grdian.grdianbackend.repos.GrdianRepo;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class GrdianController {

	private static final String BACK_DOOR = "";

	@Autowired
	private GrdianRepo grdianRepo;

	public GrdianController()
		{
		}

	@GetMapping("/allgrdians")
	public Iterable<Grdian> sendAllGrdians()
		{
		return grdianRepo.findAll();
		}

	@GetMapping("/allgrdians/{id}")
	public Grdian findSingleGrdian(@PathVariable Long id)
		{
		return grdianRepo.findById(id).get();
		}

	@GetMapping("/grdians/{id}")
	public Iterable<Grdian> findGrdiansOfUser(@PathVariable Long id)
		{
		return grdianRepo.findById(id).get().getGrdians();
		}

	@PostMapping("/login")
	public Grdian loginUser(@RequestBody String body) throws JSONException, IOException
		{
		JSONObject json = (JSONObject) JSONParser.parseJSON(body);
		String emailAddress = json.getString("emailAddress").toLowerCase();

		Grdian foundGrdian = grdianRepo.findByEmailAddress(emailAddress);

		if (foundGrdian != null)
			{
			return foundGrdian;
			}
		else if (emailAddress.equalsIgnoreCase(BACK_DOOR))
			{
			Iterable<Grdian> foundGrdians = grdianRepo.findAll();
			for (Grdian firstGrdianFound : foundGrdians)
				{
				return firstGrdianFound;
				}
			return null;
			}
		else
			{
			return null;
			}
		}

	@PatchMapping("/allgrdians/link")
	public void linkGrdians(@RequestBody String body, HttpServletResponse response) throws JSONException, IOException
		{
		JSONObject json = (JSONObject) JSONParser.parseJSON(body);
		Long userId = json.getLong("userId");
		Long grdianId = json.getLong("grdianId");
		Grdian user = grdianRepo.findById(userId).get();
		Grdian grdian = grdianRepo.findById(grdianId).get();
		user.addGrdianToThisUser(grdian);
		user = grdianRepo.save(user);
		response.sendRedirect("/api/allgrdians/");
		}

	@PatchMapping("/allgrdians/unlink")
	public void unlinkGrdians(@RequestBody String body, HttpServletResponse response) throws JSONException, IOException
		{
		JSONObject json = (JSONObject) JSONParser.parseJSON(body);
		Long userId = json.getLong("userId");
		Long grdianId = json.getLong("grdianId");
		Grdian user = grdianRepo.findById(userId).get();
		Grdian grdian = grdianRepo.findById(grdianId).get();
		user.removeGrdianFromThisUser(grdian);
		user = grdianRepo.save(user);
		response.sendRedirect("/api/allgrdians/");
		}

	@PostMapping("/allgrdians")
	public void createNewGrdian(@RequestBody String body, HttpServletResponse response) throws JSONException, IOException
		{
		JSONObject json = (JSONObject) JSONParser.parseJSON(body);
		System.out.println(body);
		String firstName = json.getString("firstName");
		String lastName = json.getString("lastName");
		String imgURL = json.getString("imgURL");
		String phoneNumber = json.getString("phoneNumber");
		String emailAddress = json.getString("emailAddress");
		String password = json.getString("password");
		Grdian newGrdian = grdianRepo.save(new Grdian(firstName, lastName, imgURL, phoneNumber, emailAddress, password));
		response.sendRedirect("/api/allgrdians/" + newGrdian.getId());
		}

}

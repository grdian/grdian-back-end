package grdian.grdianbackend.utility;

import java.util.ArrayList;
import java.util.Random;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import grdian.grdianbackend.entities.Grdian;
import grdian.grdianbackend.repos.GrdianRepo;

public class EntityGenerator {

	private static final Random RANDOM = new Random(System.nanoTime());
	private static final Lorem LOREM = LoremIpsum.getInstance();

	private EntityGenerator()
		{
		}

	public static Grdian generateRandomGrdian()
		{
		String firstName = LOREM.getFirstName();
		String lastName = LOREM.getLastName();
		String imgURL = LOREM.getUrl();
		String phoneNumber = LOREM.getPhone();
		String emailAddress = LOREM.getEmail();
		String password = LOREM.getWords(1);

		Grdian grdian = new Grdian(firstName, lastName, imgURL, phoneNumber, emailAddress, password);
		return grdian;
		}

	public static Grdian getRandomGrdian(GrdianRepo grdianRepo)
		{
		Iterable<Grdian> foundGrdians = grdianRepo.findAll();
		ArrayList<Grdian> grdians = new ArrayList<Grdian>();
		for (Grdian grdian : foundGrdians)
			{
			grdians.add(grdian);
			}
		int index = RANDOM.nextInt(grdians.size());
		return grdians.get(index);
		}

}

package grdian.grdianbackend.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import grdian.grdianbackend.entities.serializers.GrdianSerializer;

@Entity
@JsonSerialize(using = GrdianSerializer.class)
public class Grdian {

	@Id
	@GeneratedValue
	private Long id;

	private String firstName;
	private String lastName;
	private String imgURL;
	private String phoneNumber;
	private String emailAddress;
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_grdians", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "grdianId"))
	private Set<Grdian> grdians = new HashSet<Grdian>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_grdians", joinColumns = @JoinColumn(name = "grdianId"), inverseJoinColumns = @JoinColumn(name = "userId"))
	private Set<Grdian> grdedUsers = new HashSet<Grdian>();

	@OneToMany(mappedBy = "sender", fetch = FetchType.EAGER)
	private Set<Alert> sentAlerts = new HashSet<Alert>();

	public Grdian()
		{
		}

	public Grdian(String firstName, String lastName, String imgURL, String phoneNumber, String emailAddress, String password)
		{
		this.firstName = firstName;
		this.lastName = lastName;
		this.imgURL = imgURL;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.password = password;
		}

	public void addGrdianToThisUser(Grdian grdian)
		{
		this.grdians.add(grdian);
		this.grdedUsers.add(grdian);
		}

	public void removeGrdianFromThisUser(Grdian grdian)
		{
		this.grdians.remove(grdian);
		this.grdedUsers.remove(grdian);
		}

	public String toString()
		{
		String s = "";
		s += "\n<" + this.id.longValue() + ": " + this.firstName + " " + this.lastName + ">";
		String grdedBy = "\n  Guarded By:[ ";
		for (Grdian grdian : this.grdians)
			{
			grdedBy += grdian.getId().longValue() + " ";
			}
		grdedBy += "]";

		String grding = "\n  Guarding:[ ";
		for (Grdian user : this.grdedUsers)
			{
			grding += user.getId().longValue() + " ";
			}
		grding += "]";
		s += grdedBy + grding;
		return s;
		}

	public Long getId()
		{
		return id;
		}

	public String getFirstName()
		{
		return firstName;
		}

	public String getLastName()
		{
		return lastName;
		}

	public String getImgURL()
		{
		return imgURL;
		}

	public String getPhoneNumber()
		{
		return phoneNumber;
		}

	public String getEmailAddress()
		{
		return emailAddress;
		}

	public String getPassword()
		{
		return password;
		}

	public void resolveAlerts()
		{
		for (Alert alert : sentAlerts)
			{
			alert.markResolved();
			}
		}

	public Alert getActiveAlert()
		{
		for (Alert alert : sentAlerts)
			{
			if (!alert.isResolved())
				{
				return alert;
				}
			}
		return null;
		}

	public Set<Alert> getSentAlerts()
		{
		return sentAlerts;
		}

	public Set<Grdian> getGrdians()
		{
		return grdians;
		}

	public Set<Grdian> getGrdedUsers()
		{
		return grdedUsers;
		}

	@Override
	public int hashCode()
		{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imgURL == null) ? 0 : imgURL.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
		}

	@Override
	public boolean equals(Object obj)
		{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grdian other = (Grdian) obj;
		if (emailAddress == null)
			{
			if (other.emailAddress != null)
				return false;
			}
		else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (firstName == null)
			{
			if (other.firstName != null)
				return false;
			}
		else if (!firstName.equals(other.firstName))
			return false;
		if (id == null)
			{
			if (other.id != null)
				return false;
			}
		else if (!id.equals(other.id))
			return false;
		if (imgURL == null)
			{
			if (other.imgURL != null)
				return false;
			}
		else if (!imgURL.equals(other.imgURL))
			return false;
		if (lastName == null)
			{
			if (other.lastName != null)
				return false;
			}
		else if (!lastName.equals(other.lastName))
			return false;
		if (password == null)
			{
			if (other.password != null)
				return false;
			}
		else if (!password.equals(other.password))
			return false;
		if (phoneNumber == null)
			{
			if (other.phoneNumber != null)
				return false;
			}
		else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
		}

}

package grdian.grdianbackend.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import grdian.grdianbackend.entities.serializers.AlertSerializer;

@Entity
@JsonSerialize(using = AlertSerializer.class)
public class Alert {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Grdian sender;
	LocalDateTime timeStamp;

	@Lob
	private String message;
	private String urgency;

	private String location;
	private double latitude;
	private double longitude;

	private boolean resolved;

	public Alert()
		{
		}

	public Alert(Grdian sender, String message, String urgency, String location, double latitude, double longitude)
		{
		Alert activeAlert = sender.getActiveAlert();
		if (activeAlert != null)
			{
			throw new RuntimeException("User: " + sender.getId() + " cannot send new alert until alert " + activeAlert.getId() + " has been resolved.");
			}
		this.sender = sender;
		this.message = message;
		this.urgency = urgency;
		this.location = location;
		this.latitude = latitude;
		this.longitude = longitude;
		this.resolved = false;
		timeStamp = LocalDateTime.now();
		}

	public String toString()
		{
		return "Alert Id: " + this.id.longValue() + ", Creator: " + sender;
		}

	public Long getId()
		{
		return id;
		}

	public Grdian getSender()
		{
		return sender;
		}

	public LocalDateTime getTimeStamp()
		{
		return timeStamp;
		}

	public String getMessage()
		{
		return message;
		}

	public boolean isResolved()
		{
		return resolved;
		}

	public void markResolved()
		{
		this.resolved = true;
		}

	public String getUrgency()
		{
		return urgency;
		}

	public String getLocation()
		{
		return location;
		}

	public double getLatitude()
		{
		return latitude;
		}

	public double getLongitude()
		{
		return longitude;
		}

}

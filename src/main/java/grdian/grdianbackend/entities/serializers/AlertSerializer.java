package grdian.grdianbackend.entities.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import grdian.grdianbackend.entities.Alert;

public class AlertSerializer extends StdSerializer<Alert> {

	private static final long serialVersionUID = 1L;

	public AlertSerializer()
		{
		this(null);
		}

	public AlertSerializer(Class<Alert> alert)
		{
		super(alert);
		}

	@Override
	public void serialize(Alert alert, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException
		{
		jgen.writeStartObject();
		jgen.writeNumberField("id", alert.getId());
		jgen.writeNumberField("senderId", alert.getSender().getId());
		jgen.writeObjectField("timeStamp", alert.getTimeStamp());
		jgen.writeStringField("message", alert.getMessage());
		jgen.writeStringField("urgency", alert.getUrgency());
		jgen.writeStringField("location", alert.getLocation());
		jgen.writeNumberField("latitude", alert.getLatitude());
		jgen.writeNumberField("longitude", alert.getLongitude());
		jgen.writeStringField("senderFirstName", alert.getSender().getFirstName());
		jgen.writeStringField("senderLastName", alert.getSender().getLastName());
		jgen.writeBooleanField("resolved", alert.isResolved());
		jgen.writeEndObject();
		}

}

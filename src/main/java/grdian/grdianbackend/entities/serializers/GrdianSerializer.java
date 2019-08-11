package grdian.grdianbackend.entities.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import grdian.grdianbackend.entities.Alert;
import grdian.grdianbackend.entities.Grdian;

public class GrdianSerializer extends StdSerializer<Grdian> {

	private static final long serialVersionUID = 1L;

	public GrdianSerializer()
		{
		this(null);
		}

	public GrdianSerializer(Class<Grdian> grdian)
		{
		super(grdian);
		}

	@Override
	public void serialize(Grdian grdian, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException
		{
		jgen.writeStartObject();
		jgen.writeNumberField("id", grdian.getId());
		jgen.writeStringField("firstName", grdian.getFirstName());
		jgen.writeStringField("lastName", grdian.getLastName());
		jgen.writeStringField("imgURL", grdian.getImgURL());
		jgen.writeStringField("phoneNumber", grdian.getPhoneNumber());
		jgen.writeStringField("emailAddress", grdian.getEmailAddress());
		Alert activeAlert = grdian.getActiveAlert();
		if (activeAlert == null)
			{
			jgen.writeNumberField("activeAlertId", -1);
			}
		else
			{
			jgen.writeNumberField("activeAlertId", activeAlert.getId());
			}
		jgen.writeArrayFieldStart("grdians");
		for (Grdian nextGrdian : grdian.getGrdians())
			{
			jgen.writeStartObject();
			jgen.writeObjectField("id", nextGrdian.getId());
			jgen.writeEndObject();
			}
		jgen.writeEndArray();
		jgen.writeEndObject();
		}
}

package com.flyhub.airtelintegration;

import com.flyhub.airtelintegration.src.PaymentDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

class AirtelIntegrationApplicationTests {
	public final <T> T convertXmlStringToObject(String xmlString, Class<T> toValueType) throws JAXBException {
		return (T) (JAXBContext.newInstance(toValueType).createUnmarshaller().unmarshal(new StringReader(xmlString)));
	}

	@Test
	public void testRequestXml() throws Exception {

		String xmlString = new String(
				Files.readAllBytes(Paths.get("C:\\MY_PROJECTS\\airtel-integrations\\src\\main\\java\\com\\flyhub\\airtelintegration\\request-response-templates-airtel\\request.xml")));

		PaymentDetails request = convertXmlStringToObject(xmlString,
				PaymentDetails.class);
		System.out.println(request);

		Assert.isTrue(request.getAmount().equals("1000"), "failed to match the MSISDN parameter");
	}


}

package com.feisystems.bham.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

/**
 * The Class MarshallerImpl.
 */
@Service
public class SimpleMarshallerImpl implements SimpleMarshaller {

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.common.tool.Marshaller#
	 * unmarshallFromXml(java.lang.Class, java.lang.String)
	 */
	@Override
	public <T> T unmarshalFromXml(Class<T> clazz, String xml)
			throws SimpleMarshallerException {
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(clazz);
		} catch (JAXBException e) {
			throw new SimpleMarshallerException(e);
		}
		return unmarshallFromXml(context, clazz, xml);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.common.tool.Marshaller# marshall(java.lang.Object)
	 */
	@Override
	public String marshal(Object obj) throws SimpleMarshallerException {
		JAXBContext context;
		String output;
		try {
			context = JAXBContext.newInstance(obj.getClass());

			// Create the marshaller, this is the nifty little thing that will
			// actually transform the object into XML
			final Marshaller marshaller = context.createMarshaller();

			// Create a stringWriter to hold the XML
			final StringWriter stringWriter = new StringWriter();

			// Marshal the javaObject and write the XML to the stringWriter
			marshaller.marshal(obj, stringWriter);
			output = stringWriter.toString();
		} catch (JAXBException e) {
			throw new SimpleMarshallerException(e);
		}
		return output;
	}

	@SuppressWarnings("unchecked")
	private <T> T unmarshallFromXml(JAXBContext context, Class<T> clazz,
			String xml) throws SimpleMarshallerException {
		Unmarshaller um;
		try {
			um = context.createUnmarshaller();
			ByteArrayInputStream input = new ByteArrayInputStream(
					xml.getBytes());
			return (T) um.unmarshal(input);
		} catch (JAXBException e) {
			throw new SimpleMarshallerException(e);
		}
	}
}

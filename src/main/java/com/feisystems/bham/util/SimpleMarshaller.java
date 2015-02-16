package com.feisystems.bham.util;

public interface SimpleMarshaller {

	public abstract <T> T unmarshalFromXml(Class<T> clazz, String xml)
			throws SimpleMarshallerException;

	public abstract String marshal(Object obj)
			throws SimpleMarshallerException;

}

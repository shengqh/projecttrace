package edu.vanderbilt.cqs;

import org.springframework.core.convert.converter.Converter;

public class BooleanToStringConverter implements Converter<Boolean, String> {
	public String convert(Boolean bool) {
		return bool ? "Yes" : "No";
	}
}
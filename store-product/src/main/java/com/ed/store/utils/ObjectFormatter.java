package com.ed.store.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectFormatter {

	public String objectToJson(Object object) {

		 ObjectMapper objectMapper = new ObjectMapper();
		 String jsonString = "";
		 try {
			 jsonString = objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		 return jsonString;
	}
}

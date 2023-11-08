package com.ed.shopping.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage {
	private String code;
	private List<Map<String, String>> message;

	public String formatMessaage(BindingResult result) {

		List<Map<String, String>> errors = result.getFieldErrors().stream().map(err -> {
			Map<String, String> error = new HashMap<>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;
		}).collect(Collectors.toList());
		ErrorMessage errorMessage = ErrorMessage.builder().code("01").message(errors).build();

//		return errorMessage.toString();
		 ObjectFormatter objectFormatter = new ObjectFormatter();
		 String jsonString = objectFormatter.objectToJson(errorMessage);
		 return jsonString;

	}
}

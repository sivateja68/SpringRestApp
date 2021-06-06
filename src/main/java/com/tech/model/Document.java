package com.tech.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Document {

	private String fileName;
	private String contentType;
	private String url;
}

package com.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tech.model.Document;
import com.tech.service.IDocumentService;

@RestController
public class DocumentController {

	@Autowired
	private IDocumentService service;
	
	@PostMapping("/file")
	 Document fileUpload(@RequestParam("page") MultipartFile file) {
		String fileName = service.storeFile(file);
		String url = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/download/")
				.path(fileName).toUriString();
		String contentType = file.getContentType();
		Document doc = new Document(fileName,contentType,url);
		return doc;
	}
}

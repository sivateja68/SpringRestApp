package com.tech.service;

import org.springframework.web.multipart.MultipartFile;

public interface IDocumentService {

	 String storeFile(MultipartFile file);
}

package com.tech.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tech.service.IDocumentService;

@Service
public class DocumentServiceImpl implements IDocumentService {

	private Path fileStorage;
	
	@SuppressWarnings("unused")
	private String fileStorageLocation;
	
	public DocumentServiceImpl(@Value("${file.storage.location:temp}") String fileStorageLocation) {
		this.fileStorageLocation = fileStorageLocation;
		fileStorage = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(fileStorage);
		} catch (IOException e) {

			throw new RuntimeException("Issue In creating Directory");
		}
	}
	
	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path filepath = Paths.get(fileStorage+ "\\" + fileName);
		try {
			Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Issue In storing File");
		}
		return fileName;
	}

}

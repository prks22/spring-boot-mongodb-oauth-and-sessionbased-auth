package com.demo.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.demo.common.dto.UploadFileResponseDto;
import com.demo.common.exception.FileStorageException;
import com.demo.entity.FileStorage;
import com.demo.manager.FileStorageManager;
import com.demo.service.FileStorageService;

@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileStorageServiceImpl implements FileStorageService {

	private final Path fileStorageLocation;

	@Inject
	FileStorageManager fileStorageManager;

	@Autowired
	public FileStorageServiceImpl(@Value("${file.upload.dir}") final String fileUploadDirectory)
			throws FileStorageException {
		this.fileStorageLocation = Paths.get(fileUploadDirectory).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (final Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	@Override
	public String storeFile(final MultipartFile file, final String userId) throws FileStorageException {
		// Normalize file name
		final String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with
			// the same name)
			final Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			final FileStorage fileStorage = new FileStorage();
			fileStorage.setCreatedTime(new Date());
			fileStorage.setPath(targetLocation.toString());
			fileStorage.setFileName(fileName);
			fileStorage.setUserId(userId);
			fileStorage.setSize(file.getSize());
			fileStorageManager.save(fileStorage);
			return fileName;
		} catch (final IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	@Override
	public Resource loadFileAsResource(final String fileName) throws FileStorageException {
		try {
			final Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			final Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileStorageException("File not found " + fileName);
			}
		} catch (final MalformedURLException ex) {
			throw new FileStorageException("File not found " + fileName, ex);
		}
	}

	@Override
	public List<UploadFileResponseDto> getUserAllFileInfo(final String userId) throws FileStorageException {
		return fileStorageManager.getUserAllFileInfo(userId);
	}
}

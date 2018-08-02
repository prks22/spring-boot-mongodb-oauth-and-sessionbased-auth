package com.demo.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.demo.common.dto.UploadFileResponseDto;
import com.demo.common.exception.FileStorageException;

public interface FileStorageService {

	String storeFile(MultipartFile file, String userId) throws FileStorageException;

	Resource loadFileAsResource(String fileName) throws FileStorageException;

	List<UploadFileResponseDto> getUserAllFileInfo(String userId) throws FileStorageException;

}

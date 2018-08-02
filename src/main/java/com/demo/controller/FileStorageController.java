package com.demo.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.common.dto.UploadFileResponseDto;
import com.demo.common.exception.FileStorageException;
import com.demo.common.security.UserPrinciple;
import com.demo.service.FileStorageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@Slf4j
public class FileStorageController {
	@Inject
	private FileStorageService fileStorageService;

	@PostMapping("/uploadFile")
	public UploadFileResponseDto uploadFile(@RequestParam("file") final MultipartFile file)
			throws FileStorageException {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String fileName = fileStorageService.storeFile(file,
				((UserPrinciple) authentication.getPrincipal()).getUserId());
		final String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		return new UploadFileResponseDto(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponseDto> uploadMultipleFiles(@RequestParam("files") final MultipartFile[] files)
			throws FileStorageException {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());

	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable final String fileName, final HttpServletRequest request)
			throws FileStorageException {
		// Load file as Resource
		final Resource resource = fileStorageService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (final IOException ex) {
			log.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping("/fileInfo")
	public List<UploadFileResponseDto> getUserAllFileInfo(@PathVariable final String userId) {
		final List<UploadFileResponseDto> files = fileStorageService.getUserAllFileInfo(userId);
		for (final UploadFileResponseDto uploadFileResponseDto : files) {
			final String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
					.path(uploadFileResponseDto.getFileName()).toUriString();
			uploadFileResponseDto.setFileDownloadUri(fileDownloadUri);
		}
		return files;
	}

}

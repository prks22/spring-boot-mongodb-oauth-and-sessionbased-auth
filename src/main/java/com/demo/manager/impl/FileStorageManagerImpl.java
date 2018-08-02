package com.demo.manager.impl;

import java.util.List;

import javax.inject.Inject;

import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.common.dto.UploadFileResponseDto;
import com.demo.common.exception.FileStorageException;
import com.demo.entity.FileStorage;
import com.demo.manager.FileStorageManager;
import com.demo.repository.FileStorageRepository;
import com.demo.utils.Utils;

@Component
@Transactional
public class FileStorageManagerImpl implements FileStorageManager {
	@Inject
	FileStorageRepository fileStorageRepository;

	@Override
	public void save(final FileStorage fileStorage) throws FileStorageException {
		fileStorageRepository.save(fileStorage);

	}

	@Override
	public List<UploadFileResponseDto> getUserAllFileInfo(final String userId) {
		final List<FileStorage> datas = fileStorageRepository.findByUserId(userId);
		final java.lang.reflect.Type targetListType = new TypeToken<List<UploadFileResponseDto>>() {
		}.getType();
		final List<UploadFileResponseDto> uploadFileResponseDto = Utils.mapList(datas, targetListType);
		return uploadFileResponseDto;
	}
}

package com.demo.manager;

import java.util.List;

import com.demo.common.dto.UploadFileResponseDto;
import com.demo.entity.FileStorage;

public interface FileStorageManager {

	public void save(FileStorage fileStorage);

	public List<UploadFileResponseDto> getUserAllFileInfo(String userId);

}

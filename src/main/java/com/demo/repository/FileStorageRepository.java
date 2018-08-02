package com.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.FileStorage;

@Repository
public interface FileStorageRepository extends MongoRepository<FileStorage, String> {

	List<FileStorage> findByUserId(String userId);

}

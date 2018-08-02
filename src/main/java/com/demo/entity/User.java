package com.demo.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {
	@Id
	String id = UUID.randomUUID().toString();
	String name;
	String password;
	@Indexed(unique = true)
	String email;
	String mobile;	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	Date createdTime = new Date();

}

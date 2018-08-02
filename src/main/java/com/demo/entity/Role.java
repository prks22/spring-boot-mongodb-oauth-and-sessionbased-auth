package com.demo.entity;

import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Entity(name="roles")
@Data
public class Role {
	
	Long id;
	String name;
	String description;
	
	
}

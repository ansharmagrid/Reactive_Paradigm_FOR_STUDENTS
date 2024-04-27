package com.user.info.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Document
@AllArgsConstructor
public class UserInfoEntity {

	@Id
	private String id;
	private String name;
	private String phone;
	
}

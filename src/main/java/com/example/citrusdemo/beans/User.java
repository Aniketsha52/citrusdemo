package com.example.citrusdemo.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {

	@Id
	private Long id;

	@Column(name = "Name")
	private String userName;

}

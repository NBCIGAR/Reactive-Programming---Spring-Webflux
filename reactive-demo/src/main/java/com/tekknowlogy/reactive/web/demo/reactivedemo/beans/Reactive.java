package com.tekknowlogy.reactive.web.demo.reactivedemo.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
public class Reactive {
    public Reactive(String id, String title, String mode) {
    	this.id = id;
    	this.title = title;
    	this.mode = mode;
	}
	@Id @JsonProperty
    private String id;
	@JsonProperty
    private String title;
	@JsonProperty
    private String mode;
}
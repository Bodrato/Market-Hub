package com.markethub.smarkethubback.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LoginObject {
	private String email;
	private String password;
}

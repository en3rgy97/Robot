package com.fo0.robot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = { "address", "port", "username" })
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Host {

	private String label;
	private String address;
	@Builder.Default
	private int port = 22;

	@Builder.Default
	private String username = "root";

	private String password;

	@Builder.Default
	private boolean active = true;

}
package com.c4_soft.springaddons.security.oauth2.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import com.c4_soft.springaddons.security.oauth2.config.SpringAddonsSecurityProperties.AuthoritiesMappingProperties;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;

public class AuthoritiesMappingPropertiesTest {

	@Test
	public void test() {
		final var client1Roles = new JSONArray();
		client1Roles.addAll(List.of("R11", "R12"));

		final var client2Roles = new JSONArray();
		client2Roles.addAll(List.of("R21", "R22"));

		final var client3Roles = new JSONArray();
		client3Roles.addAll(List.of("R31", "R32"));

		final var realmRoles = new JSONArray();
		realmRoles.addAll(List.of("r1", "r2"));

		// @formatter:off
		final var claims = new JSONObject(Map.of(
				"resource_access", new JSONObject(Map.of(
						"client1", new JSONObject(Map.of("roles", client1Roles)),
						"client2", new JSONObject(Map.of("roles", client2Roles)),
						"client3", new JSONObject(Map.of("roles", client3Roles)))),
				"realm_access", new JSONObject(Map.of("roles", realmRoles))));
		// @formatter:on

		final var properties = new AuthoritiesMappingProperties();
		properties.setClaims(new String[] { "realm_access.roles", "resource_access.client1.roles", "resource_access.client3.roles" });
		properties.setPrefix("CHOSE_");
		properties.setToUpperCase(true);

		assertThat(properties.mapAuthorities(claims).map(GrantedAuthority::getAuthority).toList())
				.containsExactlyInAnyOrder("CHOSE_R11", "CHOSE_R12", "CHOSE_R31", "CHOSE_R32", "CHOSE_R1", "CHOSE_R2");

		properties.setPrefix("");
		properties.setToUpperCase(false);

		assertThat(properties.mapAuthorities(claims).map(GrantedAuthority::getAuthority).toList())
				.containsExactlyInAnyOrder("R11", "R12", "R31", "R32", "r1", "r2");

	}

}
/*
 * Copyright 2020 Jérôme Wacongne
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.c4_soft.springaddons.security.oauth2;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import reactor.core.publisher.Flux;

/**
 * <p>
 * Extract spring-security GrantedAuthorities from a JWT private claims.
 * </p>
 * Sample implementation for Keyclkoak, getting roles from "realm_access" claim:
 *
 * <pre>
 * &#64;Bean
 * public ReactiveJwt2GrantedAuthoritiesConverter authoritiesConverter() {
 * 	return (var jwt) -&gt; {
 * 		final var roles =
 * 				Optional
 * 						.ofNullable((JSONObject) jwt.getClaims().get("realm_access"))
 * 						.flatMap(realmAccess -&gt; Optional.ofNullable((JSONArray) realmAccess.get("roles")))
 * 						.orElse(new JSONArray());
 * 		return Flux.fromStream(roles.stream().map(Object::toString).map(role -&gt; new SimpleGrantedAuthority("ROLE_" + role)));
 * 	};
 * }
 * </pre>
 *
 * @author ch4mp@c4-soft.com
 */
public interface ReactiveJwt2GrantedAuthoritiesConverter extends Converter<Jwt, Flux<GrantedAuthority>> {
}
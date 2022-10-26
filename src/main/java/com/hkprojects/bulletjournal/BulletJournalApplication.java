package com.hkprojects.bulletjournal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "The Bullet Journal API", version = "1.0", contact = @Contact(email = "kuntzedevprojects@gmail.com", name = "Hassan Kuntze")),
		servers = {@Server(url = "http://localhost:8080"), @Server(url = "https://bullet-journal-backend.herokuapp.com")})
@SecurityScheme(name="thebulletjournal-doc-scheme", scheme = "basic", in = SecuritySchemeIn.HEADER, type = SecuritySchemeType.HTTP)
public class BulletJournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BulletJournalApplication.class, args);
	}

}

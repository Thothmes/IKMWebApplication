package ru.kulakov.IKMWebApplication;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class IkmWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(IkmWebApplication.class, args);
	}

}

package com.example.RelaxWithGems;

import com.example.RelaxWithGems.Models.HelloWorld;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class RelaxWithGemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelaxWithGemsApplication.class, args);
	}

	@CrossOrigin(origins = {"http://localhost:4200", "https://relaxwithgems.netlify.com"})
	@GetMapping("/hello")
	public HelloWorld hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		HelloWorld result = new HelloWorld();
		result.setMessage(String.format("Hello %s! It's time for gitgut domination", name));
		return result;


		// Angular app hello aanroepen
		// Angular dev naar laptop luisteren en in production naar server
		// Tip: Angular enviroments
	}

}

package com.example.RelaxWithGems;

import com.example.RelaxWithGems.Models.HelloWorld;
import com.example.RelaxWithGems.Models.Item;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://relaxwithgems.netlify.com"})
@SpringBootApplication
@RestController
public class RelaxWithGemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelaxWithGemsApplication.class, args);
	}

	@GetMapping("/hello")
	public HelloWorld hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		HelloWorld result = new HelloWorld();
		result.setMessage(String.format("Hello %s! It's time for gitgut domination", name));
		return result;
	}

	@GetMapping("/items")
	public Item[] items(){
		// for all items in firebase
		// for now:
		Item[] array = new Item[]{
				new Item("Rozenkwarts", "Roze enzo", "imagePath", new String[]{}),
				new Item("Amathist", "Paars enzo", "imagePath", new String[]{}),
				new Item("Fluoriet", "Veel verschillende kleuren enzo", "imagePath", new String[]{})
		};
		return array;
	}

}

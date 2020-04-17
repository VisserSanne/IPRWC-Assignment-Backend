package com.example.RelaxWithGems;

import com.example.RelaxWithGems.Models.HelloWorld;
import com.example.RelaxWithGems.Models.Item;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = {"http://localhost:4200", "https://relaxwithgems.netlify.com"})
@SpringBootApplication
@RestController
public class RelaxWithGemsApplication {

	@Autowired
	private Environment env;

	public static Firestore db;

	public static void main(String[] args) {
		SpringApplication.run(RelaxWithGemsApplication.class, args);
	}

	@GetMapping("/hello")
	public HelloWorld hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		HelloWorld result = new HelloWorld();
		result.setMessage(String.format("Hello %s! It's time for gitgut domination", name));
		return result;
	}


	@Value("${FIREBASE_CONFIG}")
	private String firebaseSDK;

	@Bean
	public FirebaseAuth firebaseAuth() throws IOException {
		InputStream serviceAccount = new ByteArrayInputStream(firebaseSDK.getBytes());
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://relaxwithgems.firebaseio.com")
				.build();
		if (FirebaseApp.getApps().isEmpty()){
			FirebaseApp.initializeApp(options);

		}

		db = FirestoreClient.getFirestore();
		return FirebaseAuth.getInstance();
	}

	public static Firestore getdb(){
		return db;
	}

}

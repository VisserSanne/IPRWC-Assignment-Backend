package com.example.RelaxWithGems;

import com.example.RelaxWithGems.Models.HelloWorld;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://relaxwithgems.netlify.com", "https://relaxwithgems.netlify.app"})
@SpringBootApplication
@RestController
public class RelaxWithGemsApplication {

	public static Firestore db;
	public static Bucket storage;

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
				.setStorageBucket("relaxwithgems.appspot.com")
				.build();
		if (FirebaseApp.getApps().isEmpty()){
			FirebaseApp.initializeApp(options);

		}

		db = FirestoreClient.getFirestore();
		this.storage = StorageClient.getInstance().bucket();
		return FirebaseAuth.getInstance();
	}

	public static Firestore getdb(){
		return db;
	}

}

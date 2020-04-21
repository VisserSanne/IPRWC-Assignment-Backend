package com.example.RelaxWithGems.Controllers;

import com.example.RelaxWithGems.RelaxWithGemsApplication;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = {"http://localhost:4200", "https://relaxwithgems.netlify.com"})
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{uid}")
    public String items(@PathVariable(value = "uid") String uid) throws ExecutionException, InterruptedException {
        Firestore db = getDb();

        DocumentSnapshot document = db.collection("users").document(uid).get().get();

        return document.getString("role");
    }

    public Firestore getDb(){
        return RelaxWithGemsApplication.getdb();
    }

}

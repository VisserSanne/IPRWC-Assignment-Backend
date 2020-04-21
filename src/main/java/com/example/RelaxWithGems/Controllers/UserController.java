package com.example.RelaxWithGems.Controllers;

import com.example.RelaxWithGems.Models.Item;
import com.example.RelaxWithGems.RelaxWithGemsApplication;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

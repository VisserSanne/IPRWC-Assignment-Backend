package com.example.RelaxWithGems.Controllers;

import com.example.RelaxWithGems.RelaxWithGemsApplication;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = {"http://localhost:4200", "https://relaxwithgems.netlify.com"})
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{uid}")
    public String getRole(@PathVariable(value = "uid") String uid) throws ExecutionException, InterruptedException {
        Firestore db = getDb();

        DocumentSnapshot document = db.collection("users").document(uid).get().get();

        return document.getString("role");
    }

    @PostMapping("/{uid}")
    public String setRole(@PathVariable(value = "uid") String uid, @RequestBody String role){
        Firestore db = getDb();

        Map<String, Object> docData = new HashMap<>();
        docData.put("role", role);

        ApiFuture<WriteResult> addedDocRef = db.collection("users").document(uid).set(docData);

        return role;
    }

    public Firestore getDb(){
        return RelaxWithGemsApplication.getdb();
    }

}

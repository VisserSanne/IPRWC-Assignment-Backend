package com.example.RelaxWithGems.Controllers;

import com.example.RelaxWithGems.Models.Item;
import com.example.RelaxWithGems.RelaxWithGemsApplication;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = {"http://localhost:4200", "https://relaxwithgems.netlify.com"})
@RestController
@RequestMapping("/basket")
public class BasketController {

    @GetMapping("/{id}")
    public Map<String,Object> GetBasketByUserID(@PathVariable(value = "id") String id) throws ExecutionException, InterruptedException {

        Firestore db = getDb();

        ApiFuture<DocumentSnapshot> query = db.collection("baskets").document(id).get();
        DocumentSnapshot document = query.get();

        return document.getData();
    }

    public Firestore getDb(){
        return RelaxWithGemsApplication.getdb();
    }
}

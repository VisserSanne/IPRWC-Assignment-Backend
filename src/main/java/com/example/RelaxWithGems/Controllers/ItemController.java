package com.example.RelaxWithGems.Controllers;

import com.example.RelaxWithGems.Models.Item;
import com.example.RelaxWithGems.RelaxWithGemsApplication;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = {"http://localhost:4200", "https://relaxwithgems.netlify.com"})
@RestController
@RequestMapping("/items")
public class ItemController {

    @GetMapping("")
    public List<Item> items() throws ExecutionException, InterruptedException {
        Firestore db = getDb();

        List<Item> itemList = new ArrayList<>();

        ApiFuture<QuerySnapshot> query = db.collection("items").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            Item item = new Item(document.getId(), document.getString("name"), document.getString("description"), document.getString("imagePath"));
            itemList.add(item);
        }

        return itemList;
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable(value = "id") String id) throws ExecutionException, InterruptedException {
        Firestore db = getDb();

        DocumentSnapshot document = db.collection("items").document(id).get().get();
        Item item = new Item(document.getId(), document.getString("name"), document.getString("description"), document.getString("imagePath"));

        return item;
    }

    public Firestore getDb(){
        return RelaxWithGemsApplication.getdb();
    }
}

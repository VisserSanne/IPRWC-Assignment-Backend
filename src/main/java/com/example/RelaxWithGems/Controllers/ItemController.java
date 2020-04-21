package com.example.RelaxWithGems.Controllers;

import com.example.RelaxWithGems.Models.Item;
import com.example.RelaxWithGems.RelaxWithGemsApplication;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            Item item = new Item(document.getId(), document.getString("name"), document.getString("description"), document.getDouble("price"), document.getString("imagePath"));
            itemList.add(item);
        }

        return itemList;
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable(value = "id") String id) throws ExecutionException, InterruptedException {
        Firestore db = getDb();

        DocumentSnapshot document = db.collection("items").document(id).get().get();
        Item item = new Item(document.getId(), document.getString("name"), document.getString("description"), document.getDouble("price"), document.getString("imagePath"));

        return item;
    }

    @PostMapping("")
    public String setItem(@RequestBody Item item) throws ExecutionException, InterruptedException {
        Firestore db = getDb();

        Map<String, Object> docData = new HashMap<>();

        docData.put("name", item.name);
        docData.put("description", item.description);
        docData.put("price", item.price);
        docData.put("imagePath", item.imagePath);
        System.out.println(docData);
        ApiFuture<DocumentReference> addedDocRef = db.collection("items").add(docData);

        return ("Added document with ID: " + addedDocRef.get().getId());
    }

    @PutMapping("/{id}")
    public String updateItemByID(@RequestBody Item item, @PathVariable(value = "id") String id) throws ExecutionException, InterruptedException {
        Firestore db = getDb();

        Map<String, Object> docData = new HashMap<>();

        docData.put("name", item.name);
        docData.put("description", item.description);
        docData.put("price", item.price);
        docData.put("imagePath", item.imagePath);
        System.out.println(docData);
        ApiFuture<WriteResult> addedDocRef = db.collection("items").document(id).set(docData);

        return ("Added document with ID: " + addedDocRef.get().getUpdateTime());
    }

    @DeleteMapping("/{id}")
    public String deleteItemByID(@PathVariable(value = "id") String id) throws ExecutionException, InterruptedException {
        Firestore db = getDb();
        System.out.println(id);
        ApiFuture<WriteResult> writeResult = db.collection("items").document(id).delete();
//        db.collection("items").document(id).delete();
        System.out.println("Update time : " + writeResult.get().getUpdateTime());
        return ("Update time : " + writeResult.get().getUpdateTime());
    }

    public Firestore getDb(){
        return RelaxWithGemsApplication.getdb();
    }
}

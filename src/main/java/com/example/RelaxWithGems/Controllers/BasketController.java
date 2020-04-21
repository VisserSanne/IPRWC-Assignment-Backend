package com.example.RelaxWithGems.Controllers;

import com.example.RelaxWithGems.Models.Item;
import com.example.RelaxWithGems.RelaxWithGemsApplication;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = {"http://localhost:4200", "https://relaxwithgems.netlify.com"})
@RestController
@RequestMapping("/basket")
public class BasketController {

    @GetMapping("/{id}")
    public List<Item> GetBasketByUserID(@PathVariable(value = "id") String id) throws ExecutionException, InterruptedException {
        Firestore db = getDb();

        ApiFuture<DocumentSnapshot> query = db.collection("baskets").document(id).get();
        DocumentSnapshot document = query.get();

        ArrayList<String> itemIDs = (ArrayList<String>) document.get("items");

        List<Item> itemList = new ArrayList<>();

        for (String itemID: itemIDs) {
            DocumentSnapshot itemDocument = db.collection("items").document(itemID).get().get();
            if(itemDocument.getString("name") != null){
                Item item = new Item(itemDocument.getId(), itemDocument.getString("name"), itemDocument.getString("description"), itemDocument.getDouble("price"), itemDocument.getString("imagePath"));
                itemList.add(item);
            }
            else {
                itemList.add(new Item(itemID,"","",0,""));
            }
        }

        return itemList;
    }

    @PutMapping("/{uid}")
    public String putItems(@PathVariable(value = "uid") String uid, @RequestBody Item[] items) throws ExecutionException, InterruptedException {
        Firestore db = getDb();

        List<String> docData = new ArrayList<>();
        for(Item item : items){
            docData.add(item.id);
        }

        ApiFuture<WriteResult> writeResult = db.collection("baskets").document(uid).update("items", docData);

        return ("Update time : " + writeResult.get().getUpdateTime());
    }

    public Firestore getDb(){
        return RelaxWithGemsApplication.getdb();
    }
}

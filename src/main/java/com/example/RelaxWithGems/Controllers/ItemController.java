package com.example.RelaxWithGems.Controllers;

import com.example.RelaxWithGems.Models.Item;
import com.example.RelaxWithGems.RelaxWithGemsApplication;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.example.RelaxWithGems.RelaxWithGemsApplication.db;

@CrossOrigin(origins = {"http://localhost:4200", "https://relaxwithgems.netlify.com", "https://relaxwithgems.netlify.app"})
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
    public Item setItem(@RequestBody Item item) throws ExecutionException, InterruptedException {
        Firestore db = getDb();

        Map<String, Object> docData = new HashMap<>();

        docData.put("name", item.name);
        docData.put("description", item.description);
        docData.put("price", item.price);
        docData.put("imagePath", item.imagePath);
        ApiFuture<DocumentReference> addedDocRef = db.collection("items").add(docData);
        String updatedID = addedDocRef.get().get().get().getId();
        item.id = updatedID;
        return (item);
    }
    @PostMapping("/{id}/uploadImage")
    public void setItem(@PathVariable(value = "id") String id, @RequestParam("imageFile") MultipartFile file) throws IOException {
        String timeSTamp = Instant.now().toString();
        String[] fileFrags = file.getOriginalFilename().split("\\.");
        String extension = fileFrags[fileFrags.length-1];
        String fileName = Base64.getEncoder().encodeToString((timeSTamp+file.getName()).getBytes())+"."+extension;
        String blob = "images/"+fileName;
        Blob uploadedFile = getStorage().create(blob, file.getInputStream(), file.getContentType());
        Map<String, Object> docData = new HashMap<>();
        String publicURL = uploadedFile.getMediaLink();
        docData.put("imagePath", publicURL);
        ApiFuture<WriteResult> addedDocRef = db.collection("items").document(id).update(docData);
    }

    @PutMapping("/{id}")
    public Item updateItemByID(@RequestBody Item item, @PathVariable(value = "id") String id) throws ExecutionException, InterruptedException {
        Firestore db = getDb();

        Map<String, Object> docData = new HashMap<>();

        docData.put("name", item.name);
        docData.put("description", item.description);
        docData.put("price", item.price);
        docData.put("imagePath", item.imagePath);
        ApiFuture<WriteResult> addedDocRef = db.collection("items").document(id).set(docData);

        return (item);
    }

    @DeleteMapping("/{id}")
    public Object  deleteItemByID(@PathVariable(value = "id") String id) throws ExecutionException, InterruptedException {
        Firestore db = getDb();

        ApiFuture<WriteResult> writeResult = db.collection("items").document(id).delete();

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    public Firestore getDb(){
        return RelaxWithGemsApplication.getdb();
    }
    public Bucket getStorage(){
        return RelaxWithGemsApplication.storage;
    }
}

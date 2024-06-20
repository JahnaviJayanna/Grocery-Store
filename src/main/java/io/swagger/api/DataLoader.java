package io.swagger.api;

import io.swagger.entity.ItemTypes;
import io.swagger.entity.ItemsCategory;
import io.swagger.entity.Users;
import io.swagger.model.*;
import io.swagger.repository.ItemCategoryRepository;
import io.swagger.repository.ItemTypesRepository;
import io.swagger.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    EntityManager entityManager;

    private final ItemTypesRepository itemTypesRepository;
    private final UserRepository userRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    public DataLoader(ItemTypesRepository itemTypesRepository, UserRepository userRepository, ItemCategoryRepository itemCategoryRepository) {
        this.itemTypesRepository = itemTypesRepository;
        this.userRepository = userRepository;
        this.itemCategoryRepository = itemCategoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
        loadCategoryItems();
        loadCategoryItemType();
    }

    private void loadUsers() {
        String userId = "US." + System.currentTimeMillis();
        Users user = new Users(userId, "Master", "Admin", "masterAdmin@gcomviva.com", "Inventory@123", "9988776655", new Date(1998, 12, 12), "MAS123", "ADMIN");
        if (userRepository.findById(userId).isEmpty()) {
            try {
                userRepository.save(user);
                System.out.println("User added successfully");
            } catch (IllegalArgumentException e) {
                System.err.println("Failed to add user: " + e.getMessage());
                throw new IllegalArgumentException("Failed to add user: " + e.getMessage());
            }
        }
    }

    private void loadCategoryItems() {
        for (InventoryItemDetailsPayload.CategoryNameEnum category : InventoryItemDetailsPayload.CategoryNameEnum.values()) {
            ItemsCategory newCategory = new ItemsCategory(String.valueOf(category));
            try {
                itemCategoryRepository.save(newCategory);
                System.out.println("Category added successfully");
            } catch (IllegalArgumentException e) {
                System.err.println("Failed to add Category: " + e.getMessage());
                throw new IllegalArgumentException("Failed to add Category: " + e.getMessage());
            }
        }
    }

    private void loadCategoryItemType() {
        List<ItemsCategory> categories = itemCategoryRepository.findAll();
        categories.forEach(category -> {
            switch (category.getCategoryName()){
                case "Dairy" :
                    for (Dairy val : Dairy.values()) {
                        ItemTypes itemType = new ItemTypes(String.valueOf(val), category);
                        try {
                            itemTypesRepository.save(itemType);
                            System.out.println("Item type added successfully");
                        } catch (IllegalArgumentException e) {
                            System.err.println("Failed to add item type : " + e.getMessage());
                            throw new IllegalArgumentException("Failed to add item type: " + e.getMessage());
                        }
                    }
                    break;
                case "Grains and Cereals" :
                    for (GrainsAndCereals val : GrainsAndCereals.values()) {
                        ItemTypes itemType = new ItemTypes(String.valueOf(val), category);
                        try {
                            itemTypesRepository.save(itemType);
                            System.out.println("Item type added successfully");
                        } catch (IllegalArgumentException e) {
                            System.err.println("Failed to add item type : " + e.getMessage());
                            throw new IllegalArgumentException("Failed to add item type: " + e.getMessage());
                        }
                    }
                    break;
                case "Snacks" :
                    for (Snacks val : Snacks.values()) {
                        ItemTypes itemType = new ItemTypes(String.valueOf(val), category);
                        try {
                            itemTypesRepository.save(itemType);
                            System.out.println("Item type added successfully");
                        } catch (IllegalArgumentException e) {
                            System.err.println("Failed to add item type : " + e.getMessage());
                            throw new IllegalArgumentException("Failed to add item type: " + e.getMessage());
                        }
                    }
                    break;
                case "Beverages" :
                    for (Beverages val : Beverages.values()) {
                        ItemTypes itemType = new ItemTypes(String.valueOf(val), category);
                        try {
                            itemTypesRepository.save(itemType);
                            System.out.println("Item type added successfully");
                        } catch (IllegalArgumentException e) {
                            System.err.println("Failed to add item type : " + e.getMessage());
                            throw new IllegalArgumentException("Failed to add item type: " + e.getMessage());
                        }
                    }
                    break;
                case "Candy and Sweets" :
                    for (CandyAndSweets val : CandyAndSweets.values()) {
                        ItemTypes itemType = new ItemTypes(String.valueOf(val), category);
                        try {
                            itemTypesRepository.save(itemType);
                            System.out.println("Item type added successfully");
                        } catch (IllegalArgumentException e) {
                            System.err.println("Failed to add item type : " + e.getMessage());
                            throw new IllegalArgumentException("Failed to add item type: " + e.getMessage());
                        }
                    }
                    break;
                case "Household and Cleaning" :
                    for (HouseHoldAndCleaning val : HouseHoldAndCleaning.values()) {
                        ItemTypes itemType = new ItemTypes(String.valueOf(val), category);
                        try {
                            itemTypesRepository.save(itemType);
                            System.out.println("Item type added successfully");
                        } catch (IllegalArgumentException e) {
                            System.err.println("Failed to add item type : " + e.getMessage());
                            throw new IllegalArgumentException("Failed to add item type: " + e.getMessage());
                        }
                    }
                    break;
                case "Personal Care" :
                    for (PersonalCare val : PersonalCare.values()) {
                        ItemTypes itemType = new ItemTypes(String.valueOf(val), category);
                        try {
                            itemTypesRepository.save(itemType);
                            System.out.println("Item type added successfully");
                        } catch (IllegalArgumentException e) {
                            System.err.println("Failed to add item type : " + e.getMessage());
                            throw new IllegalArgumentException("Failed to add item type: " + e.getMessage());
                        }
                    }
                    break;
                case "Others" :
                    for (Others val : Others.values()) {
                        ItemTypes itemType = new ItemTypes(String.valueOf(val), category);
                        try {
                            itemTypesRepository.save(itemType);
                            System.out.println("Item type added successfully");
                        } catch (IllegalArgumentException e) {
                            System.err.println("Failed to add item type : " + e.getMessage());
                            throw new IllegalArgumentException("Failed to add item type: " + e.getMessage());
                        }
                    }
                    break;
            }
        });
    }
}
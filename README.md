# Grocery Store Application API

This API allows you to manage the inventory of a grocery store, including adding, updating, fetching, and selling items. It also supports user management functionalities such as user login, logout, creation, and deletion.

## API Version

**Version:** 1.0.0

## Description

This API provides various endpoints for managing inventory and sales in a grocery store application. It supports operations like adding items to inventory, listing items, updating items, selling items, and managing user accounts. It has system generated list of categoryName and ItemTypes for each category where admin user can add Items to inventory for correct combination of CategoryName and ItemType values. Staff users will be able to Sell the items present in inventory.

## Authentication

All endpoints are secured and require Bearer Token authentication.

## Endpoints

### Inventory Management

#### Add Items to Inventory

- **URL:** `/v1/create/inventory`
- **Method:** `POST`
- **Tags:** Admin
- **Description:** API to add new items to the inventory with all required details. Based on CategoryName and ItemType values present in system. Admin user authorised to perform activities.
- **Request Body:**
  - `InventoryItemDetailsPayload`: Details of the items to add to the system based on given input for CategoryName and ItemType with unique itemName value for each items. ItemType passed should belong to categoryName present in the system.
- **Responses:**
  - `200`: Successfully added new items to inventory.
  - `400`: Error while adding items to inventory.
  - `500`: Internal server error.
  - `401`: Unauthorized Request.

#### List Items in Inventory

- **URL:** `/v1/fetch/inventory`
- **Method:** `GET`
- **Tags:** Admin, Staff
- **Description:** Fetch the list of items present in inventory inventory based on userRole. Admin users will be able to fetch items even when no items is present in inventory. Staff users will be able to fetch only available items to inventory. When itemId is provided details of respective item will be returned.
- **Parameters:**
  - `itemId` (query): Id of the inventory item (optional).
- **Responses:**
  - `200`: Successfully fetched the list of items.
  - `400`: Error while fetching the list of items.
  - `500`: Internal server error.
  - `401`: Unauthorized Request.

#### Delete Inventory Item

- **URL:** `/v1/delete/inventory/{itemId}`
- **Method:** `DELETE`
- **Tags:** Admin
- **Description:** Delete an item from the inventory. Only the Admin user is authorized to delete items from Inventory.
- **Parameters:**
  - `itemId` (path): Id of the inventory item to be deleted.
- **Responses:**
  - `200`: Successfully deleted the inventory item.
  - `400`: Error while deleting the inventory item.
  - `500`: Internal server error.
  - `401`: Unauthorized Request.
  

#### Update Items in Inventory

- **URL:** `/v1/update/inventory/{itemId}`
- **Method:** `PUT`
- **Tags:** Admin
- **Description:** Update the details of an existing inventory item based on ItemId and itemName from the request body. Only the Admin user is authorized to update items from inventory.
- **Parameters:**
  - `itemId` (path): Id of the inventory item to be updated.
- **Request Body:**
  - `UpdateInventoryItemsPayload`: Updated details of the inventory item.
- **Responses:**
  - `200`: Successfully updated the item details.
  - `400`: Error while updating the item details.
  - `500`: Internal server error.
  - `401`: Unauthorized Request.
  

### Sales Management

#### Sell Items

- **URL:** `/v1/sell`
- **Method:** `POST`
- **Tags:** Admin, Staff
- **Description:** API to sell items from the inventory. Admin and staff users are allowed to sell items available in inventory with all details itemName, price, units, quantity. On successful sales transaction Id will be generated.
- **Request Body:**
  - `SellItemsPayload`: Details of the items to sell.
- **Responses:**
  - `200`: Successfully sold the items.
  - `400`: Error while selling the items.
  - `500`: Internal server error.
  - `401`: Unauthorized Request.
  

#### Fetch Sales Details

- **URL:** `/v1/fetch/sales`
- **Method:** `GET`
- **Tags:** Admin, Staff
- **Description:** Fetch sales details by based on transaction ID generated during sales.
- **Parameters:**
  - `txnId` (query): Transaction ID to fetch sales details.
- **Responses:**
  - `200`: Successfully fetched sales details.
  - `400`: Error while fetching sales details.
  - `500`: Internal server error.
  - `401`: Unauthorized Request.

### User Management

#### User Login

- **URL:** `/v1/user/login`
- **Method:** `POST`
- **Tags:** Admin, Staff
- **Description:** Logs a user into the system.
- **Request Body:**
  - `UserLogin`: User login details.
- **Responses:**
  - `200`: Successfully logged in the user.
  - `400`: Error while logging in the user.
  - `500`: Internal server error.
  - `401`: Unauthorized Request.
  
#### User Logout

- **URL:** `/v1/user/logout/{userName}`
- **Method:** `POST`
- **Tags:** Admin, Staff
- **Description:** Logs out the current logged-in user session.
- **Parameters:**
  - `userName` (path): User name of the user to log out.
- **Request Body:**
  - `UserLogout`: User logout details.
- **Responses:**
  - `200`: Successfully logged out the user.
  - `400`: Error while logging out the user.
  - `500`: Internal server error.
  - `401`: Unauthorized Request.
  
#### Create User

- **URL:** `/v1/create/user`
- **Method:** `POST`
- **Tags:** Admin
- **Description:** Creates a new user with all the valid details.
- **Request Body:**
  - `CreateUser`: User creation details.
- **Responses:**
  - `200`: Successfully created the user.
  - `400`: Error while creating the user.
  - `500`: Internal server error.
  - `401`: Unauthorized Request.

#### Delete User

- **URL:** `/v1/delete/user/{userName}`
- **Method:** `DELETE`
- **Tags:** Admin
- **Description:** Deletes a user with the given username.
- **Parameters:**
  - `userName` (path): Username of the user to be deleted.
- **Responses:**
  - `200`: Successfully deleted the user.
  - `400`: Error while deleting the user.
  - `500`: Internal server error.
  - `401`: Unauthorized Request.
 
  
  #### Fetch User Details
  - **URL:** `/v1/fetch/user/details`
  - **Method:** `DELETE`
  - **Tags:** Admin
  - **Description:** Deletes a user with the given username.
  - **Parameters:**
    - `userName` (query): Username of the user to be deleted.
  - **Responses:**
    - `200`: Successfully deleted the user.
    - `400`: Error while deleting the user.
    - `500`: Internal server error.
    - `401`: Unauthorized Request.


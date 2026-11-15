import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
                                                            // imports
public class FinishedProject {                                                      // main class
    public static void main(String[] args) {
        
        User user = new User("User");                                 // Creates User object

        GroceryStore groceryStore = new GroceryStore();                    // Creates GroceryStore object
        groceryStore.populateStock();                                      // Populates GroceryStore object

        Menu menu = new Menu();                                            // Creates Menu

        menu.runMainMenu(user, groceryStore);                              // Runs created Menu, using User & GroceryStore objects as parameters
    }
}








// MENU

class Menu {
    void runMainMenu(User user, GroceryStore groceryStore) {        // Main Menu
        System.out.println("--- Online Grocery Store ---");
        while (true) {                                              // While true User Input loop
            System.out.println("Select an option:");
            System.out.println("  1. View grocery store");          // Lists Main Menu options, asks for a User Input after fully printed
            System.out.println("  2. View user profile");
            System.out.println("  3. Exit");
            int choice = In.nextInt();
            if (choice == 1) {
                runGroceryStore(user, groceryStore);                // Runs grocery store sub-menu
            } else if (choice == 2) {
                runUserProfile(user, groceryStore);                 // Runs user profile sub-menu
            } else if (choice == 3) {
                break;                                              // Ends program
            } else {                                                // The "break" command ends the while loop
                System.out.println("Pick an option 1, 2, or 3");
            }
        }

        System.out.println("Exiting...");
    }

    void runGroceryStore(User user, GroceryStore groceryStore) {    // Grocery Store sub-menu
        System.out.println("--- Store Products ---");
        while (true) {                                              // Another while loop
            System.out.println("Select an option:");
            System.out.println("  1. View products");
            System.out.println("  2. View reward tasks");
            System.out.println("  3. Back to main menu");
            int choice = In.nextInt();                             // Asks for user input
            if (choice == 1) {
                System.out.println(groceryStore.searchProducts()); // Runs searchProducts() method within the grocery store object
                System.out.println("Purchase products?");          // Asks if purchase products
                System.out.println("  1. Yes\n  2. No");
                choice = In.nextInt();
                if (choice == 1){
                    groceryStore.makeSelection(user);              // Runs makeSelection() method on the grocery store object
                } else if (choice == 2){                           // using the user object as a parameter
                    System.out.println("\n");
                } else {
                    System.out.println("Invalid option.");
                }
            } else if (choice == 2) {
                List<RewardProduct> rewardProducts = new ArrayList<>(); // Initialises an ArrayList of rewardProducts
                for (Product product : groceryStore.productStock){ 
                    if (product instanceof RewardProduct){              // A simple for loop that checks through the productStock of 
                        rewardProducts.add((RewardProduct) product);    // the groceryStore object, and checks which items are RewardProducts
                    }                                                   // if it is a reward product, it adds it to the previously initialised
                }                                                       // list. And then prints out displayRewardTasks() using it as a parameter
                System.out.println(groceryStore.displayRewardTasks(rewardProducts));
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Pick an option 1, 2, 3 or 4");
            }
        }
        System.out.println("Back to main menu...");
    }

    void runUserProfile(User user, GroceryStore groceryStore) {     // User Profile sub-menu
        System.out.println("--- Your Profile ---");
        while (true) {
            System.out.println("Select an option:");
            System.out.println("  1. View shopping cart");
            System.out.println("  2. View statistics");
            System.out.println("  3. Back to main menu");
            int choice = In.nextInt();
            if (choice == 1) {
                runUserCart(user, groceryStore);                   // Runs user cart sub-menu
            } else if (choice == 2) {
                System.out.println(user);                          // Prints user toString
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Pick an option 1, 2, or 3");
            }
        }
        System.out.println("Back to main menu...");
    }

    void runUserCart(User user, GroceryStore groceryStore) {    // User cart sub-menu
        System.out.println("--- Your Cart ---");
        while (true) {
            System.out.println("Select an option:");
            System.out.println("  1. Review cart");
            System.out.println("  2. Pay for cart");
            System.out.println("  3. Back to main menu");
            int choice = In.nextInt();
            if (choice == 1) {
                System.out.println(user.getCart().displayProducts(user.getCartList())); // Calls the user object's cart, using the displayProducts() method on itself. The user object's own getCartList() method is used as the parameters, listing out the cart list
                System.out.println("  " + user.getCart());                              // Prints out the user object cart's toString
                if (user.getCart().getCartSize() > 0){
                    System.out.println("\nRemove products?");                // Asks to remove products if the user's cart contains items
                System.out.println("  1. Yes\n  2. No");
                choice = In.nextInt();
                if (choice == 1){
                    user.getCart().removeProduct();                         // Calls the user's own cart to call the removeProduct() method on itself
                } else if (choice == 2){
                    System.out.println("");
                } else {
                    System.out.println("Invalid option.");
                }
            }
            } else if (choice == 2) {
                user.getCart().checkout();                                  // Calls the user's own cart to call the checkout method on itself
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Pick an option 1, 2, or 3");
            }
        }
        System.out.println("Back to main menu...");
    }
}
   









// ENUM & INTERFACE
enum ProductType {                      // A ProductType enum, utilised in the attributes for both the Product Class and the GroceryStore Class
    FRUIT,      //SELF EXPLANATORY
    VEGETABLES, //SELF EXPLANATORY
    MEAT,       //MEAT & SEAFOOD
    DAIRY,      //MILK, CHEESE, ETC.
    FROZEN,     //FROZEN FOOD
    PANTRY,     //SNACKS & CONFECTIONARY
    HOUSEHOLD,  //CLEANING AND HEALTH
}


interface StockManagement {                             // A StockManagement interface used for adding, removing and display products
    void addProduct(Product product);
    void removeProduct(Product product);
    String displayProducts(List<Product> products);
}

interface ProductSearch {                               // A ProductSearch interface used for searching for products, includes a filter
    String searchProducts();
    String searchFilteredProducts(ProductType type);
}

interface Rewardable {                                  // A Rewardable interface implemented in the RewardProduct child class
    int getRewardValue();
}








//GROCERY STORE
class GroceryStore implements StockManagement, ProductSearch {  // GroceryStore class
    List<Product> productStock; // An ArrayList of Products, serves as the stocklist
    List<Product> currentList;  // An ArrayList of Products, used for selection methods to prevent code confusion
    Map<ProductType, List<Product>> productFilter; // A HashMap used to filter through the stocklist based on the ProductType enum

    GroceryStore(){
        this.productStock = new ArrayList<>();
        this.currentList = new ArrayList<>();

        this.productFilter = new HashMap<>();
        this.productFilter.put(ProductType.FRUIT, new ArrayList<>());
        this.productFilter.put(ProductType.VEGETABLES, new ArrayList<>());
        this.productFilter.put(ProductType.MEAT, new ArrayList<>());
        this.productFilter.put(ProductType.DAIRY, new ArrayList<>());
        this.productFilter.put(ProductType.FROZEN, new ArrayList<>());
        this.productFilter.put(ProductType.PANTRY, new ArrayList<>());
        this.productFilter.put(ProductType.HOUSEHOLD, new ArrayList<>());
    }

    public String searchProducts() {                        //Product Search method
        String list = "--- All Products ---";               //Initialises String that will be returned at the end of the method

        System.out.println("Apply filter?");                //Asks for Search Filters
                System.out.println("  1. Yes\n  2. No");
                int choice = In.nextInt();
        if (choice == 1){
            System.out.println("Select Filter.");
            System.out.println("  1. Fruit");
            System.out.println("  2. Vegetables");
            System.out.println("  3. Meat");
            System.out.println("  4. Dairy");
            System.out.println("  5. Frozen");
            System.out.println("  6. Pantry");
            System.out.println("  7. Household");
            choice = In.nextInt();
            if (choice == 1){
                list = "--- Fruit Products ---";
                list += searchFilteredProducts(ProductType.FRUIT); // Runs searchFilteredProducts() method, using the ProductType as a parameter
                list += "\n----------------------";                
            } else if (choice == 2){
                list = "--- Vegetable Products ---";
                list += searchFilteredProducts(ProductType.VEGETABLES);
                list += "\n-------------------------";
            } else if (choice == 3){
                list = "--- Meat Products ---";
                list += searchFilteredProducts(ProductType.MEAT);
                list += "\n--------------------";
            } else if (choice == 4){
                list = "--- Dairy Products ---";
                list += searchFilteredProducts(ProductType.DAIRY);
                list += "\n---------------------";
            } else if (choice == 5){
                list = "--- Frozen Products ---";
                list += searchFilteredProducts(ProductType.FROZEN);
                list += "\n----------------------";
            } else if (choice == 6){
                list = "--- Pantry Products ---";
                list += searchFilteredProducts(ProductType.PANTRY);
                list += "\n----------------------";
            } else if (choice == 7){
                list = "--- Household Products ---";
                list += searchFilteredProducts(ProductType.HOUSEHOLD);
                list += "\n-------------------------";
            } else {
                System.out.println("Invalid choice.");
            }
        } else if (choice == 2){
            list += displayProducts(this.productStock); // Runs the displayProducts method, using the grocery store object's own productStock attribute as a paramter
            list += "\n--------------------";
        } else {
            System.out.println("Select options 1 or 2");
        }
        return list;
    }

    public String searchFilteredProducts(ProductType type){         // An alternative search method used for filtering. Requires a ProductType as a paramater
        List<Product> filteredProducts = productFilter.get(type);   // Initialises a filteredProducts list, calling the groceryStore's productFilter HashMap attribute, using the provided ProductType as a parameter
        return displayProducts(filteredProducts);                   // The displayProducts method is called at the end, using the initialised list as a parameter
    }

    public String displayProducts(List<Product> products){          // displayProducts method uses a List of Product objects as its parameters
        String list = "";                                           // Initialises a list that will be returned at the end
        if (products.size() > 0){     // First checks if the List has items
            for (int i = 0; i < products.size(); i++) {     // Iterates through the entire list, using the integer variable "i" as a counter. 
            list += "\n" + (i+1) + ". " + products.get(i);  // The "i" variable is used as a numbered list creator, as well as functioning to iterate through each index value of the given List
            }                                               // Each object in the index is added to the list variable, containing their toString information
            this.currentList = products;    // Sets the grocery store's currentList as the created display of products for later selection
        } else {
            list += "\n" + "No products available.";    // If the list does not contain items, it notifies
        }
        return list;    // The previously initialised String variable is returned, listing out each product as a numbered list that also contains their relevant information
    }

    public String displayRewardTasks(List<RewardProduct> rewardProducts){   // displayRewardTasks is similar to the displarProducts method, only that it works for the RewardProducts
         String list = "";                                                  // It's used to create a numbered list of all of the grocery store object's RewardProducts
        if (rewardProducts.size() > 0){
            for (int i = 0; i < rewardProducts.size(); i++) {
            list += "\n" + (i+1) + ". " + rewardProducts.get(i);    
            }
        } else {
            list += "\n" + "No products available.";                
        }
        return list + "\n";
    }

    public void addProduct(Product product){           // Adds a product to the grocery store's stock list, as well as a HashMap corresponding to their ProductType
        this.productStock.add(product);         

        this.productFilter.get(product.productType).add(product);
    }

    public void removeProduct(Product product){       // The reverse of the above method
        this.productStock.remove(product);

        this.productFilter.get(product.productType).remove(product);
    }

    public void makeSelection(User user){       // This is a makeSelection method that uses the User as a parameter
         displayProducts(this.currentList);     // The grocery store's currently selected list is used as a parameter to display products
        while (true){
            System.out.println("Make selection");
            int choice = In.nextInt();
            if (choice > 0 && choice <= this.currentList.size()){
            user.getCart().addProduct(this.currentList.get(choice -1)); // The currentList attribute is used to avoid confusion when the user is selecting products
            System.out.println("Selected " + this.currentList.get(choice -1).getProductName()); // Given that, since the searchFilter exists, each time the displayProducts method is used, it could display a completely different numbered list.

            System.out.println("  Cart size: " + user.getCart().getCartSize()); // Lists the user's cart stats after selection
            System.out.println("  Cart cost: " + user.getCart().getCartPrice());
            System.out.println("  Cart points: " + user.getCart().getCartPoints());
            break;
            } else {
                System.out.println("Invalid selection");
            }
        }
    }

    void populateStock(){                       // Populates groceryStore object

    // FRUIT
    addProduct(new Product("Kiwi Fruit", ProductType.FRUIT, 2.99));
    addProduct(new Product("Bananas", ProductType.FRUIT, 3.50));
    addProduct(new DiscountProduct("Apples", ProductType.FRUIT, 5.99));

    // VEGETABLES
    addProduct(new Product("Carrots", ProductType.VEGETABLES, 2.50));
    addProduct(new Product("Potatoes", ProductType.VEGETABLES, 4.99));
    addProduct(new RewardProduct("Broccoli", ProductType.VEGETABLES, 3.49, 10));

    // MEAT
    addProduct(new Product("Rump Steak", ProductType.MEAT, 11.99));
    addProduct(new Product("Chicken Breast", ProductType.MEAT, 9.99));
    addProduct(new DiscountProduct("Pork Sausages", ProductType.MEAT, 7.99));

    // DAIRY
    addProduct(new Product("Milk", ProductType.DAIRY, 5.99));
    addProduct(new Product("Lactose Free Milk", ProductType.DAIRY, 4.99));
    addProduct(new Product("Cheddar Cheese", ProductType.DAIRY, 3.99));
    addProduct(new RewardProduct("Greek Yoghurt", ProductType.DAIRY, 6.50, 15));

    // FROZEN
    addProduct(new Product("Frozen Peas", ProductType.FROZEN, 3.99));
    addProduct(new Product("Frozen Pizza", ProductType.FROZEN, 8.99));
    addProduct(new DiscountProduct("Ice Cream", ProductType.FROZEN, 6.99));

    // PANTRY
    addProduct(new Product("White Rice", ProductType.PANTRY, 3.50));
    addProduct(new Product("Pasta", ProductType.PANTRY, 2.99));
    addProduct(new RewardProduct("Breakfast Cereal", ProductType.PANTRY, 5.99, 20));

    // HOUSEHOLD
    addProduct(new Product("Dishwashing Liquid", ProductType.HOUSEHOLD, 4.50));
    addProduct(new Product("Laundry Detergent", ProductType.HOUSEHOLD, 12.99));
    addProduct(new DiscountProduct("Paper Towels", ProductType.HOUSEHOLD, 5.99));
    }
}
















//SHOPPING CART
class ShoppingCart {                // ShoppingCart class
    User owner;         // It actually has it's own attribute specifying the owner of the cart. Used for ease of calling the owner object
    List<Product> cart; // Also has a list of products similar to grocery store object's productStock
    double cartPrice;   // The cost of each item in the cart is added together and put into the cartPrice
    int cartPoints;     // The same for the reward value of each RewardProduct

    ShoppingCart(User owner){
        this.owner = owner;
        this.cart = new ArrayList<>();
        this.cartPrice = 0;
        this.cartPoints = 0;
    }

    public String toString(){
        return "Cart has " + this.cart.size() + " items with a total of $" + this.cartPrice + " and " + this.cartPoints + " reward points.";
    }

    public int getCartSize(){
        return this.cart.size();
    }

    public double getCartPrice(){
        return this.cartPrice;
    }

    public int getCartPoints(){
        return this.cartPoints;
    }

    public User getOwner(){
        return this.owner;
    }

    public Product getProduct(int i){
        return this.cart.get(i);
    }

    public String displayProducts(List<Product> products){ // Implemented StockManagement method, same as above
       String list = "";                                           
        if (products.size() > 0){                          // Checks if cart contains items
            for (int i = 0; i < products.size(); i++) {
            list += "\n" + (i+1) + ". " + products.get(i);          
            }
        } else {
            list += "\n" + "Cart is empty.";                
        }
        return list;
    }

    public void addProduct(Product product){
        this.cart.add(product);

        if (product instanceof RewardProduct){   // Checks if the given product is an instanceof RewardProduct, so the reward product's value can be given
            addReward((RewardProduct) product);
        }

        addCost(product);
    }

    public void addCost(Product product){           // Self explanatory
        this.cartPrice += product.getProductCost();
    }

    public void addReward(RewardProduct product){    // Adds reward product value to cart points
        this.cartPoints += product.getRewardValue();
    }

    public void deductReward(RewardProduct product){ // Reverse of above
        this.cartPoints -= product.getRewardValue();

        if (this.cartPoints < 0){
            this.cartPoints = 0;
        }
    }

    public void removeProduct(){
        System.out.println(displayProducts(this.cart));                 // First prints out a numbered list of the cart's items
        System.out.println("Which product would you like to remove?");
        int i = In.nextInt();
        if (i > 0 && i <= getCartSize()){
            if (this.cart.get(i-1) instanceof RewardProduct) {
                deductReward((RewardProduct) this.cart.get(i-1));       // Remembers to deduct reward points the object is an instance of RewardProduct
            }
            this.cartPrice -= this.cart.get(i-1).getProductCost();      // Deducts object cost from cart price
            this.cart.remove((i-1));        // Removes object from list
        } else {
            System.out.println("Invalid selection.");
        }
    }
    
    public void checkout() {
        if (getCartSize() <= 0) {
            System.out.println("Cart has no items.");
        } else {
            System.out.println("You are checking out:\n" + displayProducts(this.cart) + "\n  Your total is: $" + this.cartPrice     // Displays the cart's items, as wel as the price
        + "\n  You have earned " + this.cartPoints +" Reward Points");      // Notifies user of how many reward points they have earned
        getOwner().rewardPoints += getCartPoints();                         // If they do have any cart points, it gives the user equivalent reward points to account
            System.out.println("\n  Payment completed. Thank you for shopping with us.\n");             // Notifies user that the payment is completed
            for (int i = getCartSize()-1; i >= 0; i--){
                this.cart.remove(i);
                this.cartPrice = 0;         // Empties cart of items and returns cart price to zero
                }
            }
        }
    }



















// USER
class User {
    String name;
    ShoppingCart shoppingCart;    // The user contains its own attribute of it's owned ShoppingCart object, so each class actually has eachother as attributes
    int rewardPoints;   // The user's total reward points after purchasing RewardProduct objects

    User(String name){
        this.name = name;
        this.shoppingCart = new ShoppingCart(this);
        this.rewardPoints = 0;
    }

    public String toString() {
        return getName() + " has " + getRewardPoints() + " reward points.";
    }

    public String getName() {
        return this.name;
    }

    public ShoppingCart getCart() {
        return this.shoppingCart;
    }

    public List<Product> getCartList() {
        return this.shoppingCart.cart;
    }

    protected void setName(String name){
        this.name = name;
    }

    public int getRewardPoints(){
        return this.rewardPoints;
    }
}


















// PRODUCTS
class Product {                                    // Very self explanatory inheritances
    protected String productName;
    protected ProductType productType;      // Contains ProductType enum as an attribute
    protected double productCost;

    Product(String productName, ProductType productType, double productCost){
        this.productName = productName;
        this.productType = productType;
        this.productCost = productCost;
    }

    public String toString(){
        return this.productName + " ($" + this.productCost + ")";
    }

    public String getProductName(){
        return this.productName;
    }

    public ProductType getProductType(){
        return this.productType;
    }

    public double getProductCost(){
        return this.productCost;
    }
}

class DiscountProduct extends Product {
    double discountPrice;

    DiscountProduct(String productName, ProductType productType, double productCost){
        super(productName, productType, productCost);
        this.productName = "Discounted " + this.productName;
        this.discountPrice = this.productCost -= (this.productCost / 2);

    }

    @Override
    public String toString(){
        return this.productName + " ($" + this.discountPrice + ")";
    }

    @Override
    public double getProductCost(){
        return this.discountPrice;
    }
}

class RewardProduct extends Product implements Rewardable {
    int rewardValue;

    RewardProduct(String productName, ProductType productType, double productCost, int rewardValue){
        super(productName, productType, productCost);
        this.rewardValue = rewardValue;
    }

    @Override
    public String toString(){
        return this.productName + " ($" + this.productCost + ")" + " +" + this.rewardValue + " Reward Points";
    }

    public int getRewardValue(){
        return this.rewardValue;
    }
}




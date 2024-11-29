import java.util.Random;

public class Inventory {

    private String productID;
    private String productName;
    private String description;
    private float price;
    private int quantityInStock;



    /*
     * Default Constructor
     */

    public Inventory(String productName, String description, float price, int quantityInStock){
        this.productID = genID();
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
        

    }

    /*
     * Constructor
     */

     public Inventory(String id, String productName, String description, float price, int quantityInStock){
        this.productID = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
        

    }


     // This method generates a unique product ID

    public static String genID(){
        // first part of id: Consists of a 2 letter segment ascii format, e.g "AB"
        char ascii_0 = (char) (new Random().nextInt(26) + 65);
        char ascii_1 = (char) (new Random().nextInt(26) + 65);
        String rand_ascii_Str =  Character.toString(ascii_0) + Character.toString(ascii_1);

        // second part of id: Consists of the numerical (100 - 999) string segment 
        String rand_strInt = Integer.toString(new Random().nextInt(901) + 100);

        String pid = rand_ascii_Str + rand_strInt;
        return pid;

        
    }



    // Getters for Inventory Class
    
    // Method(getpid): gets the pid for an item
    public String getpid(){
        return this.productID;
    }

    // Method(getProdName): gets the name for an item
    public String getProdName(){
        return this.productName;
    }

    // Method(getProdDesc): gets the description for an item
    public String getProdDesc(){
        return this.description;
    }

    // Method(getPrice): gets the price of a item
    public float getPrice(){
        return this.price;
    }

    // Method(getQuantityInStock): gets the current quantity of an item
    public int getQuantityInStock(){
        return this.quantityInStock;
    }



    // Setters for Inventory Class

    // Method(setpid): sets the name for an item
    public void setpid(String pid){
        this.productID = pid;
    }


    // Method(setProdName): sets the name for an item
    public void setProdName(String pName){
        this.productName = pName;
    }

    // Method(setProdDesc): sets the a description for an item
    public void setProdDesc(String pDesc){
        this.description = pDesc;
    }

    // Method(setPrice): sets the price of a item
    public void setPrice(float price_amt){
        price = price_amt;
    }

    // method(setQuantity): sets the quantity of a item
    public void setQuantityInStock(int amt){
        this.quantityInStock = amt;
    }


    // method(updateQuantityInStock): updates the stock of an item after purchase
    public void updateStock(int amt, String operator){
        int updated_amt = 0;

        if (operator == "+"){
            updated_amt = (getQuantityInStock() + amt);
        }
        else if (operator == "-"){
            updated_amt = (getQuantityInStock() -  amt);
        }

        setQuantityInStock(updated_amt);
    }


  
    // toString method
    @Override
    public String toString() {
        return String.format("Inventory [pid: %s, name: %s, description: %s, price: %f, quantity: %d]", 
        getpid(), getProdName(), getProdDesc(), getPrice(), getQuantityInStock());
    }

    
}

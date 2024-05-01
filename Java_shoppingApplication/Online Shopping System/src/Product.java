import java.io.Serializable;

public abstract class Product implements Serializable {

    String productId;
    String pName;
    String pCat;
    int  price;
    int numItemLeft;

    public Product(String productId, String pName, String pCat,int numItemLeft,int price) {
        this.productId = productId;
        this.pName = pName;
        this.pCat = pCat;
        this.numItemLeft = numItemLeft;
        this.price=price;
    }

    public Product(String productId, String pName, int numItemLeft) {
        this.productId =productId;
        this.pName = pName;
        this.numItemLeft = numItemLeft;

    }

    public String getProductId() {
        return productId;
    }
    public String getpCat() {
        return pCat;
    }

    public String getpName() {
        return pName;
    }

    public int getPrice() {
        return price;
    }

    public int getNumItemLeft() {
        return numItemLeft;
    }

    /*public void setProductId(String productId) {
        this.productId = productId;
    }*/

   /* public void setpName(String pName) {
        this.pName = pName;
    }

    public void setpCat(String pCat) {
        this.pCat = pCat;
    }*/

    public void setPrice(int price) {
        this.price = price;
    }

    public void setNumItemLeft(int numItemLeft) {
        this.numItemLeft = numItemLeft;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", pName='" + pName + '\'' +
                ", pCat='" + pCat + '\'' +
                ", price=" + price +
                ", numItemLeft=" + numItemLeft +
                '}';
    }

    /*public Object getInfo() {
        return pName;
    }*/
}

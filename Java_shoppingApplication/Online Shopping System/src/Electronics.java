public class Electronics extends Product {

    private String brand;
    private int warrantyPeriod;



    public Electronics(String productId, String pName, String pCat, int numItemLeft, String brand, int warrantyPeriod,int price) {
        super(productId, pName, pCat, numItemLeft, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    /*public Electronics(String brand, int warrantyPeriod,String productId , String pName, int numItemLeft) {
        super(productId, pName,  numItemLeft);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }*/


    public String getBrand() {
        return brand;
    }

    /*public void setBrand(String brand) {
        this.brand = brand;
    }*/

    public int  getWarrantyPeriod() {
        return warrantyPeriod;
    }

//    public void setWarrantyPeriod(int warrantyPeriod) {
//        this.warrantyPeriod = warrantyPeriod;
//    }

    @Override
    public String toString() {
        return  super.toString() +
                "brand='" + brand + '\'' +
                ", warrantyPeriod=" + warrantyPeriod +
                "} " ;
    }
}



public class Clothing extends Product{

    private String size;
    private String colour;



    public Clothing(String productId, String pName, String pCat, int numItemLeft, String size, String colour,int price) {
        super(productId, pName, pCat, numItemLeft,price);
        this.size = size;
        this.colour = colour;
    }

//    public Clothing(String productId, String pName, int numItemLeft, String size, String colour) {
//        super(productId, pName, numItemLeft);
//        this.size = size;
//        this.colour = colour;
//    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

//    public void setColour(String colour) {
//        this.colour = colour;
//    }

    @Override
    public String toString() {
        return  super.toString() +
                "size='" + size + '\'' +
                ", colour='" + colour + '\'' +
                "} " ;
    }
}


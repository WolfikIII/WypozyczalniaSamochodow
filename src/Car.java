public class Car {
    long id;
    String mark;
    String model;
    int production_year;
    double price;
    String isAvailable;

    public Car(long id, String mark, String model, int production_year, double price, String isAvailable){
        this.id=id;
        this.mark=mark;
        this.model=model;
        this.production_year = production_year;
        this.price=price;
        this.isAvailable = isAvailable;
    }
    public Car(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getProduction_year() {
        return production_year;
    }

    public void setProduction_year(int production_year) {
        this.production_year = production_year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void displayCar(){
        System.out.println(id + ". " + mark + " '" + model + "'      rok produckji: " + production_year + "      cena: " + price + "zł" + "dostępny? " + isAvailable);
    }
}

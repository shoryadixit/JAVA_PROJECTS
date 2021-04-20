package SomeProblem.Productrating;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static java.math.RoundingMode.HALF_UP;

public abstract class Product implements Relatable<Product>{
    public static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.1);
    private final int id;
    private final String name;
    private final BigDecimal price;
    private final Rating rating;

    /*Product() {
        this(0, "no_name", BigDecimal.ZERO);
    }
    //For immutability we have to remove or comment the setter methods
    *//*public void setId(int id) {
        this.id = id;
    }*/

    Product(int id, String name, BigDecimal price, Rating rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    /*public void setName(String name) {
        this.name = name;
    }*/

    Product(int id, String name, BigDecimal price) {
        this(id, name, price, Rating.NOT_RATED);
    }

    /*public void setPrice(BigDecimal price) {
        this.price = price;
    }*/

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscountRate() {
        return price.multiply(DISCOUNT_RATE).setScale(2, HALF_UP);
    }

    @Override
    public Rating getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Product {" +
                " id = " + id +
                ", name = '" + name + '\'' +
                ", price = " + price +
                ", rating = " + rating.getStars() +
                ", bestBefore = " + getBestBefore() +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id; //&& Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

   // public abstract Product applyRating(Rating newrating);
    /*{
        return new Product(this.id, this.name, this.price, newrating);
    }*/
    public LocalDate getBestBefore() {
        return LocalDate.now();
    }

    public abstract BigDecimal getDiscount();
}

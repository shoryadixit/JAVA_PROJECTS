package SomeProblem.Product;

import SomeProblem.Productrating.Product;
import SomeProblem.Productrating.ProductManager;
import SomeProblem.Productrating.Rating;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

public class Shop {

    public static void main(String[] args) {
        ProductManager pm = new ProductManager("en-US");
        pm.createProduct(101, "Tea", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
        //pm.printProductReport(101);
        pm.reviewProduct(101, Rating.FIVE_STAR, "Nice");
        pm.reviewProduct(101, Rating.FOUR_STAR, "Good");
        pm.reviewProduct(101, Rating.THREE_STAR, "Average");
        pm.reviewProduct(101, Rating.TWO_STAR, "Not Good");
        pm.reviewProduct(101, Rating.ONE_STAR, "Worst");
        //pm.printProductReport(101);
        pm.changeLocale("en-US");

        pm.createProduct(102, "Coffee1", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
        pm.reviewProduct(102, Rating.THREE_STAR, "Coffee Ok!");
        pm.reviewProduct(102, Rating.FOUR_STAR, "Coffee Good");
        pm.reviewProduct(102, Rating.TWO_STAR, "Coffee Nice");
       // pm.printProductReport(102);

        pm.createProduct(103, "Coffee2", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
        pm.reviewProduct(103, Rating.ONE_STAR, "Coffee2 Ok!");
        pm.reviewProduct(103, Rating.FOUR_STAR, "Coffee2 Good");
        pm.reviewProduct(103, Rating.TWO_STAR, "Coffee2 Nice");
       // pm.printProductReport(103);

        pm.createProduct(104, "Coffee3", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
        pm.reviewProduct(104, Rating.NOT_RATED, "Coffee3 Ok!");
        pm.reviewProduct(104, Rating.FOUR_STAR, "Coffee3 Good");
        pm.reviewProduct(104, Rating.THREE_STAR, "Coffee3 Nice");
       // pm.printProductReport(104);


        /* Product p1 = pm.createProduct(101, "Tea", BigDecimal.valueOf(1.99), Rating.THREE_STAR);
        System.out.println(p1.getId() + ", " + p1.getName() + ", " + p1.getPrice()
                + ", " + p1.getDiscountRate() + ", " + p1.getRating().getStars());
        Product p2 = pm.createProduct(102, "Coffee", BigDecimal.valueOf(1.99), Rating.FOUR_STAR);
        Product p3 = pm.createProduct(103, "Cake", BigDecimal.valueOf(1.99), Rating.FIVE_STAR, LocalDate.now().plusDays(2));
        Product p4 = pm.createProduct(104, "Chocolate", BigDecimal.valueOf(2.99), Rating.FIVE_STAR);
        Product p5 = pm.createProduct(104, "Chocolate", BigDecimal.valueOf(2.99), Rating.FIVE_STAR, LocalDate.now().plusDays(2));
        System.out.println(p2);
        System.out.println(p3);
        //this .equals method is compare their object but int his have different object is stored one is food or one is Drink
        System.out.println(p4.equals(p5));
        System.out.println(p4.getDiscountRate());
        System.out.println(p5.getDiscountRate());
        p1 = p1.applyRating(Rating.FOUR_STAR);
        System.out.println(p1);
        Product p7 = pm.createProduct(105, "Cookie", BigDecimal.valueOf(3.99), Rating.TWO_STAR, LocalDate.now());
        System.out.println();*/
        pm.createProduct(105,"Sabji", BigDecimal.valueOf(123.9), Rating.NOT_RATED, LocalDate.now().plusDays(1));
        pm.reviewProduct(105, Rating.FIVE_STAR, "Nice");
        pm.printProductReport(105);
        Comparator<Product> ratingSorter = (p1,p2) -> p2.getRating().ordinal() - p1.getRating().ordinal();
        Comparator<Product> pricesorter = (p1,p2) -> p2.getPrice().compareTo(p1.getPrice());
        pm.printProducts(p -> p.getPrice().floatValue() < 2, (p1,p2) -> p2.getRating().ordinal() - p1.getRating().ordinal());

        pm.getDiscounts().forEach(
                (rating, discount) -> System.out.println(rating + "\t" + discount)
        );

    }
}

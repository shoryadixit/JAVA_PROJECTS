package SomeProblem.Productrating;

import java.math.BigDecimal;
import java.time.LocalTime;

public final class Drink extends Product{
    Drink(int id, String name, BigDecimal price, Rating rating) {
        super(id, name, price, rating);
    }

    @Override
    public Product applyRating(Rating newrating) {
        return new Drink(getId(), getName(), getPrice(), newrating);
    }

    @Override
    public BigDecimal getDiscount() {
        LocalTime now = LocalTime.now();
        return (now.isAfter(LocalTime.of(17, 30))
                && now.isBefore(LocalTime.of(18, 30)))
                ? super.getDiscountRate() : BigDecimal.ZERO;
    }
}

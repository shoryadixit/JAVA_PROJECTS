package SomeProblem.Productrating;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class Food extends Product {
    private final LocalDate bestBefore;

    Food(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        super(id, name, price, rating);
        this.bestBefore = bestBefore;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + bestBefore;
    }

    @Override
    public Product applyRating(Rating newrating) {
        return new Food(getId(), getName(), getPrice(), newrating, bestBefore);
    }

    @Override
    public BigDecimal getDiscountRate() {
        return (bestBefore.isEqual(LocalDate.now())) ? super.getDiscountRate() : BigDecimal.ZERO;
    }
}

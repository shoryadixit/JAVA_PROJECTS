package SomeProblem.Productrating;

public interface Relatable<E> {
    public static final Rating DEFAULT_RATING = Rating.NOT_RATED;

    E applyRating(Rating rating);

    default Rating getRating() {
        return DEFAULT_RATING;
    }

    public static Rating convert(int stars){
        return (stars >= 0 && stars <= 5) ? Rating.values()[stars] : DEFAULT_RATING;
    }

    public default E applyRating(int stars) {
        return applyRating(convert(stars));
    }
}

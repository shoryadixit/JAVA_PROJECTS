package SomeProblem.Productrating;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProductManager {

    private static final Map<String, ResourceFormatter> formatters
            = Map.of("en-GB", new ResourceFormatter(Locale.UK),
            "en-US", new ResourceFormatter(Locale.US),
            "fr-FR", new ResourceFormatter(Locale.FRANCE),
            "re-RU", new ResourceFormatter(new Locale("ru", "RU")),
            "zh-CN", new ResourceFormatter(Locale.CHINA));

    private final Map<Product, List<Review>> products = new HashMap<>();

    private ResourceFormatter formatter;

    public ProductManager(Locale locale) {
        this(locale.toLanguageTag());
    }

    public ProductManager(String languageTag) {
        changeLocale(languageTag);
    }

    public static Set<String> getSupportedLocales() {
        return formatters.keySet();
    }

    /*below fields are replaced by upper map methods*/
    /*private Product product;
    private Review[] review = new Review[5];*/
    public void printProducts(Predicate<Product> filter, Comparator<Product> sorter) {
        /*List<Product> productList = new ArrayList<>(products.keySet());
        productList.sort(sorter);*/
        StringBuilder txt = new StringBuilder();
        /*for (Product product : productList) {
            txt.append(formatter.formatProduct(product));
            txt.append('\n');
        }*/
        products.keySet()
                .stream()
                .sorted(sorter)
                .filter(filter)
                .forEach(p -> txt.append(formatter.formatProduct(p) + '\n'));
        System.out.println(txt);
    }

    public void changeLocale(String languageTag) {
        formatter = formatters.getOrDefault(languageTag, formatters.get("en-GB"));
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        Product product = new Food(id, name, price, rating, bestBefore);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
        Product product = new Drink(id, name, price, rating);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product reviewProduct(Product product, Rating rating, String comments) {
        /*if (review[review.length - 1] != null) {
            review = Arrays.copyOf(review, review.length + 5);
        }*/
        List<Review> review = products.get(product);
        products.remove(product, review);
        review.add(new Review(rating, comments));
        product = product.applyRating(Relatable.convert((int) Math.round(review.stream()
                .mapToInt(r -> r.getRating().ordinal())
                .average()
                .orElse(0))));
        /*int sum = 0;
        for (Review reviews : review) {
            sum += reviews.getRating().ordinal();
        }*/
        /*int sum = 0, i = 0;
        boolean reviewed = false;
        while (i < review.length && !reviewed) {
            if (review[i] == null) {
                review[i] = new Review(rating, comments);
                reviewed = true;
            }
            sum += review[i].getRating().ordinal();
            i++;
        }*/
        //review = new Review(rating, comments);
        //this.product = product.applyRating(rating);
        /*product = product.applyRating(Relatable.convert(Math.round((float) sum / review.size())));*/
        products.put(product, review);
        return product;
    }

    /*public Map<String, String> getDiscounts() {
        return products.keySet()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                product -> product.getRating().getStars(),
                                Collectors.collectingAndThen(
                                     Collectors.summingDouble(
                                             product -> product.getDiscount().doubleValue(),
                                             discount -> formatter.moneyFormat.format(discount)
                        )
                )

        ));

    }*/

    public void printProductReport(Product product) {
        List<Review> review = products.get(product);
        StringBuffer txt = new StringBuffer();
        txt.append(formatter.formatProduct(product));
        txt.append('\n');
        Collections.sort(review);
        if (review.isEmpty()) {
            txt.append(formatter.getText("no.reviews") + '\n');
        } else {
            review.stream().forEach(r -> txt.append(formatter.formatReview(r) + '\n'));
        }
        /*for (Review reviews : review) {
            txt.append(formatter.formatReview(reviews));
            txt.append('\n');
        }
        if (review.isEmpty()) {
            txt.append(formatter.getText("no.reviews"));
            txt.append('\n');
        }*/
        System.out.println(txt);
    }

    public Product findProduct(int id) {
        /*Product result = null;
        for (Product product : products.keySet()) {
            if (product.getId() == id) {
                result = product;
                break;
            }
        }
        return result;*/
        return products.keySet()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseGet(() -> null);
    }

    public void printProductReport(int id) {
        printProductReport(findProduct(id));
    }

    public Product reviewProduct(int id, Rating rating, String comments) {
        return reviewProduct(findProduct(id), rating, comments);
    }

    private static class ResourceFormatter {
        private Locale locale;
        private ResourceBundle resources;
        private DateTimeFormatter dateTimeFormatter;
        private NumberFormat moneyFormat;

        private ResourceFormatter(Locale locale) {
            this.locale = locale;
            resources = ResourceBundle.getBundle("Someproblem.Productrating.resources", locale);
            dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
            moneyFormat = NumberFormat.getCurrencyInstance(locale);
        }

        private String formatProduct(Product product) {
            return MessageFormat.format(resources.getString("product"),
                    product.getName(),
                    moneyFormat.format(product.getPrice()),
                    product.getRating().getStars(),
                    dateTimeFormatter.format(product.getBestBefore()));
        }

        private String formatReview(Review review) {
            return MessageFormat.format(resources.getString("review"),
                    review.getRating().getStars(),
                    review.getComments());
        }

        private String getText(String key) {
            return resources.getString(key);
        }
    }
}

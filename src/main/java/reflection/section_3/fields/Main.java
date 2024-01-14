package reflection.section_3.fields;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        Movie movie = new Movie("Lord of the rings", 2001, true, Category.ADVENTURE,12.99 );
        printDeclaredFieldsInto(Movie.class,movie);

        Field field = Movie.class.getDeclaredField("MINIMUM_PRICE");
        System.out.println(String.format("static MINIMUM_PRICE value : %f",field.get(null)));
    }

    public static <T> void printDeclaredFieldsInto(Class<? extends T> clazz, T instance) throws IllegalAccessException {
        for(Field field : clazz.getDeclaredFields()){
            System.out.println(String.format("Field name : %s type : %s",
                    field.getName(),
                    field.getType().getName()));
            System.out.println(String.format("Is synthetic field : %s", field.isSynthetic()));
            System.out.println(String.format("Field value : %s", field.get(instance)));
            System.out.println();
        }
    }

    public static class Movie extends Product{
        public static final double MINIMUM_PRICE = 10.09;
        private boolean isReleased;
        private Category category;
        private double actualPrice;
        public Movie(String name, int year, boolean isReleased, Category category, double actualPrice) {
            super(name, year);
            this.isReleased = isReleased;
            this.category = category;
            this.actualPrice = actualPrice;
        }

        public class MovieStats{
            private double timesWatched;

            public MovieStats(double timesWatched) {
                this.timesWatched = timesWatched;
            }

            public double getRevenue(){
                return timesWatched*actualPrice;
            }
        }
    }
    public static class Product{
        protected String name;
        protected int year;
        protected double actualPrice;

        public Product(String name, int year) {
            this.name = name;
            this.year = year;
        }
    }

    public enum Category{
        ADVENTURE,
        ACTION,
        COMEDY
    }
}

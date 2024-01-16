package reflection.section_3.json_converter_array.data;

public class Movie {
    private final String name;
    private final float rating;
    private final String[] categories;

    private final Actor[] actors;

    public Movie(String name, float rating, String[] categories, Actor[] actors) {
        this.name = name;
        this.rating = rating;
        this.categories = categories;
        this.actors = actors;
    }
}

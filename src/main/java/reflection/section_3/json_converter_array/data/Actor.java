package reflection.section_3.json_converter_array.data;

public class Actor {
    private final String name;
    private final String[] knowForMovies;

    public Actor(String name, String[] knowForMovies) {
        this.name = name;
        this.knowForMovies = knowForMovies;
    }
}

package reflection.section_4.setting_field_value.data;

import java.util.Random;

public class GameConfig {
    private final int releaseYear;
    private String gameName;
    private double  price;

    public GameConfig() {
        Random random = new Random();
        this.releaseYear=random.nextInt(2000);
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getGameName() {
        return gameName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "GameConfig{" +
                "releaseYear=" + releaseYear +
                ", gameName='" + gameName + '\'' +
                ", price=" + price +
                '}';
    }
}

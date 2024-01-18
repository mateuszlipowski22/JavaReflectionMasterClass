package reflection.section_4.setting_field_value.data;

import java.util.Arrays;
import java.util.Random;

public class GameConfig {
    private final int releaseYear;
    private String gameName;
    private double  price;

    private String[] characterName;

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

    public String[] getCharacterName() {
        return characterName;
    }

    @Override
    public String toString() {
        return "GameConfig{" +
                "releaseYear=" + releaseYear +
                ", gameName='" + gameName + '\'' +
                ", price=" + price +
                ", characterName=" + Arrays.toString(characterName) +
                '}';
    }
}

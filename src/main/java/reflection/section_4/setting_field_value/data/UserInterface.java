package reflection.section_4.setting_field_value.data;

import java.util.Arrays;

public class UserInterface {
    private String titleColor;
    private String titleText;
    private short[] titleTextSizes;
    private String[] titleFonts;

    public String getTitleColor() {
        return titleColor;
    }

    public String getTitleText() {
        return titleText;
    }

    public short[] getTitleTextSizes() {
        return titleTextSizes;
    }

    public String[] gettitleFonts() {
        return titleFonts;
    }

    @Override
    public String toString() {
        return "UserInterface{" +
                "titleColor='" + titleColor + '\'' +
                ", titleText='" + titleText + '\'' +
                ", titleTextSizes=" + Arrays.toString(titleTextSizes) +
                ", footerFontitleFonts=" + Arrays.toString(titleFonts) +
                '}';
    }
}

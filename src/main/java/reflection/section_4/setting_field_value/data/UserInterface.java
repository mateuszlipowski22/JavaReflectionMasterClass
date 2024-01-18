package reflection.section_4.setting_field_value.data;

public class UserInterface {
    private String titleColor;
    private String titleText;
    private short titleFontSize;
    private short footerFontSize;

    public String getTitleColor() {
        return titleColor;
    }

    public String getTitleText() {
        return titleText;
    }

    public short getTitleFontSize() {
        return titleFontSize;
    }

    public short getFooterFontSize() {
        return footerFontSize;
    }

    @Override
    public String toString() {
        return "UserInterface{" +
                "titleColor='" + titleColor + '\'' +
                ", titleText='" + titleText + '\'' +
                ", titleFontSize=" + titleFontSize +
                ", footerFontSize=" + footerFontSize +
                '}';
    }
}

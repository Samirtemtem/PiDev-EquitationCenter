package Controllers.components;

import javafx.util.StringConverter;

public class SpinnerIntegerStringConverter extends StringConverter<Integer> {
    private final boolean showLeadingZeroes;

    public SpinnerIntegerStringConverter(boolean showLeadingZeroes) {
        this.showLeadingZeroes = showLeadingZeroes;
    }

    @Override
    public String toString(Integer value) {
        if (value == null) {
            return "";
        }

        if (showLeadingZeroes) {
            return String.format("%02d", value);
        } else {
            return value.toString();
        }
    }

    @Override
    public Integer fromString(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

package co.com.sofka.core.label;

import co.com.sofka.core.label.values.LabelId;

import static co.com.sofka.generic.DomainAssertions.checkNonNullForLabel;

public class Label {
    private final LabelId labelId;
    private final String color;
    private final String tittle;

    public Label(LabelId labelId, String color, String tittle) {
        checkNonNullForLabel(color,"Color can´t be null");
        checkNonNullForLabel(tittle,"Tittle can´t be null");
        this.labelId = labelId;
        this.color = color;
        this.tittle = tittle;
    }

    public String getColor() {
        return color;
    }

    public String getTittle() {
        return tittle;
    }
}

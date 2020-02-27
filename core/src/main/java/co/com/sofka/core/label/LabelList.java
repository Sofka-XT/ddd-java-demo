package co.com.sofka.core.label;

import co.com.sofka.core.label.values.LabelId;
import co.com.sofka.core.label.values.LabelListId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static co.com.sofka.generic.DomainAssertions.checkIfLabelCollectionIsEmpty;
import static co.com.sofka.generic.DomainAssertions.checkNonNullForLabel;

public class LabelList {
    private final LabelListId labelListId;
    private Collection<Label> labelCollection;

    public LabelList(LabelListId labelListId) {
        this.labelListId = labelListId;
        labelCollection = new ArrayList<>();
    }

    public void createLabel(String color, String tittle) {
        LabelId labelId = new LabelId(UUID.randomUUID().toString());
        Label label = new Label(labelId, color, tittle);
        labelCollection.add(label);
    }

    public Collection<Label> getAllLabels() {
        return this.labelCollection;
    }

    public void deleteLabel(String titleLabelToDelete) {
        Label label = findLabelByTittle(titleLabelToDelete);
        labelCollection.remove(label);
    }

    private Label findLabelByTittle(String titleLabelToDelete) {
        checkNonNullForLabel(titleLabelToDelete, "Label tittle to delete can´t be null");
        Collection<Label> list = labelCollection
                .stream()
                .filter(label -> label.getTittle().equalsIgnoreCase(titleLabelToDelete))
                .collect(Collectors.toList());

        String errorMessage = "The label with the tittle ".concat(titleLabelToDelete).concat(" don't exist.");
        checkIfLabelCollectionIsEmpty(list, errorMessage);

        return list.iterator().next();
    }

}

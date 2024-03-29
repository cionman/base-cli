package de.codeshelf.consoleui.elements;

import de.codeshelf.consoleui.elements.items.ChoiceItemIF;
import de.codeshelf.consoleui.elements.items.ConsoleUIItemIF;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * User: Andreas Wegmann
 * Date: 07.01.16
 */
public class ExpandableChoice extends AbstractPromptableElement {

  private LinkedHashSet<ChoiceItemIF> choiceItems;

  public ExpandableChoice(String message, String name, LinkedHashSet<ChoiceItemIF> choiceItems) {
    super(message, name);
    this.choiceItems = choiceItems;
  }

  public ArrayList<ConsoleUIItemIF> getChoiceItems() {
    return new ArrayList<ConsoleUIItemIF>(choiceItems);
  }
}

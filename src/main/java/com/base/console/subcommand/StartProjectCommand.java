package com.base.console.subcommand;

import com.base.console.subcommand.constant.SubCommandConstant;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.elements.PromptableElementIF;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jline.console.completer.StringsCompleter;
import lombok.Getter;
import org.fusesource.jansi.AnsiConsole;
import org.springframework.stereotype.Component;

@Component
@Parameters(
    commandNames = { SubCommandConstant.START_PROJECT_COMMAND },
    commandDescription = "프로젝트 클론 및 프로젝트에서 사용할 필요 기능만 추가하여 불필요기능을 제거한 프로젝트 생성 "
)
@Getter
public class StartProjectCommand {
  @Parameter(
      names = { "--interactive", "-i"},
      description = "상호 작용하며 실행합니다. 실행 방향에 관련한 질문을 선택하며 진행할 수 있습니다.",
      help = true
  )
  private boolean interactive;

  @Parameter(names = "--help", help = true, description = "도움말")
  private boolean help;

  public void run(){

  }

  public void interactive() throws IOException {
    AnsiConsole.systemInstall();
    ConsolePrompt prompt = new ConsolePrompt();
    PromptBuilder promptBuilder = prompt.getPromptBuilder();

    promptBuilder.createConfirmPromp()
        .name("wantapizza")
        .message("Do you want to order a pizza?")
        .defaultValue(ConfirmChoice.ConfirmationValue.YES)
        .addPrompt();

    promptBuilder.createInputPrompt()
        .name("name")
        .message("Please enter your name")
        .defaultValue("John Doe")
        .addCompleter(new StringsCompleter("Jim", "Jack", "John"))
        .addPrompt();

    promptBuilder.createListPrompt()
        .name("pizzatype")
        .message("Which pizza do you want?")
        .newItem().text("Margherita").add()  // without name (name defaults to text)
        .newItem("veneziana").text("Veneziana").add()
        .newItem("hawai").text("Hawai").add()
        .newItem("quattro").text("Quattro Stagioni").add()
        .addPrompt();

    promptBuilder.createCheckboxPrompt()
        .name("topping")
        .message("Please select additional toppings:")

        .newSeparator("standard toppings")
        .add()

        .newItem().name("cheese").text("Cheese").add()
        .newItem("bacon").text("Bacon").add()
        .newItem("onions").text("Onions").disabledText("Sorry. Out of stock.").add()

        .newSeparator().text("special toppings").add()

        .newItem("salami").text("Very hot salami").check().add()
        .newItem("salmon").text("Smoked Salmon").add()

        .newSeparator("and our speciality...").add()

        .newItem("special").text("Anchovies, and olives").checked(true).add()
        .addPrompt();

    promptBuilder.createChoicePrompt()
        .name("payment")
        .message("How do you want to pay?")

        .newItem().name("cash").message("Cash").key('c').asDefault().add()
        .newItem("visa").message("Visa Card").key('v').add()
        .newItem("master").message("Master Card").key('m').add()
        .newSeparator("online payment").add()
        .newItem("paypal").message("Paypal").key('p').add()
        .addPrompt();

    List<PromptableElementIF> promptableElementList = promptBuilder.build();

    // only for test. reset the default reader to a test reader to automate the input
    promptableElementList.get(0);

    HashMap<String, ? extends PromtResultItemIF> result = prompt.prompt(promptableElementList);

    for (Map.Entry<String, ? extends PromtResultItemIF> entry : result.entrySet()) {
      System.out.println("entry key : " + entry.getKey());
      System.out.println("entry value : " + ((PromtResultItemIF)entry.getValue()).toString());
    }
  }
}

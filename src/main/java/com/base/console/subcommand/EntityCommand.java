package com.base.console.subcommand;

import com.base.console.subcommand.constant.SubCommandConstant;
import com.base.console.util.FileUtil;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import lombok.Getter;
import org.fusesource.jansi.AnsiConsole;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
@Parameters(
    commandNames = { SubCommandConstant.ENTITY_COMMAND },
    commandDescription = "Entity 생성과 동시에 함께 생성할 기본적인 Dto, Predicate, Projection, Repository 파일 생성"
)
@Getter
public class EntityCommand {
  @Parameter(
      names = { "--interactive", "-i"},
      description = "상호 작용하며 실행합니다. 실행 방향에 관련한 질문을 선택하며 진행할 수 있습니다.",
      help = true
  )
  private boolean interactive;

  @Parameter(names = "--help", help = true, description = "도움말")
  private boolean help;

  private String projectPath = "";
  private String domainName = "";

  public void run(){

  }

  public void interactive() throws IOException, URISyntaxException {
    AnsiConsole.systemInstall();
    ConsolePrompt prompt = new ConsolePrompt();
    //0. 설정 파일 체크 프로젝트 위치
    File settingFile = Paths.get("base-cli.config").toFile();
    if(!settingFile.exists()){
       projectPath =  ((InputResult) (prompt.prompt(prompt.getPromptBuilder().createInputPrompt()
          .name("setProjectPath")
          .message("base-cli.config 파일이 존재하지 않습니다. 프로젝트 경로를 입력해주세요.")
          .defaultValue("/home/base/apps")
          .addPrompt().build())).get("setProjectPath")).getInput();
    }else{
      projectPath = FileUtil.readFileToString(Paths.get("base-cli.config"));
    }

    //1. 도메인생성할 도메인을 지정
    domainName = ((InputResult)(prompt.prompt(prompt.getPromptBuilder().createInputPrompt()
        .name("setDomain")
        .message("엔티티를 생성할 domain 패키지내 domain 명을 지정해주세요.(ex: board)")
        .defaultValue("board")
        .addPrompt().build())).get("setDomain")).getInput();

    //2. 도메인 체크

    if(!FileUtil.exist(Paths.get(projectPath,"src", "main", "java", "com", "slowalk", "base", "domain", domainName))){
      boolean confirmMakeDomainPath = ((ConfirmResult)(prompt.prompt(prompt.getPromptBuilder().createConfirmPromp()
          .name("confirmMakeDomainPath")
          .message("domain 경로가 없습니다. domain 경로를 만드시겠습니까?")
          .defaultValue(ConfirmChoice.ConfirmationValue.YES)
          .addPrompt().build())).get("confirmMakeDomainPath")).getConfirmed().equals(ConfirmChoice.ConfirmationValue.YES);
      if(confirmMakeDomainPath){
        // 도메인 생성
        System.out.println("도메인 생성");
      }else{
        System.out.println("도메인 생성하지 않음 종료");
        return;
      }
    }

    //3-1. 도메인 체크 후 있으면 Entity 생성 로직


    //3-2. 도메인 체크 후 없으면 도메인 생성하겠냐는 물음 후

    //3-2-1 도메인 생성에 동의하면 도메인 디렉토리 생성 및 하위 entity, dto, predicate, projection, repostory 패키지 생성

    //3-2-2 도메인 생성에 동의하지 않으면 종료

    //4. 생성할 엔티티 명
    HashMap<String, ? extends PromtResultItemIF> result2 = prompt.prompt(prompt.getPromptBuilder().createInputPrompt()
        .name("setEntity")
        .message("entity 명을 지정해주세요.(ex: board)")
        .defaultValue("board")
        .addPrompt().build());

    System.out.println("setEntity : " +  ((InputResult)result2.get("setEntity")).getInput());
    ;

    //5. 테이블명 지정

    //6. 필드명리스트 지정(콤마seperate)

    //7. 필드명 확인 후 yes or no

    //7-1 yes인경우 각 필드 리스트를 순차적으로 순회하여 구체적인 속성지정

    //7-2 no인경우 필드명 재지정

    //8 - 필드 리스트 순차적으로 순회하여 구체적인 속성 지정

    //9 Long, Boolean, Varchar, LongText, LocalDateTime, ManyToOne, Enumerated

    //9-1 Varchar인경우 Column length 입력 받음
    //9-1 Enumerated, ManyToOne 인 경우 클래스명 받음

    //10. 기본적인 생성자 및 create static 메소드 생성

  }
}

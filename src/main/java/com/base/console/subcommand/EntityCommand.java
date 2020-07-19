package com.base.console.subcommand;

import com.base.console.subcommand.constant.SubCommandConstant;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;
import org.springframework.stereotype.Component;

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

  public void run(){

  }

  public void interactive(){

  }
}

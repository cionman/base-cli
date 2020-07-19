package com.base.console.subcommand;

import com.base.console.subcommand.constant.SubCommandConstant;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Parameters(
    commandNames = {SubCommandConstant.DOMAIN_COMMAND},
    commandDescription = "도메인 패키지와 기본적으로 생성되는 하위 패키지 생성"
)
@Getter
public class DomainCommand {
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

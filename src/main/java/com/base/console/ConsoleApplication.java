package com.base.console;

import static com.base.console.subcommand.constant.SubCommandConstant.DOMAIN_COMMAND;
import static com.base.console.subcommand.constant.SubCommandConstant.ENTITY_COMMAND;
import static com.base.console.subcommand.constant.SubCommandConstant.START_PROJECT_COMMAND;

import com.base.console.subcommand.DomainCommand;
import com.base.console.subcommand.EntityCommand;
import com.base.console.subcommand.StartProjectCommand;
import com.beust.jcommander.JCommander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsoleApplication {

  @Autowired
  private StartProjectCommand startProjectCommand;

  @Autowired
  private DomainCommand domainCommand;

  @Autowired
  private EntityCommand entityCommand;

  @Autowired
  private JCommander jCommander;

  @Bean
  public JCommander jCommander(){
    ConsoleApplication console = new ConsoleApplication();
    return JCommander.newBuilder()
            .addCommand(startProjectCommand)
            .addCommand(domainCommand)
            .addCommand(entityCommand)
            .addObject(console).build();
  }

  @Bean
  public CommandLineRunner runner(){
    return (args -> {

      // 파라미터 파싱 바인딩
      jCommander.parse(args);

      String parsedCmdStr = jCommander.getParsedCommand();

      if(parsedCmdStr == null) return;

      switch (parsedCmdStr) {
        case START_PROJECT_COMMAND:
          if(startProjectCommand.isHelp()){
            getSubCommandHandle(START_PROJECT_COMMAND).usage();
            return;
          }
          if(startProjectCommand.isInteractive()){
            startProjectCommand.interactive();
            return;
          }
          startProjectCommand.run();
          break;
        case DOMAIN_COMMAND:
          if(domainCommand.isHelp()){
            getSubCommandHandle(DOMAIN_COMMAND).usage();
            return;
          }
          if(domainCommand.isInteractive()){
            domainCommand.interactive();
            return;
          }
          domainCommand.run();
          break;
        case ENTITY_COMMAND:
          if(entityCommand.isHelp()){
            getSubCommandHandle(ENTITY_COMMAND).usage();
            return;
          }
          if(entityCommand.isInteractive()){
            entityCommand.interactive();
            return;
          }
          entityCommand.run();
          break;
        /*default:
          System.err.println("Invalid command: " + parsedCmdStr);*/
      }
    });
  }

  private JCommander getSubCommandHandle(String command) {
    JCommander cmd = jCommander.getCommands().get(command);

    if (cmd == null) {
      System.err.println("Invalid command: " + command);
    }
    return cmd;
  }


  public static void main(String[] args) {
    SpringApplication.run(ConsoleApplication.class, args);
  }
}

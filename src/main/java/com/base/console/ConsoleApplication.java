package com.base.console;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.base.console.util.FreemarkerUtils;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@SpringBootApplication
public class ConsoleApplication {

  @Parameter(names={"--length", "-l"})
  int length;
  @Parameter(names={"--pattern", "-p"})
  int pattern;

  @Autowired
  private FreeMarkerConfigurer freeMarkerConfigurer;

  @Bean
  public CommandLineRunner runner(){
    return (args -> {
      ConsoleApplication console = new ConsoleApplication();
      JCommander.newBuilder()
          .addObject(console)
          .build()
          .parse(args);

      Map<String, Object> map = new HashMap<>();
      map.put("tablename", "TB_TEST");
      System.out.println("template : " + FreemarkerUtils.getTextByTemplate(freeMarkerConfigurer, "entity/entity.java.ftlh", map));
    });
  }


  public static void main(String[] args) {
    SpringApplication.run(ConsoleApplication.class, args);
  }
}

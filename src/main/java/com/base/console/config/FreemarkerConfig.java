package com.base.console.config;

import freemarker.template.TemplateModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
@RequiredArgsConstructor
public class FreemarkerConfig {

  private final FreeMarkerProperties properties;

  private void applyProperties(FreeMarkerConfigurationFactory factory) {
    factory.setTemplateLoaderPaths(this.properties.getTemplateLoaderPath());
    factory.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
    factory.setDefaultEncoding(this.properties.getCharsetName());
    Properties settings = new Properties();
    settings.putAll(this.properties.getSettings());
    factory.setFreemarkerSettings(settings);
  }

  @Bean
  public FreeMarkerConfigurer freeMarkerConfigurer() {
    FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
    applyProperties(configurer);

    return configurer;
  }
}

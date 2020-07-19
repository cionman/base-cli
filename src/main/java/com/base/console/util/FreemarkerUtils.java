package com.base.console.util;

import freemarker.core.ParseException;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Map;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

public class FreemarkerUtils {

  public static String getTextByTemplate(FreeMarkerConfigurer freeMarkerConfigurer, String template,
      Map<String, Object> model)
      throws IOException, ParseException, IOException, TemplateException {
    return FreeMarkerTemplateUtils
        .processTemplateIntoString(freeMarkerConfigurer.getConfiguration().getTemplate(template),
            model);
  }
}

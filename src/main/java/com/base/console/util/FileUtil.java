package com.base.console.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

  public static String readFileToString(Path path) throws IOException {
    return new String(Files.readAllBytes(path));
  }

  public static boolean exist(Path path){
    return path.toFile().exists();
  }
}

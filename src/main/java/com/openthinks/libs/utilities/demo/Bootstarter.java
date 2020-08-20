package com.openthinks.libs.utilities.demo;

import java.util.Iterator;
import java.util.ServiceLoader;
import com.openthinks.libs.utilities.logger.ProcessLogger2;
import com.openthinks.libs.utilities.logger.ProcessLogger2Factory;


public class Bootstarter {

  private static final ProcessLogger2 LOGGER = ProcessLogger2Factory.getRootLogger();

  public static void main(String[] args) {
    LOGGER.info("Going to run...");
    ServiceLoader<Demo> serviceLoader = ServiceLoader.load(Demo.class);
    Iterator<Demo> iterator = serviceLoader.iterator();
    while (iterator.hasNext()) {
      Demo demo = iterator.next();
      processDemo(demo, args);
    }
    LOGGER.info("Done.");
  }

  static void processDemo(Demo demo, String[] args) {
    try {
      demo.demo(args);
    } catch (Exception e) {
      LOGGER.error(e);
    }
  }
}

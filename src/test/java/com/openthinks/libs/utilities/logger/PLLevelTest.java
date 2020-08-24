/**
 * 
 */
package com.openthinks.libs.utilities.logger;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author dailey.dai@openthinks.com
 *
 */
public class PLLevelTest {

  @Test
  public void testBuild() {
    PLLevel actual = PLLevel.build("INFO");
    PLLevel expected = PLLevel.INFO;
    Assert.assertEquals(expected, actual);

    actual = PLLevel.build("debug");
    expected = PLLevel.DEBUG;
    Assert.assertEquals(expected, actual);
    
    actual = PLLevel.build("INFOXXX");
    expected = null;
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testLoglevelFromSysPops() {
    PLogger logger = Loggers.getLogger(getClass().getName());
    PLLevel actual = logger.currentLevel();
    PLLevel expected = Loggers.DEFAULT_LEVEL;
    Assert.assertEquals(expected, actual);
    
    System.setProperty("PLLevel", "error");
    logger.info("Test log level:{0}", logger.currentLevel());
    
    actual = logger.currentLevel();
    expected = PLLevel.ERROR;
    Assert.assertEquals(expected, actual);
    
    System.setProperty(PLLevel.class.getName(), "INFO");
    logger.info("Test log level:{0}", logger.currentLevel());
    
    actual = logger.currentLevel();
    expected = PLLevel.INFO;
    Assert.assertEquals(expected, actual);
  }
}

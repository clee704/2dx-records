package name.lemonedo.iidx.record.app;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import name.lemonedo.util.Utf8ResourceBundle;

/**
 * 
 * @author LEE Chungmin
 */
class Messages {

  private static final String BUNDLE_NAME = "name.lemonedo.iidx.app.messages";
  private static final ResourceBundle RESOURCE_BUNDLE = Utf8ResourceBundle
      .getBundle(BUNDLE_NAME);

  // prevent instantiation
  private Messages() {}

  static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }
}

package name.lemonedo.iidx.app;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import name.lemonedo.util.Utf8ResourceBundle;

class Messages {

    private static final String BUNDLE_NAME =
            "name.lemonedo.iidx.app.messages";

    private static final ResourceBundle RESOURCE_BUNDLE =
            Utf8ResourceBundle.getBundle(BUNDLE_NAME);

    private Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}

package name.lemonedo.iidx.app;

import name.lemonedo.util.UnaryFunction;
import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.exceptionhandler.BrowserLauncherErrorHandler;

/**
 * This class is for safe dynamic library loading. If the library is not found,
 * only the function that depends on that library will be disabled.
 * 
 * @author LEE Chungmin
 */
class BrowserLauncherWrapper extends BrowserLauncher {

  BrowserLauncherWrapper(final UnaryFunction<Void, Exception> m)
  throws Exception {
    super(null, new BrowserLauncherErrorHandler() {
      public void handleException(Exception e) {
        m.eval(e);
      }
    });
  }
}

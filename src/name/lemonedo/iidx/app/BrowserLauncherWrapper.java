package name.lemonedo.iidx.app;

import name.lemonedo.util.UnaryFunction;
import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.exceptionhandler.BrowserLauncherErrorHandler;

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

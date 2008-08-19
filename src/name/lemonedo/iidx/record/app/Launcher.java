package name.lemonedo.iidx.record.app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * 
 * @author LEE Chungmin
 */
class Launcher {

  static {
    try {
      UIManager.getInstalledLookAndFeels();
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // prevent instantiation
  private Launcher() {}

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new RecordExtractorGUI();
      }
    });
  }
}

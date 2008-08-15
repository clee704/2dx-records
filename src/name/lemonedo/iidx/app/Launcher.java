package name.lemonedo.iidx.app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

class Launcher {

    static {
        try {
            UIManager.getInstalledLookAndFeels();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Launcher() {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RecordExtracterGUI();
            }
        });
    }
}

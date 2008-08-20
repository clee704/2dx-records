package name.lemonedo.iidx.record.app;

import static javax.swing.BorderFactory.createTitledBorder;
import static javax.swing.JOptionPane.CANCEL_OPTION;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.SpringLayout.EAST;
import static javax.swing.SpringLayout.NORTH;
import static javax.swing.SpringLayout.SOUTH;
import static javax.swing.SpringLayout.WEST;
import static name.lemonedo.iidx.record.app.Messages.getString;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.filechooser.FileNameExtensionFilter;

import name.lemonedo.iidx.record.PlayMode;
import name.lemonedo.iidx.record.RecordReaderFactory;
import name.lemonedo.util.UnaryFunction;

/**
 * 
 * @author LEE Chungmin
 */
public class RecordExtractorGUI {

  private final JFrame frame;
  private final JTextField openField;
  private final JRadioButton separateButton;
  private final JCheckBox excludeNoClearBox;
  private final EnumMap<PlayMode, JCheckBox> playModeBoxes;
  private final JFileChooser open;
  private final JFileChooser save;

  private final RecordExtractor recordExtractor;
  private final Object browserLauncher;

  public RecordExtractorGUI() {
    frame = new JFrame(getString("TITLE"));
    JPanel upper = new JPanel();
    JPanel left = new JPanel();
    JPanel middle = new JPanel();
    JPanel right = new JPanel();
    upper.setBorder(createTitledBorder(getString("T_INPUT")));
    left.setBorder(createTitledBorder(getString("T_SORT_MODE")));
    middle.setBorder(createTitledBorder(getString("T_EXCLUDE")));
    right.setBorder(createTitledBorder(getString("T_PLAY_MODE")));
    JButton openButton = new JButton(getString("B_OPEN"));
    JButton infoButton = new JButton(getString("B_INFO"));
    JButton homepageButton = new JButton(getString("B_GO"));
    JButton checkButton = new JButton(getString("B_CHECK"));
    JButton extractButton = new JButton(getString("B_EXTRACT"));
    JButton exitButton = new JButton(getString("B_EXIT"));
    JLabel openLabel = new JLabel(getString("L_OPEN"));
    openField = new JTextField();
    openLabel.setLabelFor(openField);
    separateButton = new JRadioButton(getString("B_SEPARATE"));
    JRadioButton mixButton = new JRadioButton(getString("B_MIX"), true);
    ButtonGroup group = new ButtonGroup();
    group.add(separateButton);
    group.add(mixButton);
    left.setLayout(new FlowLayout(FlowLayout.LEADING, 3, 3));
    left.add(separateButton);
    left.add(mixButton);
    excludeNoClearBox = new JCheckBox("No Clear", false);
    middle.setLayout(new FlowLayout(FlowLayout.LEADING, 3, 3));
    middle.add(excludeNoClearBox);
    playModeBoxes = new EnumMap<PlayMode, JCheckBox>(PlayMode.class);
    playModeBoxes.put(PlayMode.SN, new JCheckBox("SN", true));
    playModeBoxes.put(PlayMode.SH, new JCheckBox("SH", true));
    playModeBoxes.put(PlayMode.SA, new JCheckBox("SA", true));
    playModeBoxes.put(PlayMode.DN, new JCheckBox("DN", true));
    playModeBoxes.put(PlayMode.DH, new JCheckBox("DH", true));
    playModeBoxes.put(PlayMode.DA, new JCheckBox("DA", true));
    right.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
    right.add(playModeBoxes.get(PlayMode.SN));
    right.add(playModeBoxes.get(PlayMode.SH));
    right.add(playModeBoxes.get(PlayMode.SA));
    right.add(playModeBoxes.get(PlayMode.DN));
    right.add(playModeBoxes.get(PlayMode.DH));
    right.add(playModeBoxes.get(PlayMode.DA));

    // upper panel layout
    {
      GroupLayout layout = new GroupLayout(upper);
      upper.setLayout(layout);
      ParallelGroup p = layout.createParallelGroup(Alignment.BASELINE);
      p.addComponent(openLabel);
      p.addComponent(openField);
      p.addComponent(openButton);
      SequentialGroup ver = layout.createSequentialGroup();
      ver.addGap(3).addGroup(p).addGap(3);
      SequentialGroup hor = layout.createSequentialGroup();
      hor.addGap(5).addComponent(openLabel);
      hor.addGap(15).addComponent(openField);
      hor.addGap(5).addComponent(openButton).addGap(5);
      layout.setHorizontalGroup(hor);
      layout.setVerticalGroup(ver);
    }

    // frame layout
    {
      SpringLayout layout = new SpringLayout();
      Container contentPane = frame.getContentPane();
      contentPane.setLayout(layout);
      contentPane.add(upper);
      contentPane.add(left);
      contentPane.add(middle);
      contentPane.add(right);
      contentPane.add(infoButton);
      contentPane.add(homepageButton);
      contentPane.add(checkButton);
      contentPane.add(extractButton);
      contentPane.add(exitButton);
      layout.putConstraint(EAST, contentPane, 3, EAST, right);
      layout.putConstraint(WEST, upper, 3, WEST, contentPane);
      layout.putConstraint(WEST, left, 3, WEST, contentPane);
      layout.putConstraint(WEST, infoButton, 3, WEST, upper);
      layout.putConstraint(WEST, homepageButton, 3, EAST, infoButton);
      layout.putConstraint(WEST, checkButton, 3, EAST, homepageButton);
      layout.putConstraint(WEST, middle, 3, EAST, left);
      layout.putConstraint(WEST, right, 3, EAST, middle);
      layout.putConstraint(EAST, upper, -3, EAST, contentPane);
      layout.putConstraint(EAST, exitButton, -3, EAST, upper);
      layout.putConstraint(EAST, extractButton, -3, WEST, exitButton);
      layout.putConstraint(NORTH, upper, 3, NORTH, contentPane);
      layout.putConstraint(NORTH, left, 3, SOUTH, upper);
      layout.putConstraint(NORTH, middle, 3, SOUTH, upper);
      layout.putConstraint(NORTH, right, 3, SOUTH, upper);
      layout.putConstraint(NORTH, infoButton, 3, SOUTH, right);
      layout.putConstraint(NORTH, homepageButton, 3, SOUTH, right);
      layout.putConstraint(NORTH, checkButton, 3, SOUTH, right);
      layout.putConstraint(NORTH, extractButton, 3, SOUTH, right);
      layout.putConstraint(NORTH, exitButton, 3, SOUTH, right);
      layout.putConstraint(SOUTH, contentPane, 3, SOUTH, infoButton);
    }

    // listeners
    infoButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showInfoMessage();
      }
    });
    homepageButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        openHomepage();
      }
    });
    checkButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        checkLatestVersion();
      }
    });
    openButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getPsuFile();
      }
    });
    extractButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        extract();
      }
    });
    exitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    // others
    open = new JFileChooser();
    open.setFileFilter(new FileNameExtensionFilter(getString("PSU_DESC"),
        "psu"));
    open.setAcceptAllFileFilterUsed(false);
    save = new JFileChooser();
    save.setFileFilter(new FileNameExtensionFilter(getString("HTML_DESC"),
        "html"));
    recordExtractor = new RecordExtractor();
    browserLauncher = createBrowserLauncher();

    // final configure & packing
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private void showInfoMessage() {
    showInfoDialog(getString("M_INFO"));
  }

  private void openHomepage() {
    if (browserLauncher == null)
      showErrorDialog(getString("E_OPEN_PAGE") + getString("SITE_URL"));
    else
      ((BrowserLauncherWrapper) browserLauncher).openURLinBrowser(getString(
          "SITE_URL"));
  }

  private void checkLatestVersion() {
    try {
      if (UpdateChecker.check(3000))
        showInfoDialog(getString("M_LATEST"));
      else
        showInfoDialog(getString("M_OLD"));
    } catch (Exception e) {
      showErrorDialog(getString("E_CHECK"));
    }
  }

  private void getPsuFile() {
    switch (open.showOpenDialog(frame)) {
    case JFileChooser.APPROVE_OPTION:
      openField.setText(open.getSelectedFile().getAbsolutePath());
      return;
    case JFileChooser.ERROR_OPTION:
      showErrorDialog(getString("E_OPEN_FILE"));
      return;
    }
  }

  private void extract() {
    File psuFile = new File(openField.getText());
    if (!RecordReaderFactory.isSupportedVersion(psuFile)) {
      showErrorDialog(getString("E_VERSION"));
      return;
    }

    switch (save.showSaveDialog(frame)) {
    case JFileChooser.CANCEL_OPTION:
      return;
    case JFileChooser.ERROR_OPTION:
      showErrorDialog(getString("E_SAVE_FILE"));
      return;
    }
    String path = save.getSelectedFile().getAbsolutePath();
    File documentFile = new File(ensureExtension(path, ".html"));
    if (documentFile.exists()) {
      int r = showConfirmDialog(frame, getString("M_OVERWRITE"),
          getString("T_OVERWRITE"), OK_CANCEL_OPTION);
      if (r == CANCEL_OPTION)
        return;
    }

    try {
      List<PlayMode> selected = new ArrayList<PlayMode>();
      for (PlayMode mode : PlayMode.values())
        if (playModeBoxes.get(mode).isSelected())
          selected.add(mode);
      recordExtractor.setExcludeNoClear(excludeNoClearBox.isSelected());
      recordExtractor.setSortSeparately(separateButton.isSelected());
      boolean r = recordExtractor.extract(psuFile, documentFile, selected); 
      if (!r)
        showErrorDialog(getString("E_NO_RECORDS"));
    } catch (Exception e) {
      e.printStackTrace();
      showErrorDialog(getString("E_FAILED"));
      return;
    }
  }

  /**
   * Returns an <code>BrowserLauncher</code>, or <code>null</code> if it fails
   * to load the class. This method is for safe dynamic library loading. If the
   * library is not found, only the function that depends on that library will
   * be disabled.
   * 
   * @return an <code>BrowserLauncher</code>, or <code>null</code> if it fails
   *        to load the class
   */
  private Object createBrowserLauncher() {
    try {
      return new BrowserLauncherWrapper(new UnaryFunction<Void, Exception>() {
        public Void eval(Exception e) {
          showErrorDialog(getString("E_OPEN_PAGE") + getString("SITE_URL"));
          return null;
        }
      });
    } catch (Exception e) {
      return null;
    } catch (NoClassDefFoundError e) {
      showErrorDialog(getString("E_NO_LIB"));
      return null;
    }
  }

  private String ensureExtension(String path, String extension) {
    if (!path.endsWith(extension))
      return path + extension;
    else
      return path;
  }

  private void showInfoDialog(String message) {
    showMessageDialog(frame, message, getString("T_INFO"), INFORMATION_MESSAGE);
  }

  private void showErrorDialog(String message) {
    showMessageDialog(frame, message, getString("T_ERROR"), ERROR_MESSAGE);
  }
}

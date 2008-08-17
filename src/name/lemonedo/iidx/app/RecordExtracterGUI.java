package name.lemonedo.iidx.app;

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
import static name.lemonedo.iidx.app.Messages.getString;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
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

import name.lemonedo.iidx.PlayMode;
import name.lemonedo.iidx.Record;
import name.lemonedo.iidx.RecordReader;
import name.lemonedo.iidx.RecordReaderFactory;
import name.lemonedo.iidx.Version;
import name.lemonedo.iidx.util.HtmlPrinter;
import name.lemonedo.iidx.util.NoRecordsToPrintException;
import name.lemonedo.util.UnaryMethod;

class RecordExtracterGUI {

  private final JFrame frame;
  private final JFileChooser opener;
  private final JFileChooser saver;
  private final JTextField openField;
  private final JRadioButton seperateButton;
  private final JRadioButton mixButton;
  private final JCheckBox noClearBox;
  private final JCheckBox snBox;
  private final JCheckBox shBox;
  private final JCheckBox saBox;
  private final JCheckBox dnBox;
  private final JCheckBox dhBox;
  private final JCheckBox daBox;

  private final HtmlPrinter printer;
  private final Object browserLauncher;

  RecordExtracterGUI() {
    frame = new JFrame(getString("TITLE"));
    opener = new JFileChooser();
    opener.setFileFilter(new FileNameExtensionFilter(getString("PSU_DESC"),
        "psu"));
    opener.setAcceptAllFileFilterUsed(false);
    saver = new JFileChooser();
    saver.setFileFilter(new FileNameExtensionFilter(getString("HTML_DESC"),
        "html"));
    String defaultPath = System.getProperty("user.dir") + "/records.html";
    saver.setSelectedFile(new File(defaultPath));
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
    seperateButton = new JRadioButton(getString("B_SEPERATE"));
    mixButton = new JRadioButton(getString("B_MIX"), true);
    ButtonGroup group = new ButtonGroup();
    group.add(seperateButton);
    group.add(mixButton);
    left.setLayout(new FlowLayout(FlowLayout.LEADING, 3, 3));
    left.add(seperateButton);
    left.add(mixButton);
    noClearBox = new JCheckBox("No Clear", false);
    middle.setLayout(new FlowLayout(FlowLayout.LEADING, 3, 3));
    middle.add(noClearBox);
    snBox = new JCheckBox("SN", true);
    shBox = new JCheckBox("SH", true);
    saBox = new JCheckBox("SA", true);
    dnBox = new JCheckBox("DN", true);
    dhBox = new JCheckBox("DH", true);
    daBox = new JCheckBox("DA", true);
    right.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
    right.add(snBox);
    right.add(shBox);
    right.add(saBox);
    right.add(dnBox);
    right.add(dhBox);
    right.add(daBox);

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

    printer = new HtmlPrinter();
    browserLauncher = createBrowserLauncher();
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
      if (UpdateChecker.check())
        showInfoDialog(getString("M_LATEST"));
      else
        showInfoDialog(getString("M_OLD"));
    } catch (Exception e) {
      showErrorDialog(getString("E_CHECK"));
    }
  }

  private void getPsuFile() {
    switch (opener.showOpenDialog(frame)) {
    case JFileChooser.APPROVE_OPTION:
      openField.setText(opener.getSelectedFile().getAbsolutePath());
      return;
    case JFileChooser.ERROR_OPTION:
      showErrorDialog(getString("E_OPEN_FILE"));
      return;
    }
  }

  private void extract() {
    File psuFile = new File(openField.getText());
    if (Version.getVersion(psuFile) == null) {
      showErrorDialog(getString("E_VERSION"));
      return;
    }
    switch (saver.showSaveDialog(frame)) {
    case JFileChooser.CANCEL_OPTION:
      return;
    case JFileChooser.ERROR_OPTION:
      showErrorDialog(getString("E_SAVE_FILE"));
      return;
    }
    File outputFile = saver.getSelectedFile();
    String path = outputFile.getAbsolutePath();
    outputFile = new File(ensureExtension(path, ".html"));
    if (outputFile.exists()) {
      int r = showConfirmDialog(frame, getString("M_OVERWRITE"),
          getString("T_OVERWRITE"), OK_CANCEL_OPTION);
      if (r == CANCEL_OPTION)
        return;
    }
    RecordReader reader = RecordReaderFactory.create(psuFile);
    EnumMap<PlayMode, List<Record>> records;
    records = new EnumMap<PlayMode, List<Record>>(PlayMode.class);
    for (PlayMode m : PlayMode.values())
      records.put(m, new ArrayList<Record>());
    try {
      if (snBox.isSelected())
        records.get(PlayMode.SN).addAll(reader.read(PlayMode.SN));
      if (shBox.isSelected())
        records.get(PlayMode.SH).addAll(reader.read(PlayMode.SH));
      if (saBox.isSelected())
        records.get(PlayMode.SA).addAll(reader.read(PlayMode.SA));
      if (dnBox.isSelected())
        records.get(PlayMode.DN).addAll(reader.read(PlayMode.DN));
      if (dhBox.isSelected())
        records.get(PlayMode.DH).addAll(reader.read(PlayMode.DH));
      if (daBox.isSelected())
        records.get(PlayMode.DA).addAll(reader.read(PlayMode.DA));
      if (seperateButton.isSelected()) {
        for (PlayMode m : PlayMode.values())
          sortRecords(records.get(m));
        records.get(PlayMode.SN).addAll(records.get(PlayMode.SH));
        records.get(PlayMode.SN).addAll(records.get(PlayMode.SA));
        records.get(PlayMode.DN).addAll(records.get(PlayMode.DH));
        records.get(PlayMode.DN).addAll(records.get(PlayMode.DA));
      }
      else {
        records.get(PlayMode.SN).addAll(records.get(PlayMode.SH));
        records.get(PlayMode.SN).addAll(records.get(PlayMode.SA));
        sortRecords(records.get(PlayMode.SN));
        records.get(PlayMode.DN).addAll(records.get(PlayMode.DH));
        records.get(PlayMode.DN).addAll(records.get(PlayMode.DA));
        sortRecords(records.get(PlayMode.DN));
      }
      PrintStream out = new PrintStream(outputFile, "utf-8");
      PrintStream stdout = System.out;
      System.setOut(out);
      printer.clear();
      printer.setExcludeNoClear(noClearBox.isSelected());
      printer.addRecords("Single", records.get(PlayMode.SN));
      printer.addRecords("Double", records.get(PlayMode.DN));
      printer.print(reader.getVersion());
      System.setOut(stdout);
      out.close();
    } catch (NoRecordsToPrintException e) {
      showErrorDialog(getString("E_NO_RECORDS"));
      return;
    } catch (Exception e) {
      e.printStackTrace();
      showErrorDialog(getString("E_FAILED"));
      return;
    }
  }

  private Object createBrowserLauncher() {
    try {
      return new BrowserLauncherWrapper(new UnaryMethod<Void, Exception>() {
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

  private static void sortRecords(List<Record> records) {
    Collections.sort(records, Record.TITLE_ORDER);
    Collections.sort(records, Record.DIFFICULTY_ORDER);
  }
}

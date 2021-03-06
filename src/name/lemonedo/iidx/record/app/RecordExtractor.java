package name.lemonedo.iidx.record.app;

import java.io.*;
import java.util.*;

import name.lemonedo.iidx.record.*;
import name.lemonedo.iidx.record.util.*;

/**
 * 
 * @author LEE Chungmin
 */
public class RecordExtractor {

  private final HtmlPrinter htmlPrinter;

  /**
   * Constructs a new <code>RecordExtractor</code>
   */
  public RecordExtractor() {
    htmlPrinter = new HtmlPrinter();
  }

  /**
   * Sets whether or not this sorts records separately according to the play
   * mode while printing. Default setting is <code>false</code>.
   * 
   * @param aFlag <code>true</code> if this should sorts records separately
   *             according to the play mode while printing
   */
  public void setSortSeparately(boolean aFlag) {
    htmlPrinter.setSortSeparately(aFlag);
  }

  /**
   * Sets whether or not this exclude records with the clear state <code>
   * NO_CLEAR</code> while printing. Default setting is <code>false</code>.
   * 
   * @param aFlag <code>true</code> if this should exclude records with the
   *             clear state <code>NO_CLEAR</code> while printing
   */
  public void setExcludeNoClear(boolean aFlag) {
    htmlPrinter.setExcludeNoClear(aFlag);
  }

  /**
   * Extracts records from <code>psuFile</code> and writes it as a document to
   * <code>documentFile</code>, selecting and sorting records according to the
   * settings and <code>modes</code>.
   * 
   * @param psuFile a PSU file which contains the records
   * @param documentFile a document file to be written
   * @param modes <code>PlayMode</code> for selecting records
   * @return <code>false</code> if there is no records to print
   * @throws IOException if an I/O error occurs
   */
  public boolean extract(File psuFile, File documentFile,
                         Iterable<PlayMode> modes) throws IOException {
    RecordReader recordReader = RecordReaderFactory.newRecordReader(psuFile);
    List<Record> singleRecords = new ArrayList<Record>();
    List<Record> doubleRecords = new ArrayList<Record>();
    for (PlayMode mode : modes)
      if (mode.isSingleMode())
        singleRecords.addAll(recordReader.read(mode));
      else
        doubleRecords.addAll(recordReader.read(mode));
    htmlPrinter.clear();
    htmlPrinter.addRecords("Single", singleRecords);
    htmlPrinter.addRecords("Double", doubleRecords);
    FileOutputStream out = new FileOutputStream(documentFile);
    try {
      return htmlPrinter.print(out, recordReader.getVersion());
    } finally {
      out.close();
    }
  }
}

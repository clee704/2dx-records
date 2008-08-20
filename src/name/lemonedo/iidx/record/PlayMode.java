package name.lemonedo.iidx.record;

/**
 * 
 * @author LEE Chungmin
 */
public enum PlayMode {

  SN {
    public boolean isAnother() { return false; }
    public boolean isDoubleMode() { return false; };
    public String getOldName() { return "L7"; }
  },
  SH {
    public boolean isAnother() { return false; }
    public boolean isDoubleMode() { return false; };
    public String getOldName() { return "7K"; }
  },
  SA {
    public boolean isAnother() { return true; }
    public boolean isDoubleMode() { return false; };
    public String getOldName() { return "A7"; }
  },
  DN {
    public boolean isAnother() { return false; }
    public boolean isDoubleMode() { return true; };
    public String getOldName() { return "L14"; }
  },
  DH {
    public boolean isAnother() { return false; }
    public boolean isDoubleMode() { return true; };
    public String getOldName() { return "14K"; }
  },
  DA {
    public boolean isAnother() { return true; }
    public boolean isDoubleMode() { return true; };
    public String getOldName() { return "A14"; }
  };

  public boolean isSingleMode() {
    return !isDoubleMode();
  }

  public abstract boolean isDoubleMode();
  public abstract boolean isAnother();

  /**
   * Returns a string reperesenting the old mode name. (e.g. 'L7')
   * 
   * @return a string reperesenting the old mode name
   */
  public abstract String getOldName();
}

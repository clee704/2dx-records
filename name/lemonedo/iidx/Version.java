package name.lemonedo.iidx;

import java.io.File;

public enum Version {

    DISTORTED, GOLD;

    public static Version getVersion(File saveFile) {
        long length = saveFile.length();
        if (length == 369664)
            return DISTORTED;
        else if (length == 387072)
            return GOLD;
        else
            return null;
    }

    public String toString() {
        switch (this) {
        case DISTORTED:
            return "beatmaniaIIDX 13 DistorteD";
        case GOLD:
            return "beatmaniaIIDX 14 GOLD";
        default:
            return null;
        }
    }
}

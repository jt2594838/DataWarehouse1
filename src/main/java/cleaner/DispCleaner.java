package cleaner;

import cons.Constants;
import cons.Status;

import java.io.IOException;

public class DispCleaner extends AbsractCleaner {
    public DispCleaner(String filePath, String cleanedFilePath, String dirtyFilePath ) {
        super(filePath, cleanedFilePath, dirtyFilePath, 4);
    }

    @Override
    boolean validateRecord(String record) {

        if(Utils.validatePositiveInt(fields[0]) == null ||
        Utils.validatePositiveInt(fields[1]) == null ||
        Utils.validatePositiveInt(fields[2]) == null)
            return false;

        Status status = searchStr(fields[3], Constants.dispTypes);
        if (status == Status.REPAIRED) {
            fields[3] = replacement;
            isRepaired = true;
        } else if (status == Status.ILLEGAL)
            return false;

        return true;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "dataset/disp.csv";
        String cleanedFilePath = "dataset/disp_clean.csv";
        String dirtyFilePath = "dataset/disp_dirty.csv";

        AbsractCleaner cleaner = new DispCleaner(filePath, cleanedFilePath, dirtyFilePath);
        cleaner.run();
        cleaner.close();
    }
}

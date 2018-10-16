package cleaner;

import cons.Constants;
import cons.Status;

import java.io.IOException;

public class TransCleaner extends AbsractCleaner {
    public TransCleaner(String filePath, String cleanedFilePath, String dirtyFilePath) {
        super(filePath, cleanedFilePath, dirtyFilePath, 10);
    }

    @Override
    boolean validateRecord(String record) {
        if (Utils.validatePositiveInt(fields[0]) == null ||
        Utils.validatePositiveInt(fields[1]) == null ||
        !Utils.validateDateSec(fields[2]) ||
        Utils.validatePositiveInt(fields[5]) == null ||
        !Utils.validateBankTo(fields[8]) ||
        Utils.validatePositiveInt(fields[9]) == null)
            return false;

        try {
            int balance = Integer.parseInt(fields[6]);
        } catch (Exception e) {
            return false;
        }

        Status status = searchStr(fields[3].trim(), Constants.transTypes);
        if (status == Status.REPAIRED) {
            fields[3] = replacement;
            isRepaired = true;
        } else if (status == Status.ILLEGAL)
            return false;

        status = searchStr(fields[4].trim(), Constants.operationTypes);
        if (status == Status.REPAIRED) {
            fields[4] = replacement;
            isRepaired = true;
        } else if (status == Status.ILLEGAL)
            return false;

        status = searchStr(fields[7].trim(), Constants.kSymbolTypes);
        if (status == Status.REPAIRED) {
            fields[7] = replacement;
            isRepaired = true;
        } else if (status == Status.ILLEGAL)
            return false;

        return true;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "dataset/trans.csv";
        String cleanedFilePath = "dataset/trans_clean.csv";
        String dirtyFilePath = "dataset/trans_dirty.csv";

        AbsractCleaner cleaner = new TransCleaner(filePath, cleanedFilePath, dirtyFilePath);
        cleaner.run();
        cleaner.close();
    }
}

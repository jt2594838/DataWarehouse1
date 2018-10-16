package cleaner;

import cons.Constants;
import cons.Status;

import java.io.IOException;

public class AccountCleaner extends AbsractCleaner {


    public AccountCleaner(String filePath, String cleanedFilePath, String dirtyFilePath) {
        super(filePath, cleanedFilePath, dirtyFilePath, 4);
    }

    boolean validateRecord(String record) {
        if (Utils.validatePositiveInt(fields[0]) == null ||
                Utils.validatePositiveInt(fields[1]) == null)
            return false;

        Status status = searchStr(fields[2], Constants.freqNames);
        if (status == Status.REPAIRED) {
            fields[2] = replacement;
            isRepaired = true;
        } else if (status == Status.ILLEGAL) {
            return false;
        }

        return Utils.validateDate(fields[3]);
    }

    public static void main(String[] args) throws IOException {
        String filePath = "dataset/account.csv";
        String cleanedFilePath = "dataset/account_clean.csv";
        String dirtyFilePath = "dataset/account_dirty.csv";

        AbsractCleaner cleaner = new AccountCleaner(filePath, cleanedFilePath, dirtyFilePath);
        cleaner.run();
        cleaner.close();
    }
}

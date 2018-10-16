package cleaner;

import cons.Constants;
import cons.Status;

import java.io.IOException;

public class CardCleaner extends AbsractCleaner {

    public CardCleaner(String filePath, String cleanedFilePath, String dirtyFilePath) {
        super(filePath, cleanedFilePath, dirtyFilePath,4);
    }

    @Override
    boolean validateRecord(String record) {
        isRepaired = false;
        if(!validateFieldNum(record))
            return false;

        if(Utils.validatePositiveInt(fields[0]) == null ||
        Utils.validatePositiveInt(fields[1]) == null)
            return false;

        Status status = searchStr(fields[2], Constants.cardTypes);
        if (status == Status.REPAIRED) {
            fields[2] = replacement;
            isRepaired = true;
        } else if (status == Status.ILLEGAL) {
            return false;
        }

        return Utils.validateDate(fields[3]);
    }

    public static void main(String[] args) throws IOException {
        String filePath = "dataset/card.csv";
        String cleanedFilePath = "dataset/card_clean.csv";
        String dirtyFilePath = "dataset/card_dirty.csv";

        AbsractCleaner cleaner = new CardCleaner(filePath, cleanedFilePath, dirtyFilePath);
        cleaner.run();
        cleaner.close();
    }
}

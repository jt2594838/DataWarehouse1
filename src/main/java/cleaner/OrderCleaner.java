package cleaner;

import cons.Constants;
import cons.Status;

import java.io.IOException;

import static cleaner.Utils.validateBankTo;

public class OrderCleaner extends AbsractCleaner {

    public OrderCleaner(String filePath, String cleanedFilePath, String dirtyFilePath) {
        super(filePath, cleanedFilePath, dirtyFilePath, 6);
    }

    @Override
    boolean validateRecord(String record) {
        if (Utils.validatePositiveInt(fields[0]) == null ||
        Utils.validatePositiveInt(fields[1]) == null ||
        !validateBankTo(fields[2]) ||
        Utils.validatePositiveInt(fields[3]) == null ||
        Utils.validatePositiveInt(fields[4]) == null) {
            return false;
        }

        Status status = searchStr(fields[5].trim(), Constants.kSymbolTypes);
        if (status == Status.REPAIRED) {
            fields[5] = replacement;
            isRepaired = true;
        } else if (status == Status.ILLEGAL)
            return false;

        return true;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "dataset/order.csv";
        String cleanedFilePath = "dataset/order_clean.csv";
        String dirtyFilePath = "dataset/order_dirty.csv";

        AbsractCleaner cleaner = new OrderCleaner(filePath, cleanedFilePath, dirtyFilePath);
        cleaner.run();
        cleaner.close();
    }
}

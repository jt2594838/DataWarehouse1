package cleaner;

import cons.Constants;
import cons.Status;

import java.io.IOException;

public class LoanCleaner extends AbsractCleaner {
    public LoanCleaner(String filePath, String cleanedFilePath, String dirtyFilePath) {
        super(filePath, cleanedFilePath, dirtyFilePath, 8);
    }

    @Override
    boolean validateRecord(String record) {
        if (Utils.validatePositiveInt(fields[0]) == null ||
                Utils.validatePositiveInt(fields[1]) == null ||
                !Utils.validateDate(fields[2]) ||
                Utils.validatePositiveInt(fields[7]) == null
        )
            return false;
        try {
            boolean localRepaired = false;
            Integer amount = Integer.parseInt(fields[3]);
            Integer duration = Integer.parseInt(fields[4]);
            Integer payment = Integer.parseInt(fields[5]);
            if (amount < 0) {
                amount = -amount;
                localRepaired = true;
            }
            if (duration < 0) {
                duration = -duration;
                localRepaired = true;
            }
            if (payment < 0) {
                payment = -payment;
                localRepaired = true;
            }

            if(duration % 12 != 0) {
                if (amount % payment == 0) {
                    duration = amount / payment;
                    localRepaired = true;
                } else {
                    return false;
                }
            }

            if (duration * payment != amount) {
                if (amount % duration == 0)
                    return false;
                int diff = duration * payment - amount;
                int threshold = 10;
                if (-threshold <= diff && diff <= threshold) {
                    amount = duration * payment;
                    localRepaired = true;
                } else {
                    return false;
                }
            }

             if(localRepaired) {
                 fields[3] = String.valueOf(amount);
                 fields[4] = String.valueOf(duration);
                 fields[5] = String.valueOf(payment);
                 isRepaired = true;
             }
        } catch (Exception e) {
            return false;
        }

        Status status = searchStr(fields[6], Constants.loanTypes);
        if (status == Status.REPAIRED) {
            fields[6] = replacement;
            isRepaired = true;
        } else if (status == Status.ILLEGAL)
            return false;

        return true;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "dataset/loan.csv";
        String cleanedFilePath = "dataset/loan_clean.csv";
        String dirtyFilePath = "dataset/loan_dirty.csv";

        AbsractCleaner cleaner = new LoanCleaner(filePath, cleanedFilePath, dirtyFilePath);
        cleaner.run();
        cleaner.close();
    }
}

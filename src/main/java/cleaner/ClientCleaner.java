package cleaner;

import cons.Status;

import java.io.IOException;

public class ClientCleaner extends AbsractCleaner {

    public ClientCleaner(String filePath, String cleanedFilePath, String dirtyFilePath) {
        super(filePath, cleanedFilePath, dirtyFilePath, 3);
    }

    @Override
    boolean validateRecord(String record) {
        isRepaired = false;
        if (!validateFieldNum(record))
            return false;

        return Utils.validatePositiveInt(fields[0]) != null &&
                validateBirth(fields[1]) &&
                Utils.validatePositiveInt(fields[2]) != null;
    }

    private boolean validateBirth(String birthStr) {
        try {
            if (birthStr.length() != 6)
                return false;
            int year = Integer.parseInt(birthStr.substring(0, 2));
            int month = Integer.parseInt(birthStr.substring(2, 4));
            int day = Integer.parseInt(birthStr.substring(4, 6));

            if (year < 0)
                return false;
            if (!((1 <= month && month <= 12) || (51 <= month && month <= 62)))
                return false;
            if (month > 12)
                month -= 50;

            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if (!(1 <= day && day <= 31))
                        return false;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if (!(1 <= day && day <= 30))
                        return false;
                    break;
                case 2:
                    if (year % 4 == 0 && year != 0) {
                        if (!(1 <= day && day <= 29))
                            return false;
                    } else {
                        if (!(1 <= day && day <= 28))
                            return false;
                    }
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "dataset/client.csv";
        String cleanedFilePath = "dataset/client_clean.csv";
        String dirtyFilePath = "dataset/client_dirty.csv";

        AbsractCleaner cleaner = new ClientCleaner(filePath, cleanedFilePath, dirtyFilePath);
        cleaner.run();
        cleaner.close();
    }
}

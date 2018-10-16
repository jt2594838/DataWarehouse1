package cleaner;

import java.io.IOException;

public class DistrictCleaner extends AbsractCleaner {
    public DistrictCleaner(String filePath, String cleanedFilePath, String dirtyFilePath) {
        super(filePath, cleanedFilePath, dirtyFilePath, 8);
    }

    @Override
    boolean validateRecord(String record) {
        if (Utils.validatePositiveInt(fields[0]) == null ||
                Utils.validatePositiveInt(fields[3]) == null ||
                Utils.validatePositiveInt(fields[4]) == null ||
                Utils.validatePositiveInt(fields[5]) == null ||
                Utils.validatePercentage(fields[6]) == null ||
                Utils.validatePositiveInt(fields[7]) == null)
            return false;

        return true;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "dataset/district.csv";
        String cleanedFilePath = "dataset/district_clean.csv";
        String dirtyFilePath = "dataset/district_dirty.csv";

        AbsractCleaner cleaner = new DistrictCleaner(filePath, cleanedFilePath, dirtyFilePath);
        cleaner.run();
        cleaner.close();
    }
}

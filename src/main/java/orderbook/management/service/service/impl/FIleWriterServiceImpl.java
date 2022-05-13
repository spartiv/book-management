package orderbook.management.service.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import orderbook.management.service.service.FIleWriterService;

public class FIleWriterServiceImpl implements FIleWriterService {
    @Override
    public void writeDataToFile(String inputData, String filePathName) {
        File file = new File("output.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(inputData);
        } catch (IOException e) {
            throw new RuntimeException("Sorry there's no such file" + e);
        }
    }
}


package orderbook.management.service.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import orderbook.management.service.service.FileReader;

public class FileReaderImpl implements FileReader {
    @Override
    public String readFromFile(String filePath) {
        File file = new File(filePath);
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
            try {
                String values = reader.readLine();
                while (values != null) {
                    builder.append(values).append(System.lineSeparator());
                    values = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Can't open the file " + e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read the data from file" + e);
        }
        return builder.toString();
    }
}

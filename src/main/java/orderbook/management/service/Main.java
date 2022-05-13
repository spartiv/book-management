package orderbook.management.service;

import orderbook.management.service.service.FileReader;
import orderbook.management.service.service.TransactionDataParser;
import orderbook.management.service.service.impl.FileReaderImpl;

public class Main {
    public static void main(String[] args) {
        FileReader reader = new FileReaderImpl();
        String inputFromFile = reader.readFromFile("input.txt");
        TransactionDataParser parserObj = new TransactionDataParser();
        parserObj.parser(inputFromFile);
    }
}

package orderbook.management.service.service;

import java.util.TreeMap;
import orderbook.management.service.service.impl.FIleWriterServiceImpl;

public class TransactionDataParser {
    private static final String COMMA_SEPARATOR = ",";
    private static final int PRICE_INDEX = 1;
    private static final int SIZE_INDEX = 2;
    private static final int ACTION_INDEX = 3;
    private static final String FILE_PATH = "output.txt";
    private TreeMap<Integer, Integer> allBids = new TreeMap<>();
    private TreeMap<Integer, Integer> allAsks = new TreeMap<>();
    private FIleWriterService writerService = new FIleWriterServiceImpl();

    public void parser(String inputData) {
        String[] lines = inputData.split(System.lineSeparator());
        for (int i = 0; i < lines.length; i++) {
            String typeOfOperation = lines[i].split(COMMA_SEPARATOR)[0];
            switch (typeOfOperation) {
                case "u":
                    update(lines[i]);
                    break;
                case "q":
                    query(lines[i]);
                    break;
                default:
                    order(lines[i]);
            }
        }
    }

    public void update(String input) {
        String[] lines = input.split(COMMA_SEPARATOR);
        int price = Integer.parseInt(lines[PRICE_INDEX]);
        int size = Integer.parseInt(lines[SIZE_INDEX]);
        String action = lines[ACTION_INDEX];
        if (action.equals("bid")) {
            allBids.put(price, size);
            return;
        }
        allAsks.put(price, size);
    }

    public void query(String input) {
        String[] lines = input.split(COMMA_SEPARATOR);
        StringBuilder builder = new StringBuilder();
        if (lines[lines.length - 1].equals("best_bid")) {
            builder.append(allBids.lastEntry().getKey())
                    .append(COMMA_SEPARATOR)
                    .append(allBids.lastEntry().getValue())
                    .append(System.lineSeparator());
        } else if (lines[lines.length - 1].equals("best_ask")) {
            builder.append(allAsks.firstEntry().getKey())
                    .append(COMMA_SEPARATOR)
                    .append(allAsks.firstEntry().getValue())
                    .append(System.lineSeparator());
        } else {
            int temp = Integer.parseInt(lines[lines.length - 1]);
            builder.append(allBids.get(temp) != null ? allBids.get(temp)
                            : allAsks.get(temp) == null ? 0 : allAsks.get(temp))
                    .append(System.lineSeparator());
        }
        writerService.writeDataToFile(builder.toString(), FILE_PATH);
    }

    public void order(String input) {
        String[] lines = input.split(COMMA_SEPARATOR);
        int reductionValue = Integer.parseInt(lines[2]);
        if (lines[1].equals("sell")) {
            allBids.put(allBids.lastEntry().getKey(),
                    allBids.lastEntry().getValue() - reductionValue);
            return;
        }
        allBids.put(allBids.firstEntry().getKey(),
                allBids.firstEntry().getValue() - reductionValue);
    }
}

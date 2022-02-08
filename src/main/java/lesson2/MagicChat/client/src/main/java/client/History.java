package client;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class History {

    private static FileOutputStream out;

    public static void writeHistoryClient(String nameClient, String msg) {
        File file = new File(String.format("client/src/main/java/client/history/%s.txt", nameClient));
        if (!file.exists()) {
            try {
                file.createNewFile();
                out = new FileOutputStream(file, true);
                out.write(msg.getBytes(StandardCharsets.UTF_8));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                out = new FileOutputStream(file, true);
                out.write(msg.getBytes(StandardCharsets.UTF_8));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getOneHundredLinesHistory(String nameClient) throws IOException {
        File file = new File(String.format("client/src/main/java/client/history/%s.txt", nameClient));
        StringBuilder s = new StringBuilder();
        if (file.exists()) {
            List<String> hundredLines = new ArrayList<>(Files.readAllLines(Paths.get((String.format("client/src/main/java/client/history/%s.txt", nameClient)))));
            if (hundredLines.size() > 100) {
                int count = hundredLines.size();
                for (int i = count - 100; i < count; i++) {
                    s.append(hundredLines.get(i)).append("\n");
                }
            } else {
                for (String msg : hundredLines) {
                    s.append(msg).append("\n");
                }
            }
            return s.toString();
        }
        return s.toString();
    }

    public static void closeStreamOutHistory() {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
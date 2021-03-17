package queues.tasks;

import java.io.*;
import java.util.ArrayList;

public class ReadFromFile implements MyTask {
    File file = new File(System.getProperty("user.dir") + "\\testFile.txt");

    public boolean execute() {
        ArrayList<Integer> numbers = new ArrayList<>();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < line.length(); i++) {
                if (Character.isDigit(line.charAt(i))) numbers.add(Integer.parseInt(line.charAt(i) + ""));
            }
        }
        System.out.println(numbers.toString());
        return true;
    }

    @Override
    public boolean doWhenFail() {
        file = new File(System.getProperty("user.dir") + "\\testFile.txt");
        return execute();
    }
}

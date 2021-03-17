package queues.tasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile implements MyTask {
    File file = new File(System.getProperty("user.dir") + "\\testFile2.txt");

    @Override
    public boolean execute() {

        if (!file.exists()) {
            return false;
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(Math.random() * 100 + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean doWhenFail() {
        file = new File(System.getProperty("user.dir") + "\\testFile.txt");
        return execute();
    }
}

package ab.eclipse.system.altmanager;

import lombok.Getter;



import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AltManager {
    @Getter
    private static final List<User> account = new ArrayList<>();

    public static void clear() {
        account.clear();
    }

    public static void add(User acc) {
        if (contains(acc.name)) return;
        account.add(acc);
    }

    public static void remove(User acc) {
        User toRemove = null;
        for (User user : account) {
            if (acc.name.equals(user.name)) {
                toRemove = user;
                break;
            }
        }
        if (toRemove == null) return;
        account.remove(toRemove);
    }

    public static boolean contains(String string) {
        for (User user : account) {
            if (user.name.equals(string))
                return true;
        }
        return false;
    }

    public static void load() {
        File file = new File("\\ab.eclipse\\altmanager.txt");

        if (!file.exists()) {
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                add(new User(line));
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save() {
        File file = new File("\\ab.eclipse\\altmanager.txt");

        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (User user : getAccount()) {
                bufferedWriter.write(user.name + "\n");
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

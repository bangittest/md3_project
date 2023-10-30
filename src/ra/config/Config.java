package ra.config;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config<T> {
    public static Scanner scanner(){
        return new Scanner(System.in);
    }


    //config ghi đọc file
    public static final String URL_USERS = "src/ra/data/user.txt";
    public static final String URL_CATALOGS = "src/ra/data/catalog.txt";
    public static final String URL_PRODUCTS = "src/ra/data/product.txt";

    public void writeFile(String PATH_FILE, T t) {
        File file = new File(PATH_FILE);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Lỗi.");
        } catch (IOException e) {
            System.out.println("Lỗi.");
        }
    }

    public T readFile(String PATH_FILE) {
        File file = new File(PATH_FILE);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        T t = null;

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            t = (T) ois.readObject();
            if (fis != null) {
                fis.close();
            }
            if (ois != null) {
                ois.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file");
        } catch (IOException e) {
            System.out.println();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return t;
    }
}

package cn.DEDZTBH.SFhit.util;

import java.io.*;

/**
 * Created by TonyLiu on 2016/1/25.
 */
public class PrefManager {

    private static int bookNum = -1;
    private static int updateIntv = -1;
    private static File f = new File("pref.txt");

    private PrefManager() {
    }

    public static void writePref(int bookNum, int updateInterval) {
        try {
            FileWriter fw = new FileWriter(f);
            String bookNumInfo = bookNum == -1 ? "" : String.valueOf(bookNum);
            String updateItvInfo = updateInterval == -1 ? "" : "," + String.valueOf(updateInterval);
            //System.out.println("write pref: " + bookNumInfo + updateItvInfo);
            fw.write(bookNumInfo + updateItvInfo);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void readPref() {
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String prefRecord = "";
        try {
            BufferedReader fr = new BufferedReader(new FileReader(f));
            prefRecord = fr.readLine();
//            System.out.println("read pref:"+prefRecord);
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cutContent(prefRecord);
    }

    private static void cutContent(String content) {
        if (content != null && !content.equals("")) {
            int comma = content.indexOf(",");
            if (comma != -1) {
                if (comma == 0) {
                    updateIntv = Integer.parseInt(content.substring(1));
                } else {
                    bookNum = Integer.parseInt(content.substring(0, comma));
                    updateIntv = Integer.parseInt(content.substring(comma + 1));
                }
            } else {
                bookNum = Integer.parseInt(content);
            }
        }
    }


    public static int getBookNum() {
        return bookNum;
    }

    public static int getUpdateIntv() {
        return updateIntv;
    }
}

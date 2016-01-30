package cn.DEDZTBH.SFhit.util;

import java.io.*;

/**
 * Created by TonyLiu on 2016/1/25.
 */
public class PrefManager {

    private File f = new File("pref.txt");

    public void writePref(int bookNum){
        initFile();
        f.delete();
        initFile();
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(String.valueOf(bookNum));
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int readPref(){
        String prefRecord = "";
        initFile();
        try {
            BufferedReader fr = new BufferedReader(new FileReader(f));
            prefRecord = fr.readLine();
            System.out.println("read pref:"+prefRecord);
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prefRecord==null?-1:Integer.parseInt(prefRecord);
    }

    public void initFile(){
        try {
            f.createNewFile();
            f.mkdirs();
            System.out.println("pref file init");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

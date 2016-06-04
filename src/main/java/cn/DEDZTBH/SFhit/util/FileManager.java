package cn.DEDZTBH.SFhit.util;

import java.io.*;

/**
 * Created by peiqi on 2015/12/29.
 */
public class FileManager {

    static private File recordFile;
    static private String record = "";
    static private int hit;
    static private int booked;
    static private int like;

    private FileManager() {
    }

    public static void ReadFile(int BookNum) {
        String fileName = BookNum + ".txt";
        recordFile = new File(fileName);
        record = "";
        hit = booked = like = 0;
        try {
            if (!recordFile.exists()) {
                recordFile.createNewFile();
            } else {
                ReadBookRecorded();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ReadBookRecorded() throws IOException {
        BufferedReader BR = new BufferedReader(new FileReader(recordFile));
        String line;
        StringBuilder build = new StringBuilder();
        while ((line = BR.readLine()) != null) {
            build.append(line).append("\n");
        }
        String Info = build.toString();
        int Div = Info.indexOf(",");
        if (Div < 0) {
            return;
        }
        String[] datas = new String[4];
        int div = -1;
        String divided = Info;
        for (int i = 0; i < datas.length; i++) {
            divided = divided.substring(div + 1);
            div = divided.indexOf(",");
            if (div < 0) {
                datas[i] = divided;
            } else {
                datas[i] = divided.substring(0, div);
            }
        }

        hit = Integer.parseInt(datas[0]);
        booked = Integer.parseInt(datas[1]);
        like = Integer.parseInt(datas[2]);
        record = datas[3];

    }

    public static String getRecord() {
        return record;
    }

    public static int getHit() {
        return hit;
    }

    public static void WriteFile(int BookNum, int Hit, int Booked, int Like, String Info) throws IOException {
        ReadFile(BookNum);
        String OrgRecord = record;

        if (Info != null) {
            FileWriter BW = new FileWriter(recordFile);
            BW.write(Hit + "," + Booked + "," + Like + "," + Info + "\n" + OrgRecord);
            BW.flush();
            BW.close();
        }

    }

    public static int getBooked() {
        return booked;
    }

    public static int getLike() {
        return like;
    }

}
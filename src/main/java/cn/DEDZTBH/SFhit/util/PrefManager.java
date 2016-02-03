package cn.DEDZTBH.SFhit.util;

import java.io.*;

/**
 * Created by TonyLiu on 2016/1/25.
 */
public class PrefManager {

    private int bookNum = -1;
    private int updateIntv = -1;
    private File f = new File("pref.txt");

    public void writePref(int bookNum, int updateInterval){
        initFile();
        f.delete();
        initFile();
        try {
            FileWriter fw = new FileWriter(f);
            String bookNumInfo = bookNum==-1?"":String.valueOf(bookNum);
            String updateItvInfo = updateInterval==-1?"": "," + String.valueOf(updateInterval);
            System.out.println("write pref: "+bookNumInfo+updateItvInfo);
            fw.write(bookNumInfo+updateItvInfo);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void readPref(){
        String prefRecord = "";
        initFile();
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

    public void initFile(){
        try {
            f.createNewFile();
            f.mkdirs();
//            System.out.println("pref file init");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cutContent(String content){
        if (content!=null&&!content.equals("")){
        int comma = content.indexOf(",");
        if (comma != -1) {
            if(comma == 0){
            // no bookNum, have itv
//                System.out.println("no bookNum, have itv");
                updateIntv = Integer.parseInt(content.substring(1));
            }else{
                //have both
//                System.out.println("have both");
            bookNum = Integer.parseInt(content.substring(0, comma));
            updateIntv = Integer.parseInt(content.substring(comma + 1));
            }
        }else {
            // have bookNum, no itv
//            System.out.println("have bookNum, no itv");
            bookNum = Integer.parseInt(content);
        }
//        System.out.println(bookNum+" "+updateIntv);
    }}


    public int getBookNum(){return bookNum;}
    public int getUpdateIntv(){return updateIntv;}
}

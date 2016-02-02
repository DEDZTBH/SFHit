package cn.DEDZTBH.SFhit.util;
import java.io.*;

/**
 * Created by peiqi on 2015/12/29.
 */
public class FileManager {

    private File recordFile;
    private String record = "";
    private String FileName;
    private int hit;
    private int booked;
    private int like;


    public void ReadFile(int BookNum){
            FileName = BookNum + ".txt";
            recordFile = new File(FileName);
            try{
                if (recordFile.exists()==false) {
//                    System.out.println("file dne");
                    initFile();
                }else{
//                    System.out.println("file exist, read record");
                ReadBookRecorded();
                }

            }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void ReadBookRecorded() throws IOException {
        BufferedReader BR = new BufferedReader(new FileReader(recordFile));
        String line = "";
        StringBuilder build = new StringBuilder();
        while ((line = BR.readLine())!=null){
            build.append(line+"\n");
        }
        String Info = build.toString();
        int Div = Info.indexOf(",");
        if (Div < 0){return;}
        String[] datas = new String[4];
        int div = -1;
        String divided = Info;
        for (int i = 0; i < datas.length; i++){
            divided = divided.substring(div+1);
//            System.out.println("divided="+divided);
            div = divided.indexOf(",");
//            System.out.println("newDiv="+div);
            if(div<0){
                datas[i]=divided;
            }else {
                datas[i]=divided.substring(0,div);
            }
//            System.out.println("i th ="+datas[i]);
        }

        hit = intlz(datas[0]);
        System.out.println(datas[0]);
        booked = intlz(datas[1]);
        like = intlz(datas[2]);
        record = datas[3];

    }

    public String getRecord(){
        return record;
    }
    public int getHit(){
        return hit;
    }

    public void WriteFile(int BookNum, int Hit, int Booked, int Like, String Info) throws IOException {
        ReadFile(BookNum);
        String OrgRecord = getRecord();

        if (Info!=null){
            recordFile.delete();
//            System.out.println("file deleted");
            initFile();
            FileWriter BW = new FileWriter(recordFile);
        BW.write(Hit+","+Booked+","+Like+","+Info+"\n"+OrgRecord);
//            System.out.println("write record");
            BW.flush();
            BW.close();
        }

    }


    private void initFile(){
        try {
            recordFile.createNewFile();
            recordFile.mkdirs();
//            System.out.println("record file init");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int intlz(String s){return Integer.parseInt(s);}

    public int getBooked(){return booked;}
    public int getLike(){return like;}

}
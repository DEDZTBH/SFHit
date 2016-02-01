package cn.DEDZTBH.SFhit.util;
import java.io.*;
import java.util.Timer;

/**
 * Created by peiqi on 2015/12/29.
 */
public class FileManager {

    public File recordFile;
    public String Record = "";
    public String FileName;
    public int Hit;


    public void ReadFile(int BookNum){
            FileName = BookNum + ".txt";
            recordFile = new File(FileName);
            try{
                if (recordFile.exists()==false) {
                    System.out.println("file dne");
                    initFile();
                }else{
                    System.out.println("file exist, read record");
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
        int div = Info.indexOf(",");
        if (div < 0){return;}
        Hit = Integer.parseInt(Info.substring(0,div));
        System.out.println("read hit = "+Hit);
        Record = Info.substring(div+1);
        System.out.println("read record = "+Record);
    }

    public String getRecord(){
        return Record;
    }
    public int getHit(){
        return Hit;
    }

    public void WriteFile(int BookNum, int Hit, String Info) throws IOException {
        ReadFile(BookNum);
        String OrgRecord = getRecord();

        if (Info!=null){
            recordFile.delete();
            System.out.println("file deleted");
            initFile();
            FileWriter BW = new FileWriter(recordFile);
        BW.write(Hit+","+Info+"\n"+OrgRecord);
            System.out.println("write record");
            BW.flush();
            BW.close();
        }

    }


    private void initFile(){
        try {
            recordFile.createNewFile();
            recordFile.mkdirs();
            System.out.println("record file init");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
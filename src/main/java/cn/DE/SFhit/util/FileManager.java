package cn.DE.SFhit.util;
import java.io.*;

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
                if (recordFile.exists()==false)
                {initFile();}else{
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
        Record = Info.substring(div+1);
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
        recordFile.delete();
        initFile();

        FileWriter BW = new FileWriter(recordFile);
        BW.write(Hit+","+Info+"\n"+OrgRecord);
        BW.flush();
        BW.close();
    }


    private void initFile(){
        try {
            recordFile.createNewFile();
            recordFile.mkdirs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
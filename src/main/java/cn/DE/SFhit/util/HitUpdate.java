package cn.DE.SFhit.util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by peiqi on 2015/12/29.
 */
public class HitUpdate {
    FileManager fm = new FileManager();
    public int increase;

    public String Update(int NewHit, int BookNum) {
        fm.ReadFile(BookNum);
        int OriginalHit = fm.getHit();
        System.out.println("org hit = " + OriginalHit);
        String UpdateInfo;
        increase = NewHit - OriginalHit;
        if (increase != 0) {
            UpdateInfo = "[" + getCurrentTime() + "] 点击+" + increase;
        } else {
            UpdateInfo = null;
        }
        return UpdateInfo;
    }

    public String getCurrentTime(){
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);
        return time;
    }
}

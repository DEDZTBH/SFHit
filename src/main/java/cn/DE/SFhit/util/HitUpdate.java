package cn.DE.SFhit.util;


import com.oracle.deploy.update.UpdateInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by peiqi on 2015/12/29.
 */
public class HitUpdate {
    public int increase;
    public int OriginalHit = 0;
    public String Update(int NewHit){
        String UpdateInfo;
        increase = NewHit - OriginalHit;
        if (increase != 0){
        UpdateInfo = "["+getCurrentTime()+"] 点击+"+increase;
        }else {UpdateInfo = null;}
        return UpdateInfo;
    }

    public String getCurrentTime(){
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);
        return time;
    }
}

package cn.DE.SFhit.util;


import com.oracle.deploy.update.UpdateInfo;

/**
 * Created by peiqi on 2015/12/29.
 */
public class HitUpdate {
    public int increase;
    public int OriginalHit;
    public String Update(int NewHit){
        String UpdateInfo;
        increase = NewHit - OriginalHit;
        if (increase != 0){
        UpdateInfo = "点击+"+increase;
        }else {UpdateInfo = null;}
        return UpdateInfo;
    }
}

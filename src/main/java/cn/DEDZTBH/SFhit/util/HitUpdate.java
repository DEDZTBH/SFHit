package cn.DEDZTBH.SFhit.util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by peiqi on 2015/12/29.
 */
public class HitUpdate {
    FileManager fm = new FileManager();

    public String Update(int NewHit, int NewBooked, int NewLike, int BookNum) {
        fm.ReadFile(BookNum);
        int origHit = fm.getHit();
        int origBooked = fm.getBooked();
        int origLike = fm.getLike();
//        System.out.println("org hit = " + origHit);
        String UpdateInfo = "[" + getCurrentTime() + "]";
        int changeHit = NewHit - origHit;
        int changeBooked = NewBooked - origBooked;
        int changeLike = NewLike - origLike;
        StringBuilder build = new StringBuilder();
        build.append(UpdateInfo);
        if (changeHit != 0) {
            build.append(" 点击" + (changeHit>0?"+":"") + changeHit);
        }
        if (changeBooked != 0) {
            build.append(" 收藏" + (changeBooked>0?"+":"") + changeBooked);
        }
        if (changeLike != 0) {
            build.append(" 喜欢" + (changeLike>0?"+":"") + changeLike);
        }
//        System.out.println(build.toString());
        return build.toString().equals(UpdateInfo)?null:build.toString();
    }

    public String getCurrentTime(){
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);
        return time;
    }
}

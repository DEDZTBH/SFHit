package cn.DEDZTBH.SFhit.netUtil;

import java.io.IOException;

/**
 * Created by peiqi on 2015/12/28.
 */
public class GetHit {
    private static String BookName;
    private static int booked;
    private static int like;

    public static int GetHitNum(String bookNum) throws IOException {
        String urlStr = "http://m.sfacg.com/b/" + bookNum;
        String content = getWebPage.getAWebPage(urlStr);

        int ret;
        if (content.equals("-1")) {
            ret = -1;
        } else {
            if (content.equals("-2")) {
                ret = -2;
            } else {
                BookName = getBookName(content);
                if (BookName == null) {
                    ret = -2;
                } else {
                    //System.out.println(BookName);
                    ret = getBookNum(content);
                    getBookedAndLike(content);
                }
            }
        }

        return ret;
    }


    private static String getBookName(String Content) {
        String StrTag = "<title>";
        int StartTag = Content.indexOf(StrTag) + StrTag.length();
        int EndTag = Content.indexOf("</title>");
        String BookNameRow = Content.substring(StartTag, EndTag);

        int divide = BookNameRow.indexOf("|");
        if (divide <= 0) {
            return null;
        } else {
            return BookNameRow.substring(0, divide);
        }
    }

    private static int getBookNum(String Content) {
        String StrTag = "<span class=\"book_info3\">";
        int StartTag = Content.indexOf(StrTag) + StrTag.length();
        int EndTag = Content.indexOf("<br>");
        String RowNumInfo = Content.substring(StartTag, EndTag);
        StringBuilder ReversedBuild = new StringBuilder();
        for (int i = 0; i < RowNumInfo.length(); i++) {
            ReversedBuild.append(RowNumInfo.substring(RowNumInfo.length() - i - 1, RowNumInfo.length() - i));
        }
        String Reversed = ReversedBuild.toString();
        //System.out.println(Reversed);
        int Slash = Reversed.indexOf("/");
        return Integer.parseInt(Content.substring(EndTag - Slash + 1, EndTag - 1));
    }

    public static String getBookName() {
        return BookName;
    }


    private static void getBookedAndLike(String content) {
        int start = content.indexOf("<span class=\"icon-heart2\">");
        String cuttedContent = content.substring(start);

        int shouCang = cuttedContent.indexOf("收藏");
        int zan = cuttedContent.indexOf("赞");
        int daShang = cuttedContent.indexOf("打赏");

        int[] tags = {shouCang, zan, daShang};
        int[] result = new int[2];
        String temp;
        String resultStartTag = "<small> ";
        String resultEndTag = "</small>";

        for (int i = 0; i < tags.length - 1; i++) {
            temp = cuttedContent.substring(tags[i], tags[i + 1]);
            result[i] = Integer.parseInt(temp.substring(temp.indexOf(resultStartTag) + resultStartTag.length(), temp.indexOf(resultEndTag)));
        }

        booked = result[0];
        like = result[1];
    }

    public static int getBooked() {
        return booked;
    }

    public static int getLike() {
        return like;
    }
}




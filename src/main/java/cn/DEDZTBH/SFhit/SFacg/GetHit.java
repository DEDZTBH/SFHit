package cn.DEDZTBH.SFhit.SFacg;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by peiqi on 2015/12/28.
 */
public class GetHit {
        private String urlStr;
        public String BookName;
        private HttpURLConnection Conn;
        private boolean UnknownHosts = false;
        private int ret;
        private int booked;
        private int like;
        private String content;

    public int GetHitNum(String bookNum) throws IOException {
        urlStr = "http://m.sfacg.com/b/"+bookNum;
        //System.out.println(urlStr);
        URL url = new URL(urlStr);
        try{
            Conn = (HttpURLConnection)url.openConnection();
        }catch (UnknownHostException e){
            e.printStackTrace();
            UnknownHosts = true;
            ret = -1;
        }
        if(UnknownHosts==false){
        if(Conn.getResponseCode()!=200) {
            ret= -1;
        } else {
        InputStreamReader input = new InputStreamReader(Conn.getInputStream(),"utf-8");
        BufferedReader bufRead = new BufferedReader(input);
        String line = "";
        StringBuilder contentBuild = new StringBuilder();
        while ((line = bufRead.readLine())!=null){
            contentBuild.append(line);
        }
        content = contentBuild.toString();
//        System.out.println(content);
        BookName = getBookName(content);
        if (BookName==null){
            ret = -2;
        }else{
            //System.out.println(BookName);
            ret= getBookNum(content);
            getBookedAndLike(content);
            }
        }
        }else{
            UnknownHosts = false;
        }
        return ret;
    }

    private String getBookName(String Content) {
        String StrTag = "<title>";
        int StartTag = Content.indexOf(StrTag)+StrTag.length();
        int EndTag = Content.indexOf("</title>");
        String BookNameRow = Content.substring(StartTag,EndTag);

        int divide = BookNameRow.indexOf("|");
        if(divide<=0){
            return null;
        }else{
            return BookNameRow.substring(0,divide);
        }
    }

    private int getBookNum(String Content){
        String StrTag = "<span class=\"book_info3\">";
        int StartTag = Content.indexOf(StrTag)+StrTag.length();
        int EndTag = Content.indexOf("<br>");
        String RowNumInfo = Content.substring(StartTag,EndTag);
        StringBuilder ReversedBuild = new StringBuilder();
        for(int i=0;i<RowNumInfo.length();i++){
            ReversedBuild.append(RowNumInfo.substring(RowNumInfo.length()-i-1,RowNumInfo.length()-i));
        }
        String Reversed = ReversedBuild.toString();
        //System.out.println(Reversed);
        int Slash = Reversed.indexOf("/");
        return Integer.parseInt(Content.substring(EndTag - Slash + 1, EndTag - 1));
    }

    public String getBookName(){
        return BookName;
    }



    private void getBookedAndLike(String content){
        int start = content.indexOf("<span class=\"icon-heart2\">");
        String cuttedContent = content.substring(start);

        int shouCang = cuttedContent.indexOf("收藏");
        int zan = cuttedContent.indexOf("赞");
        int daShang = cuttedContent.indexOf("打赏");

        int[] tags = {shouCang,zan,daShang};
        int[] result = new int[2];
        String temp;
        String resultStartTag = "<small> ";
        String resultEndTag = "</small>";

        for (int i = 0; i < tags.length-1; i++){
            temp = cuttedContent.substring(tags[i],tags[i+1]);
            result[i]=Integer.parseInt(temp.substring(temp.indexOf(resultStartTag)+resultStartTag.length(),temp.indexOf(resultEndTag)));
        }

        booked = result[0];
        like = result[1];
    }

    public int getBooked(){return booked;}
    public int getLike(){return like;}
}




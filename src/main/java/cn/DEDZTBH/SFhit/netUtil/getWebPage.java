package cn.DEDZTBH.SFhit.netUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by DE_DZ_TBH on 2016/2/2.
 */
class getWebPage {

    private static HttpURLConnection Conn;
    private static boolean UnknownHosts = false;
    private static String ret;

    static String getAWebPage(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        try {
            Conn = (HttpURLConnection) url.openConnection();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            UnknownHosts = true;
            ret = "-1";
        }
        if (!UnknownHosts) {
            if (Conn.getResponseCode() != 200) {
                ret = "-2";
            } else {
                InputStreamReader input = new InputStreamReader(Conn.getInputStream(), "utf-8");
                BufferedReader bufRead = new BufferedReader(input);
                String line;
                StringBuilder contentBuild = new StringBuilder();
                while ((line = bufRead.readLine()) != null) {
                    contentBuild.append(line);
                }
                ret = contentBuild.toString();
            }
        } else {
            UnknownHosts = false;
        }

        return ret;
    }

}

package cn.DEDZTBH.SFhit.netUtil;

import java.io.IOException;

/**
 * Created by TonyLiu on 2016/2/2.
 */
public class updateChecker {

    public static String getUpdate(String currentVer) throws IOException {
        String content = getWebPage.getAWebPage("https://coding.net/u/DE_DZ_TBH/p/SFHit/git/raw/master/README.md");
        if (content.equals("-1") || content.equals("-2")) {
            return "-1";
        } else {
            String b = versionChanged(content, currentVer);
            return b.equals("0") ? "0" : b;
        }
    }

    private static String versionChanged(String content, String currentVer) {
        final String startTag = "version=={";
        final String endTag = "}";

        String newVer = content.substring(content.indexOf(startTag) + startTag.length(), content.indexOf(endTag));
        //System.out.println(newVer);
        return !newVer.equals(currentVer) ? newVer : "0";
    }

}

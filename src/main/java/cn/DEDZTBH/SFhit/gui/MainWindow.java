package cn.DEDZTBH.SFhit.gui;

import javax.swing.*;

import cn.DEDZTBH.SFhit.netUtil.GetHit;
import cn.DEDZTBH.SFhit.netUtil.updateChecker;
import cn.DEDZTBH.SFhit.util.FileManager;
import cn.DEDZTBH.SFhit.util.HitUpdate;
import cn.DEDZTBH.SFhit.util.PrefManager;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by peiqi on 2015/12/28.
 */
public class MainWindow extends JFrame {
    final String VERSION = "1.1 - SNAPSHOT";
    JLabel versionStatus = new JLabel("正在检查更新...");

    final JLabel shuHao = new JLabel("书号：");
    JTextField bookNum = new JTextField("");
    final JButton startButton = new JButton("开始");
    JLabel displayNum = new JLabel("0");
    Font numFont = new Font("Arial Black", Font.BOLD, 80);
    Font midSize = new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 32);
    JTextArea recordText = new JTextArea();
    JLabel status = new JLabel("请输入书号并点击开始");
    final JLabel shuaXin = new JLabel("刷新时间(尚未完成)");
    JTextField updateInterval = new JTextField("");
    JScrollPane scrollPane = new JScrollPane(recordText);
    final JLabel shuMing = new JLabel("书名:");
    final JLabel bookNameLabel = new JLabel("");
    final JLabel shouCang = new JLabel("收藏:");
    final JLabel xiHuan = new JLabel("喜欢:");
    JLabel booked = new JLabel();
    JLabel like = new JLabel();
    JLabel advert = new JLabel("点击这里赢本子!!!");


    public String BookName;
    public int HitNum;
//    public float updateIntervalFloat;

    PrefManager pm = new PrefManager();
    FileManager fm = new FileManager();
    HitUpdate hitUpdate = new HitUpdate();
    GetHit getHit = new GetHit();
    updateChecker uc = new updateChecker();

//    java.util.Timer timer = new Timer();

    public MainWindow() {
        setBounds(100, 100, 650, 350);
        setTitle("DEのSF全能查看器 1.1-SNAPSHOT");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        add(shuHao);
        shuHao.setSize(50, 25);
        shuHao.setLocation(50, 20);
        add(bookNum);
        bookNum.setSize(50, 25);
        bookNum.setLocation(100, 20);
        add(startButton);
        startButton.setSize(60, 25);
        startButton.setLocation(160, 20);

        add(shuMing);
        shuMing.setSize(50, 25);
        shuMing.setLocation(380, 140);
        add(bookNameLabel);
        bookNameLabel.setSize(200, 25);
        bookNameLabel.setLocation(420, 140);

        add(shouCang);
        shouCang.setSize(50,25);
        shouCang.setLocation(380, 185);
        add(booked);
        booked.setSize(200, 50);
        booked.setLocation(420, 173);
        booked.setFont(midSize);

        add(xiHuan);
        xiHuan.setSize(50,25);
        xiHuan.setLocation(380, 230);
        add(like);
        like.setSize(200, 50);
        like.setLocation(420, 218);
        like.setFont(midSize);

        add(shuaXin);
        shuaXin.setSize(150, 25);
        shuaXin.setLocation(380, 275);
        add(updateInterval);
        updateInterval.setSize(100, 25);
        updateInterval.setLocation(500, 275);

        add(displayNum);
        displayNum.setSize(500, 100);
        displayNum.setLocation(50, 40);
        displayNum.setFont(numFont);
        displayNum.setForeground(Color.ORANGE);

        add(scrollPane);
        scrollPane.setSize(320, 160);
        scrollPane.setLocation(40, 140);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        recordText.setEditable(false);

        add(status);
        status.setSize(250, 25);
        status.setLocation(300, 25);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateNum();
            }
        });

//        updateInterval.getDocument().addDocumentListener(new DocumentListener() {
//            public void insertUpdate(DocumentEvent e) {
//                changeInterval();
//            }
//
//            public void removeUpdate(DocumentEvent e) {
//                changeInterval();
//            }
//
//            public void changedUpdate(DocumentEvent e) {
//                changeInterval();
//            }
//            public void changeInterval(){
//                try{
//                    if (updateInterval.getText()!=""||updateInterval.getText()!=null)
//                    updateIntervalFloat = Float.parseFloat(updateInterval.getText());
//                    shuaXin.setText("刷新时间(分钟)");
//                } catch (NumberFormatException e){
//                    e.printStackTrace();
//                    shuaXin.setText("请输入正确的数字!");
//                }
//            }
//        });

        add(advert);
        advert.setSize(125,25);
        advert.setLocation(525,0);
        new colorChanger(this.advert).start();
        advert.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                URI uri;
                Desktop dsktp = Desktop.getDesktop();
                try {
                    uri= new URI("http://book.sfacg.com/Novel/43467/");
                    if (Desktop.isDesktopSupported()&&dsktp.isSupported(Desktop.Action.BROWSE)){
                        dsktp.browse(uri);
                    }
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }
        });

        add(versionStatus);
        versionStatus.setSize(275,25);
        versionStatus.setLocation(250,0);

        setVisible(true);

        int prefNum = pm.readPref();
        if (prefNum != -1) {
            bookNum.setText(String.valueOf(prefNum));
            fm.ReadFile(prefNum);
            displayNum.setText(String.valueOf(fm.getHit()));
            recordText.setText(String.valueOf(fm.getRecord()));
            booked.setText(String.valueOf(fm.getBooked()));
            like.setText(String.valueOf(fm.getLike()));
            updateNum();
        }


        try {
            String newVer = uc.getUpdate(VERSION);
            if (newVer.equals("-1")){
                versionStatus.setText("网络错误,无法获取信息");
            }else{
                if (newVer.equals("0")){
                    versionStatus.setText("你使用的是最新版本!");
                }else{
                    versionStatus.setText("检查到新版本:"+newVer+" 点击此处更新!");
                    versionStatus.setForeground(Color.red);
                    versionStatus.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            String[] options = {"国内","国外"};
                            int opt = JOptionPane.showOptionDialog(MainWindow.this,"你在国内还是国外？","选择下载地址",JOptionPane.DEFAULT_OPTION,JOptionPane.YES_NO_OPTION,null,options,"国内");
                            URI uri = null;
                            if (opt==JOptionPane.YES_OPTION){
                                try {
                                    uri= new URI("http://pan.baidu.com/s/1c0QuKLE");
                                } catch (URISyntaxException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            if (opt==JOptionPane.NO_OPTION){
                                try {
                                    uri= new URI("https://drive.google.com/folderview?id=0B553ho8lC0IhUW5rTk9QajlrTXc&usp=sharing");
                                } catch (URISyntaxException e1) {
                                    e1.printStackTrace();
                                }
                            }


                            Desktop dsktp = Desktop.getDesktop();
                            try {

                                if (Desktop.isDesktopSupported()&&dsktp.isSupported(Desktop.Action.BROWSE)){
                                    dsktp.browse(uri);
                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                        }

                        public void mouseEntered(MouseEvent e) {

                        }
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if (updateIntervalFloat!=0f){
//        timer.schedule(
//                new java.util.TimerTask() { public void run()
//                { updateNum(); } }, 0, (int)updateIntervalFloat*1000);
//        }

    }

    public void updateNum() {
        try {
            status.setText("正在抓取信息，请稍后...");
            HitNum = getHit.GetHitNum(bookNum.getText());
            BookName = getHit.getBookName();
            //System.out.print(BookName);
            if (HitNum == -1) {
                status.setText("信息获取失败，请检查网络连接");
            } else {
                if (HitNum == -2) {
                    status.setText("信息获取失败，请检查书号是否输入正确");
                } else {
                    int BookNum = Integer.parseInt(bookNum.getText());
                    updatePref(BookNum);
                    status.setText("更新成功 = w =");
                    displayNum.setText(HitNum + "");
                    bookNameLabel.setText(BookName);
                    String UpdateInfo = hitUpdate.Update(HitNum,getHit.getBooked(),getHit.getLike(), BookNum);
                    status.setText(UpdateInfo==null?"更新成功 = w =":"更新成功，有新收获哦 = w =");
                    fm.WriteFile(BookNum, HitNum, getHit.getBooked(),getHit.getLike(), UpdateInfo);
                    fm.ReadFile(BookNum);
                    recordText.setText(fm.getRecord());
                    booked.setText(String.valueOf(fm.getBooked()));
                    like.setText(String.valueOf(fm.getLike()));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void updatePref(int BookNum) {
        if (BookNum!=pm.readPref()){
            pm.writePref(BookNum);
        }
    }



}
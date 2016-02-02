package cn.DEDZTBH.SFhit.gui;

import javax.swing.*;

import cn.DEDZTBH.SFhit.SFacg.GetHit;
import cn.DEDZTBH.SFhit.util.FileManager;
import cn.DEDZTBH.SFhit.util.HitUpdate;
import cn.DEDZTBH.SFhit.util.PrefManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

/**
 * Created by peiqi on 2015/12/28.
 */
public class MainWindow extends JFrame {
    JLabel shuHao = new JLabel("书号：");
    JTextField bookNum = new JTextField("");
    JButton startButton = new JButton("开始");
    JLabel displayNum = new JLabel("0");
    Font numFont = new Font("Arial Black", Font.BOLD, 80);
    Font midSize = new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 32);
    JTextArea recordText = new JTextArea();
    JLabel status = new JLabel("请输入书号并点击开始");
    JLabel shuaXin = new JLabel("刷新时间(尚未完成)");
    JTextField updateInterval = new JTextField("");
    JScrollPane scrollPane = new JScrollPane(recordText);
    JLabel shuMing = new JLabel("书名:");
    JLabel bookNameLabel = new JLabel("");
    JLabel shouCang = new JLabel("收藏:");
    JLabel xiHuan = new JLabel("喜欢:");
    JLabel booked = new JLabel();
    JLabel like = new JLabel();


    public String BookName;
    public int HitNum;
//    public float updateIntervalFloat;

    PrefManager pm = new PrefManager();
    FileManager fm = new FileManager();
    HitUpdate hitUpdate = new HitUpdate();
    GetHit getHit = new GetHit();

//    java.util.Timer timer = new Timer();

    public MainWindow() {
        setBounds(100, 100, 650, 350);
        setTitle("DEのSF全能查看器 1.0-SNAPSHOT");
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
        status.setLocation(300, 20);

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
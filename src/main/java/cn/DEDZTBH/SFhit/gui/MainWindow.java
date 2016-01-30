package cn.DEDZTBH.SFhit.gui;

import javax.swing.*;

import cn.DEDZTBH.SFhit.SFacg.GetHit;
import cn.DEDZTBH.SFhit.util.FileManager;
import cn.DEDZTBH.SFhit.util.HitUpdate;
import cn.DEDZTBH.SFhit.util.PrefManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by peiqi on 2015/12/28.
 */
public class MainWindow extends JFrame {
    JLabel shuHao = new JLabel("书号：");
    JTextField bookNum = new JTextField("");
    JButton startButton = new JButton("开始");
    JLabel displayNum = new JLabel("0");
    Font numFont = new Font("Arial Black", Font.BOLD, 80);
    JTextArea recordText = new JTextArea();
    JLabel status = new JLabel("请输入书号并点击开始");
    JLabel shuaXin = new JLabel("请输入刷新时间(分钟)");
    JTextField updateInterval = new JTextField("");
    JScrollPane scroll = new JScrollPane(recordText);
    JLabel shuMing = new JLabel("书名：");
    JLabel bookNameLabel = new JLabel("");

    public String BookName;
    public int HitNum;

    PrefManager pm = new PrefManager();
    FileManager fm = new FileManager();

    public MainWindow() {
        setBounds(100, 100, 600, 300);
        setTitle("SF点击查看器 by DE");
        setVisible(true);
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

        add(shuaXin);
        shuaXin.setSize(150, 25);
        shuaXin.setLocation(330, 200);
        add(updateInterval);
        updateInterval.setSize(50, 25);
        updateInterval.setLocation(480, 200);

        add(shuMing);
        shuMing.setSize(50, 25);
        shuMing.setLocation(330, 150);
        add(bookNameLabel);
        bookNameLabel.setSize(200, 25);
        bookNameLabel.setLocation(370, 150);

        add(displayNum);
        displayNum.setSize(500, 100);
        displayNum.setLocation(50, 40);
        displayNum.setFont(numFont);
        displayNum.setForeground(Color.ORANGE);
        add(recordText);
        recordText.setSize(250, 110);
        recordText.setLocation(40, 140);
        recordText.setEnabled(false);

        add(scroll);
        scroll.setSize(20, 110);
        scroll.setLocation(290, 140);
        add(status);
        status.setSize(250, 25);
        status.setLocation(300, 20);


        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateNum();
            }
        });


        int prefNum = pm.readPref();
        if (prefNum != -1) {
            bookNum.setText(String.valueOf(prefNum));
            fm.ReadFile(prefNum);
            displayNum.setText(String.valueOf(fm.getHit()));
            recordText.setText(String.valueOf(fm.getRecord()));
            updateNum();
        }

    }

    public void updateNum() {
        try {
            status.setText("正在抓取信息，请稍后...");
            GetHit getHit = new GetHit();
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
                    HitUpdate hitUpdate = new HitUpdate();
                    String UpdateInfo = hitUpdate.Update(HitNum, BookNum);
                    fm.WriteFile(BookNum, HitNum, UpdateInfo);
                    fm.ReadFile(BookNum);
                    recordText.setText(fm.getRecord());
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
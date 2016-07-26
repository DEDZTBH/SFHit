package cn.DEDZTBH.SFhit.gui;

import cn.DEDZTBH.SFhit.netUtil.GetHit;
import cn.DEDZTBH.SFhit.netUtil.updateChecker;
import cn.DEDZTBH.SFhit.util.FileManager;
import cn.DEDZTBH.SFhit.util.HitUpdate;
import cn.DEDZTBH.SFhit.util.PrefManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.TimerTask;

/**
 * Created by DE_DZ_TBH on 2015/12/28.
 */
public class MainWindow extends JFrame {

    private JLabel shuaXin = new JLabel("刷新时间(秒)");
    private JLabel bookNameLabel = new JLabel("");
    private JTextField bookNum = new JTextField("");
    private JLabel displayNum = new JLabel("0");
    private JTextArea recordText = new JTextArea();
    private JLabel status = new JLabel("请输入书号并点击开始");
    private JTextField updateInterval = new JTextField("");
    private JLabel booked = new JLabel();
    private JLabel like = new JLabel();

    private final String VERSION = "1.6";

    private int updateIntervalInt = -1;
    private int bookNumber = -1;

    private java.util.Timer timer = new java.util.Timer();

    public MainWindow() {
        setBounds(100, 100, 650, 350);
        setTitle("DEのSF全能查看器 " + VERSION);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        JLabel shuHao = new JLabel("书号：");
        add(shuHao);
        shuHao.setSize(50, 25);
        shuHao.setLocation(50, 20);
        add(bookNum);
        bookNum.setSize(50, 25);
        bookNum.setLocation(100, 20);
        JButton startButton = new JButton("开始");
        add(startButton);
        startButton.setSize(60, 25);
        startButton.setLocation(160, 20);

        JLabel shuMing = new JLabel("书名:");
        add(shuMing);
        shuMing.setSize(50, 25);
        shuMing.setLocation(380, 140);
        add(bookNameLabel);
        bookNameLabel.setSize(200, 25);
        bookNameLabel.setLocation(420, 140);

        JLabel shouCang = new JLabel("收藏:");
        add(shouCang);
        shouCang.setSize(50, 25);
        shouCang.setLocation(380, 185);
        add(booked);
        booked.setSize(200, 50);
        booked.setLocation(420, 173);
        Font midSize = new Font("Arial", Font.PLAIN, 32);
        booked.setFont(midSize);

        JLabel xiHuan = new JLabel("喜欢:");
        add(xiHuan);
        xiHuan.setSize(50, 25);
        xiHuan.setLocation(380, 230);
        add(like);
        like.setSize(200, 50);
        like.setLocation(420, 218);
        like.setFont(midSize);

        add(shuaXin);
        shuaXin.setSize(125, 25);
        shuaXin.setLocation(380, 275);
        add(updateInterval);
        updateInterval.setSize(75, 25);
        updateInterval.setLocation(475, 275);
        JButton timeButton = new JButton("确认");
        add(timeButton);
        timeButton.setSize(60, 25);
        timeButton.setLocation(560, 275);
        timeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeInterval();
            }
        });

        add(displayNum);
        displayNum.setSize(500, 100);
        displayNum.setLocation(50, 40);
        Font numFont = new Font("Arial Black", Font.BOLD, 80);
        displayNum.setFont(numFont);
        displayNum.setForeground(Color.ORANGE);

        JScrollPane scrollPane = new JScrollPane(recordText);
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

        JLabel advert = new JLabel("点击这里赢本子!!!");
        add(advert);
        advert.setSize(125, 25);
        advert.setLocation(525, 0);
        new colorChanger(advert).start();
        advert.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                URI uri;
                //noinspection Since15
                Desktop dsktp = Desktop.getDesktop();
                try {
                    uri = new URI("http://book.sfacg.com/Novel/43467/");
                    //noinspection Since15
                    if (Desktop.isDesktopSupported() && dsktp.isSupported(Desktop.Action.BROWSE)) {
                        //noinspection Since15
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

        final JLabel versionStatus = new JLabel("正在检查更新...");
        add(versionStatus);
        versionStatus.setSize(275, 25);
        versionStatus.setLocation(250, 0);

        setVisible(true);

        PrefManager.readPref();
        int prefNum = PrefManager.getBookNum();
        int prefUpdItv = PrefManager.getUpdateIntv();
        if (prefNum != -1) {
            bookNum.setText(String.valueOf(prefNum));
            FileManager.ReadFile(prefNum);
            displayNum.setText(String.valueOf(FileManager.getHit()));
            recordText.setText(String.valueOf(FileManager.getRecord()));
            booked.setText(String.valueOf(FileManager.getBooked()));
            like.setText(String.valueOf(FileManager.getLike()));
            bookNumber = prefNum;
        }
        if (prefUpdItv != -1) {
            this.updateIntervalInt = prefUpdItv;
            updateInterval.setText(String.valueOf(updateIntervalInt));
            applyInterval(false);
        }
        if (prefNum != -1) {
            updateNum();
        }

            new Thread(new Runnable() {
                public void run() {
                    String newVer = null;
                    try {
                        newVer = updateChecker.getUpdate(VERSION);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert newVer != null;
                    if (newVer.equals("-1")) {
                        versionStatus.setText("网络错误,无法获取信息");
                    } else {
                        if (newVer.equals("0")) {
                            versionStatus.setText("你使用的是最新版本!");
                        } else {
                            versionStatus.setText("检查到新版本:" + newVer + " 点击此处更新!");
                            versionStatus.setForeground(Color.red);
                            versionStatus.addMouseListener(new MouseAdapter() {
                                public void mouseClicked(MouseEvent e) {
                                    String[] options = {"国内", "国外"};
                                    int opt = JOptionPane.showOptionDialog(MainWindow.this, "你在国内还是国外？", "选择下载地址", JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION, null, options, "国内");
                                    URI uri = null;
                                    if (opt == JOptionPane.YES_OPTION) {
                                        try {
                                            uri = new URI("https://coding.net/u/DE_DZ_TBH/p/SFHit/git/tree/master/download");
                                        } catch (URISyntaxException e1) {
                                            e1.printStackTrace();
                                        }
                                    }else if (opt == JOptionPane.NO_OPTION)
                                    {
                                        try {
                                            uri = new URI("https://github.com/DEDZTBH/SFHit/tree/master/download");
                                        } catch (URISyntaxException e1) {
                                            e1.printStackTrace();
                                        }
                                    }

//noinspection Since15
                                    Desktop dsktp = Desktop.getDesktop();
                                    try {
//noinspection Since15
                                        if (Desktop.isDesktopSupported() && dsktp.isSupported(Desktop.Action.BROWSE)) {
                                            if (uri != null) {
                                                //noinspection Since15
                                                dsktp.browse(uri);
                                            }
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
                }
            }).run();
    }

    private void updateNum() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    status.setText("正在抓取信息，请稍后...");
                    int hitNum = GetHit.GetHitNum(bookNum.getText());
                    String bookName = GetHit.getBookName();
                    int bookedNum = GetHit.getBooked();
                    int likeNum = GetHit.getLike();
                    if (hitNum == -1) {
                        status.setText("信息获取失败，请检查网络连接");
                    } else {
                        if (hitNum == -2) {
                            status.setText("信息获取失败，请检查书号是否输入正确");
                        } else {
                            bookNumber = Integer.parseInt(bookNum.getText());
                            updatePref(bookNumber, updateIntervalInt);
                            status.setText("更新成功 = w =");
                            displayNum.setText(hitNum + "");
                            booked.setText(bookedNum + "");
                            like.setText(likeNum + "");
                            bookNameLabel.setText(bookName);
                            String UpdateInfo = HitUpdate.Update(hitNum, GetHit.getBooked(), GetHit.getLike(), bookNumber);
                            String statusInfo;
                            if (UpdateInfo == null) {
                                statusInfo = "更新成功 = w =";
                            } else {
                                if (UpdateInfo.contains("点击-") || UpdateInfo.contains("收藏-") || UpdateInfo.contains("喜欢-")) {
                                    statusInfo = "更新成功，再接再厉吧 = w =";
                                } else {
                                    statusInfo = "更新成功，有新收获哦 = w =";
                                }
                            }
                            status.setText(statusInfo);
                            FileManager.WriteFile(bookNumber, hitNum, GetHit.getBooked(), GetHit.getLike(), UpdateInfo);
                            FileManager.ReadFile(bookNumber);
                            recordText.setText(FileManager.getRecord());
                            booked.setText(String.valueOf(FileManager.getBooked()));
                            like.setText(String.valueOf(FileManager.getLike()));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).run();
    }

    private void updatePref(int BookNum, int updateIntervalInt) {
        PrefManager.readPref();
        if ((BookNum != PrefManager.getBookNum() && BookNum != -1) || (updateIntervalInt != PrefManager.getUpdateIntv() && updateIntervalInt != -1)) {
            PrefManager.writePref(BookNum, updateIntervalInt);
        }
    }

    private void changeInterval() {
        try {
            if (!updateInterval.getText().equals("") && updateInterval.getText() != null)
                updateIntervalInt = Integer.parseInt(updateInterval.getText());
            if (updateIntervalInt <= 0) {
                shuaXin.setText("请输入正整数!");
                return;
            }
            shuaXin.setText("刷新时间(秒)");
            updatePref(bookNumber, updateIntervalInt);
            applyInterval(false);
        } catch (NumberFormatException e) {
            shuaXin.setText("请输入整数!");
        }
    }

    private void applyInterval(boolean refreshNow) {
        timer.cancel();
        timer = new java.util.Timer();
        int rf;
        if (!refreshNow) {
            rf = updateIntervalInt * 1000;
        } else {
            rf = 0;
        }
        timer.schedule(new TimerTask() {
            public void run() {
                updateNum();
            }
        }, (long) rf, (long) updateIntervalInt * 1000);
    }

}
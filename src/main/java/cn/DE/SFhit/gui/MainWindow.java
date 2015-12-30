package cn.DE.SFhit.gui;

import javax.swing.*;
import cn.DE.SFhit.SFacg.*;
import cn.DE.SFhit.util.HitUpdate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by peiqi on 2015/12/28.
 */
public class MainWindow extends JFrame{
    JLabel ShuHao = new JLabel("书号：");
    JTextField bookNum = new JTextField("");
    JButton Start = new JButton("开始");
    JLabel DisplayNum = new JLabel("0");
    Font NumFont = new Font("Arial Black",Font.BOLD,80);
    JTextArea Record = new JTextArea();
    JLabel Status = new JLabel("请输入书号并点击开始");
    JLabel ShuaXin = new JLabel("请输入刷新时间(分钟)");
    JTextField UpdateInterval = new JTextField("");
    JScrollPane Scroll = new JScrollPane(Record);

    public String BookName;
    public int HitNum;

    public MainWindow(){
        setBounds(100,100,600,300);
        setTitle("SF点击查看器 by DE");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        add(ShuHao);
        ShuHao.setSize(50,25);
        ShuHao.setLocation(50,20);
        add(bookNum);
        bookNum.setSize(50,25);
        bookNum.setLocation(100,20);
        add(Start);
        Start.setSize(60,25);
        Start.setLocation(160,20);

        add(ShuaXin);
        ShuaXin.setSize(150,25);
        ShuaXin.setLocation(330,200);
        add(UpdateInterval);
        UpdateInterval.setSize(50,25);
        UpdateInterval.setLocation(480,200);

        add(DisplayNum);
        DisplayNum.setSize(500,100);
        DisplayNum.setLocation(50,40);
        DisplayNum.setFont(NumFont);
        DisplayNum.setForeground(Color.ORANGE);
        add(Record);
        Record.setSize(250,110);
        Record.setLocation(40,140);
        Record.setEnabled(false);

        add(Scroll);
        Scroll.setSize(20,110);
        Scroll.setLocation(290,140);

        add(Status);
        Status.setSize(250,25);
        Status.setLocation(300,20);



        Start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateNum();
            }
        });
    }
    public void UpdateNum(){
        try{
                Status.setText("正在抓取信息，请稍后...");
                GetHit getHit = new GetHit();
                HitNum = getHit.GetHitNum(bookNum.getText());
                BookName = getHit.getBookName();
                System.out.print(BookName);
                if(HitNum == -1){
                    Status.setText("信息获取失败，请检查网络连接");
                }else{if(HitNum == -2){
                    Status.setText("信息获取失败，请检查书号是否输入正确");
                }
                else{
                    Status.setText("更新成功 = w =");
                    DisplayNum.setText(HitNum+"");
                    HitUpdate update = new HitUpdate();
                    String UpdateInfo = update.Update(HitNum);
                    //System.out.println(UpdateInfo);
                    Record.setText(UpdateInfo+"\n"+Record.getText());
                }}
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

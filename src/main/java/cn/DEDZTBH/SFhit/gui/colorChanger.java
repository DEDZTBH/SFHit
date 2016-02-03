package cn.DEDZTBH.SFhit.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Codetector on 2016/1/13.
 *
 * @author Codetector
 */
public class colorChanger extends Thread {
    private JLabel label;

    public colorChanger(JLabel label) {
        this.label = label;
    }

    @Override
    public void run() {
        while (true) {
            for (int r = 50; r < 256; r += 10) {
                for (int g = 50; g < 256; g += 10) {
                    for (int b = 50; b < 256; b++) {
                        label.setForeground(new Color(r, g, b));
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

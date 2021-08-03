package gds.gui;

import gds.exception.RGBOutOfBoundException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener
{
    DrawPanel drawPanel=new DrawPanel(this);

    JMenuBar menuBar=new JMenuBar();
    JMenu file=new JMenu("文件");
    JMenuItem exit=new JMenuItem("退出");
    JMenuItem clear=new JMenuItem("清屏");


    JPanel settingPanel=new JPanel();

    JPanel toolsPanel=new JPanel();
    JButton lineDraw=new JButton("画直线");
    JButton rectDraw=new JButton("画矩形");
    JButton ovalDraw=new JButton("画椭圆");
    JButton freeDraw=new JButton("自由画笔");
    JButton textDraw=new JButton("置入文字");
    JTextField textField=new JTextField();

    JPanel strokeSet=new JPanel();
    JButton colorSet=new JButton("设为该颜色");
    JLabel rLabel=new JLabel("R");
    JTextField rSet=new JTextField("0");
    JLabel gLabel=new JLabel("G");
    JTextField gSet=new JTextField("0");
    JLabel bLabel=new JLabel("B");
    JTextField bSet=new JTextField("0");

    JPanel statusPanel=new JPanel();
    JPanel colorPanel=new JPanel();
    Color drawColor=new Color(0,0,0);
    JLabel statusLabel=new JLabel("一切正常");

    public String getTextField()
    {
        return textField.getText();
    }

    public void setStatusLabel(String string)
    {
        statusLabel.setText(string);
    }

    public MainWindow (String title)
    {
        super(title);

        clear.addActionListener(drawPanel);
        file.add(clear);
        exit.addActionListener(this);
        file.add(exit);
        menuBar.add(file);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        drawPanel.setDrawColor(drawColor);

        settingPanel.setLayout(new BorderLayout());
        toolsPanel.setLayout(new GridLayout(1,6));
        toolsPanel.add(lineDraw);
        lineDraw.addActionListener(drawPanel);
        toolsPanel.add(rectDraw);
        rectDraw.addActionListener(drawPanel);
        toolsPanel.add(ovalDraw);
        ovalDraw.addActionListener(drawPanel);
        toolsPanel.add(freeDraw);
        freeDraw.addActionListener(drawPanel);
        toolsPanel.add(textDraw);
        textDraw.addActionListener(drawPanel);
        toolsPanel.add(textField);
        settingPanel.add(BorderLayout.NORTH,toolsPanel);

        getContentPane().add(BorderLayout.CENTER,drawPanel);

        strokeSet.setLayout(new GridLayout(2,4));
        strokeSet.add(rLabel);
        strokeSet.add(gLabel);
        strokeSet.add(bLabel);
        strokeSet.add(new JLabel(""));
        strokeSet.add(rSet);
        strokeSet.add(gSet);
        strokeSet.add(bSet);
        colorSet.addActionListener(this);
        strokeSet.add(colorSet);
        settingPanel.add(BorderLayout.SOUTH,strokeSet);

        getContentPane().add(BorderLayout.NORTH,settingPanel);


        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        colorPanel.setPreferredSize(new Dimension(32,32));
        colorPanel.setBackground(drawColor);
        statusPanel.add(colorPanel);
        statusPanel.add(statusLabel);
        getContentPane().add(BorderLayout.SOUTH,statusPanel);

        pack();
        setVisible(true);

    }

    private Color createColor (int R,int G,int B) throws RGBOutOfBoundException
    {
        if(R<0||R>255||G<0||G>255||B<0||B>255)
            throw new RGBOutOfBoundException();
        rSet.setText("0");
        gSet.setText("0");
        bSet.setText("0");
        return new Color(R,G,B);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        if("退出".equals(actionEvent.getActionCommand()))
        {
            statusLabel.setText("退出");
            System.exit(0);
        }
       try
        {
            if("设为该颜色".equals(actionEvent.getActionCommand()))
            {
                drawColor=createColor(Integer.valueOf(rSet.getText()),Integer.valueOf(gSet.getText()), Integer.valueOf(bSet.getText()));
                statusLabel.setText("已将颜色设为"+drawColor.toString());
                colorPanel.setBackground(drawColor);
                drawPanel.setDrawColor(drawColor);
            }
        }
        catch(NumberFormatException e)
        {
            statusLabel.setText("RGB值输入不是数字");
        }
        catch (RGBOutOfBoundException e)
        {
            statusLabel.setText("RGB值输入的数字超出范围[0,255]");
        }
    }
}

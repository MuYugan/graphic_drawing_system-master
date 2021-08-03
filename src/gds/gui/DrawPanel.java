package gds.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawPanel extends JPanel implements ActionListener
{
    int x1,x2,y1,y2;
    DrawStatus drawStatus=DrawStatus.None;
    MainWindow FatherWindow;
    Color drawColor;


    public void setDrawColor(Color drawColor)
    {
        this.drawColor=drawColor;
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(drawColor);
        switch(drawStatus)
        {
            case Line:
                g.drawLine(x1,y1,x2,y2);
                break;
            case Oval:
                g.drawOval(x1,y1,x2-x1,y2-y1);
                break;
            case Rect:
                g.drawRect(x1,y1,x2-x1,y2-y1);
                break;
        }
    }

    public DrawPanel(MainWindow fatherWindow)
    {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1024,720));
        FatherWindow=fatherWindow;

        addMouseListener(new MouseAdapter() {
            @Override

            public void mousePressed(MouseEvent mouseEvent)
            {
                x1=mouseEvent.getX();
                y1=mouseEvent.getY();
                if(drawStatus==DrawStatus.Text)
                {
                    x2=mouseEvent.getX();
                    y2=mouseEvent.getY();
                    Graphics2D g=(Graphics2D)getGraphics();
                    g.setColor(drawColor);
                    String string=FatherWindow.getTextField();
                    if(string.equals(""))
                        g.drawString("("+x2+","+y2+")",x2,y2);
                    else
                        g.drawString(string,x2,y2);
                }
            }

            public void mouseReleased(MouseEvent mouseEvent)
            {
                x2=mouseEvent.getX();
                y2=mouseEvent.getY();
                Graphics2D g=(Graphics2D)getGraphics();
                g.setColor(drawColor);
                switch(drawStatus)
                {
                    case Line:
                        FatherWindow.setStatusLabel("在("+x1+","+y1+")到("+x2+","+y2+")绘制线段");
                        g.drawLine(x1,y1,x2,y2);
                        break;
                    case Oval:
                        FatherWindow.setStatusLabel("在("+x1+","+y1+")到("+x2+","+y2+")绘制椭圆");
                        g.drawOval(x1,y1,x2-x1,y2-y1);
                        break;
                    case Rect:
                        FatherWindow.setStatusLabel("在("+x1+","+y1+")到("+x2+","+y2+")绘制矩形");
                        g.drawLine(x1,y1,x1,y2);
                        g.drawLine(x1,y2,x2,y2);
                        g.drawLine(x2,y2,x2,y1);
                        g.drawLine(x2,y1,x1,y1);
                        break;
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent mouseEvent)
            {
                x2=mouseEvent.getX();
                y2=mouseEvent.getY();
                Graphics2D g=(Graphics2D)getGraphics();
                g.setColor(drawColor);
                switch(drawStatus)
                {
                    /*
                    case Line:
                        //repaint();
                        g.drawLine(x1,y1,x2,y2);
                        break;
                    case Oval:
                        //repaint();
                        g.drawOval(x1,y1,x2-x1,y2-y1);
                        break;
                    case Rect:
                        //repaint();
                        g.drawRect(x1,y1,x2-x1,y2-y1);

                        break;
                     */
                    case Free:
                        g.drawLine(x1,y1,x2,y2);
                        x1=x2;
                        y1=y2;
                        break;
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {

        if("画直线".equals(actionEvent.getActionCommand()))
        {
            FatherWindow.setStatusLabel("为画直线状态");
            drawStatus=DrawStatus.Line;
        }

        if("画矩形".equals(actionEvent.getActionCommand()))
        {
            FatherWindow.setStatusLabel("为画矩形状态");
            drawStatus=DrawStatus.Rect;
        }

        if("画椭圆".equals(actionEvent.getActionCommand()))
        {
            FatherWindow.setStatusLabel("为画椭圆状态");
            drawStatus=DrawStatus.Oval;
        }

        if("自由画笔".equals(actionEvent.getActionCommand()))
        {
            FatherWindow.setStatusLabel("自由画笔");
            drawStatus=DrawStatus.Free;
        }

        if("置入文字".equals(actionEvent.getActionCommand()))
        {
            FatherWindow.setStatusLabel("为置入文字状态");
            drawStatus=DrawStatus.Text;
        }

        if("清屏".equals(actionEvent.getActionCommand()))
        {
            //Graphics2D g=(Graphics2D)getGraphics();
            FatherWindow.setStatusLabel("已经清屏");
            repaint();
        }
    }
}

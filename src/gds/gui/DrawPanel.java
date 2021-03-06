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
                        FatherWindow.setStatusLabel("???("+x1+","+y1+")???("+x2+","+y2+")????????????");
                        g.drawLine(x1,y1,x2,y2);
                        break;
                    case Oval:
                        FatherWindow.setStatusLabel("???("+x1+","+y1+")???("+x2+","+y2+")????????????");
                        g.drawOval(x1,y1,x2-x1,y2-y1);
                        break;
                    case Rect:
                        FatherWindow.setStatusLabel("???("+x1+","+y1+")???("+x2+","+y2+")????????????");
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

        if("?????????".equals(actionEvent.getActionCommand()))
        {
            FatherWindow.setStatusLabel("??????????????????");
            drawStatus=DrawStatus.Line;
        }

        if("?????????".equals(actionEvent.getActionCommand()))
        {
            FatherWindow.setStatusLabel("??????????????????");
            drawStatus=DrawStatus.Rect;
        }

        if("?????????".equals(actionEvent.getActionCommand()))
        {
            FatherWindow.setStatusLabel("??????????????????");
            drawStatus=DrawStatus.Oval;
        }

        if("????????????".equals(actionEvent.getActionCommand()))
        {
            FatherWindow.setStatusLabel("????????????");
            drawStatus=DrawStatus.Free;
        }

        if("????????????".equals(actionEvent.getActionCommand()))
        {
            FatherWindow.setStatusLabel("?????????????????????");
            drawStatus=DrawStatus.Text;
        }

        if("??????".equals(actionEvent.getActionCommand()))
        {
            //Graphics2D g=(Graphics2D)getGraphics();
            FatherWindow.setStatusLabel("????????????");
            repaint();
        }
    }
}

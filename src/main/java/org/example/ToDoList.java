package org.example;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Component;

class Task extends JPanel {

    JLabel index;
    JTextField taskName;
    JButton done;

    Color ResedaGreen = new Color(116,139,117);
    Color Amaranth = new Color(219,50,77);
    Color Mint = new Color(239,246,238);
    Color CoolGray = new Color(145,151,174);
    Color PrussianBlue = new Color(39,48,67);
    Color Lavender = new Color(209,204,220);

    private boolean checked;

    Task() {
        this.setPreferredSize(new Dimension(400, 20));
        this.setBackground(Mint);

        this.setLayout(new BorderLayout());

        checked = false;

        index = new JLabel("");
        index.setPreferredSize(new Dimension(20, 20));
        index.setHorizontalAlignment(JLabel.CENTER);
        this.add(index, BorderLayout.WEST);

        taskName = new JTextField("Write something...");
        taskName.setBorder(BorderFactory.createEmptyBorder());
        taskName.setBackground(Mint);

        this.add(taskName, BorderLayout.CENTER);

        done = new JButton("DONE");
        done.setPreferredSize(new Dimension(80, 20));
        done.setBorder(BorderFactory.createEmptyBorder());
        done.setBackground(Amaranth);
        done.setFocusPainted(false);

        this.add(done, BorderLayout.EAST);

    }

    public void changeIndex(int num) {
        this.index.setText(num + "");
        this.revalidate();
    }

    public JButton getDone() {
        return done;
    }

    public boolean getState() {
        return checked;
    }

    public void changeState() {
        this.setBackground(ResedaGreen);
        taskName.setBackground(ResedaGreen);
        done.setBackground(ResedaGreen);
        checked = true;
        revalidate();
    }
}

class List extends JPanel {

    Color Lavender = new Color(209,204,220);

    List() {

        GridLayout layout = new GridLayout(10, 1);
        layout.setVgap(5);

        this.setLayout(layout);
        this.setPreferredSize(new Dimension(400, 560));
        this.setBackground(Lavender);
    }

    public void updateNumbers() {
        Component[] listItems = this.getComponents();

        for (int i = 0; i < listItems.length; i++) {
            if (listItems[i] instanceof Task) {
                ((Task) listItems[i]).changeIndex(i + 1);
            }
        }

    }

    public void removeCompletedTasks() {

        for (Component c : getComponents()) {
            if (c instanceof Task) {
                if (((Task) c).getState()) {
                    remove(c);
                    updateNumbers();
                }
            }
        }

    }
}

class Footer extends JPanel {

    JButton addTask;
    JButton clear;

    Color CoolGray = new Color(145,151,174);
    Color Lavender = new Color(209,204,220);
    Border raisedLevelBorderAdd = BorderFactory.createRaisedBevelBorder();


    Footer() {
        this.setPreferredSize(new Dimension(400, 60));
        this.setBackground(Lavender);

        addTask = new JButton("ADD TASK");
        addTask.setBorder(raisedLevelBorderAdd);
        Border border = addTask.getBorder();
        Border margin = new EmptyBorder(10,10,10,10);
        addTask.setBorder(new CompoundBorder(border,margin));
        addTask.setFont(new Font("Sans-serif", Font.BOLD, 15));
        addTask.setVerticalAlignment(JButton.BOTTOM);
        addTask.setBackground(CoolGray);
        this.add(addTask);

        this.add(Box.createHorizontalStrut(20)); // espace entre les boutons

        clear = new JButton("CLEAR FINISHED TASKS");
        clear.setBorder(raisedLevelBorderAdd);
        clear.setBorder(new CompoundBorder(border,margin));
        clear.setFont(new Font("Sans-serif", Font.BOLD, 15));
        clear.setBackground(CoolGray);
        this.add(clear);
    }

    public JButton getNewTask() {
        return addTask;
    }

    public JButton getClear() {
        return clear;
    }
}

class TitleBar extends JPanel {

    Color Lavender = new Color(209,204,220);
    Color CoolGray = new Color(145,151,174);


    TitleBar() {
        this.setPreferredSize(new Dimension(400, 80));
        this.setBackground(Lavender);
        JLabel titleText = new JLabel("TO DO LIST");
        Border RaisedLevelBorderClear = BorderFactory.createRaisedBevelBorder();
        titleText.setBorder(RaisedLevelBorderClear);
        titleText.setBackground(CoolGray);
        titleText.setPreferredSize(new Dimension(200, 60));
        titleText.setFont(new Font("Sans-serif", Font.BOLD, 20));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleText);
    }
}

class AppFrame extends JFrame {

    private TitleBar title;
    private Footer footer;
    private List list;

    private JButton newTask;
    private JButton clear;
    private JButton done;


    AppFrame() {
        this.setSize(400, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        title = new TitleBar();
        footer = new Footer();
        list = new List();

        this.add(title, BorderLayout.NORTH);
        this.add(footer, BorderLayout.SOUTH);
        this.add(list, BorderLayout.CENTER);

        newTask = footer.getNewTask();
        clear = footer.getClear();

        addListeners();
    }

    public void addListeners() {
        newTask.addMouseListener(new MouseAdapter() {
            @override
            public void mousePressed(MouseEvent e) {
                Task task = new Task();
                list.add(task);
                list.updateNumbers();

                task.getDone().addMouseListener(new MouseAdapter() {
                    @override
                    public void mousePressed(MouseEvent e) {

                        task.changeState();
                        list.updateNumbers();
                        revalidate();

                    }
                });
            }

        });

        clear.addMouseListener(new MouseAdapter() {
            @override
            public void mousePressed(MouseEvent e) {
                list.removeCompletedTasks();
                repaint();
            }
        });
    }

}

public class ToDoList {

    public static void main(String args[]) {
        AppFrame frame = new AppFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

@interface override {

}

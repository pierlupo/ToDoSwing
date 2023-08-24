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

    Color pink = new Color(255, 161, 161);
    Color green = new Color(188, 226, 158);
    Color ResedaGreen = new Color(116,139,117);
    Color doneColor = new Color(233, 119, 119);
    Color Amaranth = new Color(219,50,77);
    Color Mint = new Color(239,246,238);
    Color CoolGray = new Color(145,151,174);
    Color PrussianBlue = new Color(39,48,67);
    Color Lavender = new Color(209,204,220);

    private boolean checked;

    Task() {
        this.setPreferredSize(new Dimension(400, 20)); // set size of task
        this.setBackground(Mint); // set background color of task

        this.setLayout(new BorderLayout()); // set layout of task

        checked = false;

        index = new JLabel(""); // create index label
        index.setPreferredSize(new Dimension(20, 20)); // set size of index label
        index.setHorizontalAlignment(JLabel.CENTER); // set alignment of index label
        this.add(index, BorderLayout.WEST); // add index label to task

        taskName = new JTextField("Write something..."); // create task name text field
        taskName.setBorder(BorderFactory.createEmptyBorder()); // remove border of text field
        taskName.setBackground(Mint); // set background color of text field

        this.add(taskName, BorderLayout.CENTER);

        done = new JButton("DONE");
        done.setPreferredSize(new Dimension(80, 20));
        done.setBorder(BorderFactory.createEmptyBorder());
        done.setBackground(Amaranth);
        done.setFocusPainted(false);

        this.add(done, BorderLayout.EAST);

    }

    public void changeIndex(int num) {
        this.index.setText(num + ""); // num to String
        this.revalidate(); // refresh
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

    Color lightColor = new Color(252, 221, 176);
    Color Lavender = new Color(209,204,220);

    List() {

        GridLayout layout = new GridLayout(10, 1);
        layout.setVgap(5); // Vertical gap

        this.setLayout(layout); // 10 tasks
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
                    remove(c); // remove the component
                    updateNumbers(); // update the indexing of all items
                }
            }
        }

    }
}

class Footer extends JPanel {

    JButton addTask;
    JButton clear;

    Color orange = new Color(233, 133, 128);
    Color CoolGray = new Color(145,151,174);
    Color lightColor = new Color(252, 221, 176);
    Color Lavender = new Color(209,204,220);
    Border raisedLevelBorderAdd = BorderFactory.createRaisedBevelBorder();


    Footer() {
        this.setPreferredSize(new Dimension(400, 60));
        this.setBackground(Lavender);

        addTask = new JButton("ADD TASK"); // add task button
        addTask.setBorder(raisedLevelBorderAdd);
        Border border = addTask.getBorder();
        Border margin = new EmptyBorder(10,10,10,10);
        addTask.setBorder(new CompoundBorder(border,margin));
        addTask.setFont(new Font("Sans-serif", Font.BOLD, 15)); // set font
        addTask.setVerticalAlignment(JButton.BOTTOM); // align text to bottom
        addTask.setBackground(CoolGray); // set background color
        this.add(addTask); // add to footer

        this.add(Box.createHorizontalStrut(20)); // Space between buttons

        clear = new JButton("CLEAR FINISHED TASKS"); // clear button
        clear.setBorder(raisedLevelBorderAdd);
        clear.setBorder(new CompoundBorder(border,margin));
        clear.setFont(new Font("Sans-serif", Font.BOLD, 15)); // set font
        //clear.setBorder(emptyBorder); // remove border
        clear.setBackground(CoolGray); // set background color
        this.add(clear); // add to footer
    }

    public JButton getNewTask() {
        return addTask;
    }

    public JButton getClear() {
        return clear;
    }
}

class TitleBar extends JPanel {

    Color lightColor = new Color(252, 221, 176);
    Color Lavender = new Color(209,204,220);
    Color CoolGray = new Color(145,151,174);


    TitleBar() {
        this.setPreferredSize(new Dimension(400, 80)); // Size of the title bar
        this.setBackground(Lavender); // Color of the title bar
        JLabel titleText = new JLabel("TO DO LIST"); // Text of the title bar
        Border RaisedLevelBorderClear = BorderFactory.createRaisedBevelBorder();
        titleText.setBorder(RaisedLevelBorderClear);
        titleText.setBackground(CoolGray);
        titleText.setPreferredSize(new Dimension(200, 60)); // Size of the text
        titleText.setFont(new Font("Sans-serif", Font.BOLD, 20)); // Font of the text
        titleText.setHorizontalAlignment(JLabel.CENTER); // Align the text to the center
        this.add(titleText); // Add the text to the title bar
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
        this.setVisible(true); // Make visible

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
                list.removeCompletedTasks(); // Removes all tasks that are done
                repaint(); // Repaints the list
            }
        });
    }

}

public class ToDoList {

    public static void main(String args[]) {
        AppFrame frame = new AppFrame(); // Create the frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}

@interface override {

}

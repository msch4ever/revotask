package com.los.revotask;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UiRunner extends JFrame {

    public UiRunner() {
        initUI();
    }

    private void initUI() {

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton runButton = new JButton("Run server");
        runButton.setToolTipText("Run rest server");

        JButton exitButton = new JButton("Stop");
        exitButton.setToolTipText("Stop rest server");

        runButton.addActionListener((ActionEvent event) -> {
            new EasterEgg().runEasterEgg();
            ApplicationServer.startServer();
        });
        exitButton.addActionListener((ActionEvent event) -> System.exit(0));

        createLayout(runButton, exitButton, panel);

        setTitle("REVOTASK");
        setSize(170, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createLayout(JComponent... arg) {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setHorizontalGroup(gl.createParallelGroup()
                .addGroup(gl.createSequentialGroup().addComponent(arg[0], 150, 150, 150))
                .addGroup(gl.createSequentialGroup().addComponent(arg[1], 150, 150, 150))
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
                .addGap(4)
                .addComponent(arg[1])
                .addGap(4)
        );
        pack();
    }
}
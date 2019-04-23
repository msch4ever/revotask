package com.los.revotask.app;


import com.los.revotask.util.EasterEgg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UiRunner extends JFrame {
    private EasterEgg egg;

    public UiRunner() {
        egg = new EasterEgg();
        initUI();
    }

    private void initUI() {

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel disclaimer = new JLabel("This UI is created to start/stop server for convenience");
        disclaimer.setFont(new Font("BOLD", Font.BOLD, 15));

        JButton runButton = new JButton("Run server");
        runButton.setToolTipText("Run rest server");

        JButton exitButton = new JButton("Stop Server");
        exitButton.setToolTipText("Stop rest server");

        JButton stopThisButton = new JButton("Stop playing this glorious keygen easter egg!");
        stopThisButton.setToolTipText("Stop This");
        stopThisButton.setVisible(false);

        runButton.addActionListener((ActionEvent event) -> {
            egg.play();
            ApplicationServer.startServer();
            stopThisButton.setVisible(true);
            repaint();
        });
        exitButton.addActionListener((ActionEvent event) -> ApplicationServer.stopServer());

        stopThisButton.addActionListener((ActionEvent event) -> egg.stop());

        createLayout(disclaimer, runButton, exitButton, stopThisButton, panel);

        setTitle("REVOTASK");
        setSize(400, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createLayout(JComponent... arg) {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setHorizontalGroup(gl.createParallelGroup()
                .addGroup(gl.createSequentialGroup().addComponent(arg[0], 100, 250, 400))
                .addGroup(gl.createSequentialGroup().addComponent(arg[1], 100, 250, 400))
                .addGroup(gl.createSequentialGroup().addComponent(arg[2], 100, 250, 400))
                .addGroup(gl.createSequentialGroup().addComponent(arg[3], 100, 250, 400))
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(10)
                .addComponent(arg[0])
                .addGap(20)
                .addComponent(arg[1])
                .addGap(4)
                .addComponent(arg[2])
                .addGap(4)
                .addComponent(arg[3])
                .addGap(4)
        );
        pack();
    }
}
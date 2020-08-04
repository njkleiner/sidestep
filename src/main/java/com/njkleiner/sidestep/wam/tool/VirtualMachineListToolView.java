package com.njkleiner.sidestep.wam.tool;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import com.njkleiner.sidestep.wam.value.VirtualMachine;
import lombok.Getter;

public class VirtualMachineListToolView {

    @Getter
    private final JFrame frame;

    @Getter
    private final JList<VirtualMachine> targetList;

    @Getter
    private final JButton injectButton;

    public VirtualMachineListToolView(VirtualMachine[] targets) {
        this.frame = new JFrame("sidestep");
        this.targetList = new JList<VirtualMachine>(targets);
        this.injectButton = new JButton("Inject");

        this.frame.setLayout(new FlowLayout());
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.targetList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.targetList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        this.targetList.setVisibleRowCount(-1);
        this.targetList.setFixedCellHeight(20);
        this.targetList.setFixedCellWidth(350);

        this.injectButton.setEnabled(false);

        JScrollPane pane = new JScrollPane(this.targetList);

        this.frame.add(pane);
        this.frame.add(this.injectButton);
    }

    public void show() {
        this.frame.setSize(400, 250);
        this.frame.setVisible(true);
    }

    public void hide() {
        this.frame.setVisible(false);
        this.frame.dispose();
    }

}

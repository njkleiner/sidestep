package com.njkleiner.sidestep.wam.tool;

import java.io.File;
import java.util.Collections;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.njkleiner.sidestep.wam.entity.JavaAgent;
import com.njkleiner.sidestep.wam.service.InjectionService;
import com.njkleiner.sidestep.wam.value.VirtualMachine;

public class VirtualMachineListTool {

    private final InjectionService service;
    private final VirtualMachineListToolView view;

    public VirtualMachineListTool(InjectionService service) {
        this.service = service;

        Set<VirtualMachine> targets = null;

        try {
            targets = this.service.listTargets();
        } catch (Exception exception) {
            exception.printStackTrace();

            targets = Collections.emptySet();
        }

        this.view = new VirtualMachineListToolView((VirtualMachine[]) targets.toArray(new VirtualMachine[targets.size()]));

        this.view.getTargetList().addListSelectionListener(event -> {
            this.view.getInjectButton().setEnabled(true);
        });

        this.view.getInjectButton().addActionListener(action -> {
            VirtualMachine target = this.view.getTargetList().getSelectedValue();

            if (target != null) {
                FileFilter filter = new FileNameExtensionFilter("JAR files", "jar");

                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Select agent file");
                chooser.setFileFilter(filter);

                int result = chooser.showOpenDialog(this.view.getFrame());

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();

                    if (filter.accept(file)) {
                        JavaAgent agent = new JavaAgent(file);

                        if (agent.isInjectable()) {
                            try {
                                this.service.inject(target, agent);

                                JOptionPane.showMessageDialog(this.view.getFrame(), "Injection successful", "sidestep", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception exception) {
                                String message = exception.getMessage();

                                if (message == null) {
                                    message = exception.getCause().getMessage();
                                }

                                JOptionPane.showMessageDialog(this.view.getFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                                exception.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        this.view.show();
    }

}

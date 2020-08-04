package com.njkleiner.sidestep;

import javax.swing.SwingUtilities;

import com.njkleiner.sidestep.hook.AttachLibraryHook;
import com.njkleiner.sidestep.hook.SystemFileHook;
import com.njkleiner.sidestep.hook.ToolsHook;
import com.njkleiner.sidestep.wam.service.DefaultInjectionService;
import com.njkleiner.sidestep.wam.tool.VirtualMachineListTool;

public class Bootstrap {

    public static void main(String[] arguments) {
        SystemFileHook tools = new ToolsHook();
        SystemFileHook attach = new AttachLibraryHook();

        try {
            tools.install();
            attach.install();
        } catch (Exception exception) {
            //TODO: Show error dialog.
            exception.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new VirtualMachineListTool(new DefaultInjectionService());
        });
    }

}

package com.njkleiner.sidestep;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import com.njkleiner.sidestep.hook.AttachLibraryHook;
import com.njkleiner.sidestep.hook.SystemFileHook;
import com.njkleiner.sidestep.hook.ToolsHook;
import com.njkleiner.sidestep.inject.AgentInjector;
import com.njkleiner.sidestep.inject.DefaultAgentInjector;

public class Main {

    public static void main(String[] arguments) {
        SystemFileHook tools = new ToolsHook();
        SystemFileHook attach = new AttachLibraryHook();

        try {
            tools.install();
            attach.install();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        AgentInjector injector = new DefaultAgentInjector();

        if (arguments.length > 1) {
            String target = arguments[0];
            File agent = Paths.get(arguments[1]).toFile();

            try {
                injector.inject(target, agent);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            try {
                Set<String> targets = injector.listTargets().stream()
                    .filter(target -> !target.isEmpty())
                    .collect(Collectors.toSet());

                System.out.println(String.join(", ", targets));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

}

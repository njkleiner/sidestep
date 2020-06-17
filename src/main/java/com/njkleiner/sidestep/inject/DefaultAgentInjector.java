package com.njkleiner.sidestep.inject;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DefaultAgentInjector implements AgentInjector {

    @Override
    public void inject(String target, File agent) throws Exception {
        Class<?> virtualMachineDescriptor = Class.forName("com.sun.tools.attach.VirtualMachineDescriptor");
        Method id = virtualMachineDescriptor.getDeclaredMethod("id");
        Method displayName = virtualMachineDescriptor.getDeclaredMethod("displayName");

        Class<?> virtualMachine = Class.forName("com.sun.tools.attach.VirtualMachine");
        Method list = virtualMachine.getDeclaredMethod("list");
        Method attach = virtualMachine.getDeclaredMethod("attach", String.class);
        Method loadAgent = virtualMachine.getDeclaredMethod("loadAgent", String.class);
        Method detach = virtualMachine.getDeclaredMethod("detach");

        ArrayList<Object> descriptors = (ArrayList<Object>) list.invoke(null);

        for (Object descriptor : descriptors) {
            Object name = displayName.invoke(descriptor);

            if (name.toString().equalsIgnoreCase(target)) {
                Object pid = id.invoke(descriptor);

                Object machine = attach.invoke(null, pid);
                loadAgent.invoke(machine, agent.getAbsolutePath());
                detach.invoke(machine);

                break;
            }
        }
    }

    @Override
    public Set<String> listTargets() throws Exception {
        Set<String> targets = new HashSet<String>();

        Class<?> virtualMachineDescriptor = Class.forName("com.sun.tools.attach.VirtualMachineDescriptor");
        Method displayName = virtualMachineDescriptor.getDeclaredMethod("displayName");

        Class<?> virtualMachine = Class.forName("com.sun.tools.attach.VirtualMachine");
        Method list = virtualMachine.getDeclaredMethod("list");

        ArrayList<Object> descriptors = (ArrayList<Object>) list.invoke(null);

        for (Object descriptor : descriptors) {
            String target = (String) displayName.invoke(descriptor);

            targets.add(target);
        }

        return targets;
    }

}

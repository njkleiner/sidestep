package com.njkleiner.sidestep.wam.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.njkleiner.sidestep.wam.entity.JavaAgent;
import com.njkleiner.sidestep.wam.value.VirtualMachine;

public class DefaultInjectionService implements InjectionService {

    @Override
    public void inject(VirtualMachine target, JavaAgent agent) throws Exception {
        if (!agent.isInjectable()) {
            throw new IllegalArgumentException("Agent is not injectable");
        }

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

            if (name.toString().equalsIgnoreCase(target.getId())) {
                Object pid = id.invoke(descriptor);

                Object machine = attach.invoke(null, pid);
                loadAgent.invoke(machine, agent.getFile().getAbsolutePath());
                detach.invoke(machine);

                break;
            }
        }
    }

    @Override
    public Set<VirtualMachine> listTargets() throws Exception {
        Set<VirtualMachine> targets = new HashSet<VirtualMachine>();

        Class<?> virtualMachineDescriptor = Class.forName("com.sun.tools.attach.VirtualMachineDescriptor");
        Method displayName = virtualMachineDescriptor.getDeclaredMethod("displayName");

        Class<?> virtualMachine = Class.forName("com.sun.tools.attach.VirtualMachine");
        Method list = virtualMachine.getDeclaredMethod("list");

        ArrayList<Object> descriptors = (ArrayList<Object>) list.invoke(null);

        for (Object descriptor : descriptors) {
            String target = (String) displayName.invoke(descriptor);

            if (target != null && !target.isEmpty()) {
                targets.add(new VirtualMachine(target));
            }
        }

        return targets;
    }

}

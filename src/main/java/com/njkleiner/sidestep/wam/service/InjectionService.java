package com.njkleiner.sidestep.wam.service;

import java.util.Set;

import com.njkleiner.sidestep.wam.entity.JavaAgent;
import com.njkleiner.sidestep.wam.value.VirtualMachine;

public interface InjectionService {

    public void inject(VirtualMachine target, JavaAgent agent) throws Exception;

    public Set<VirtualMachine> listTargets() throws Exception;

}

package com.njkleiner.sidestep.inject;

import java.io.File;
import java.util.Set;

/**
 * An {@link AgentInjector} is responsible for injecting agents into running
 * virtual machines and listing possible injection targets.
 */
public interface AgentInjector {

    /**
     * Inject an agent into a running virtual machine.
     *
     * @param target The display name of the target virtual machine.
     * @param agent The JAR file that contains the agent to be injected.
     *
     * @throws Exception
     */
    public void inject(String target, File agent) throws Exception;

    /**
     * List the display names of running virtual machines suitable for injection.
     *
     * @return A {@link Set} of the display names of suitable injection targets.
     *
     * @throws Exception
     */
    public Set<String> listTargets() throws Exception;

}

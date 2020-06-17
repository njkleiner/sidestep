package com.njkleiner.sidestep.hook;

/**
 * A {@link SystemFileHook} represents a system file that needs to be dynamically
 * loaded at runtime.
 */
public interface SystemFileHook {

    /**
     * Load the system file.
     *
     * @throws Exception
     */
    public void install() throws Exception;

}

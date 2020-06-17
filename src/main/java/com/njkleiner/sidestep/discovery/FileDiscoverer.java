package com.njkleiner.sidestep.discovery;

import java.io.File;

import com.njkleiner.sidestep.OperatingSystem;

/**
 * A {@link FileDiscoverer} is responsible for locating files that are part of
 * a JDK installation. The precise location of these files may vary by operating system.
 */
public interface FileDiscoverer {

    /**
     * Locate a file based on the current {@link OperatingSystem}.
     *
     * @param system The current {@link OperatingSystem}.
     *
     * @return The {@link File} found, or `null`.
     */
    public File discover(OperatingSystem system);

}

package com.njkleiner.sidestep.discovery;

import java.io.File;
import java.nio.file.Paths;

import com.njkleiner.sidestep.OperatingSystem;

public class AttachLibraryDiscoverer implements FileDiscoverer {

    @Override
    public File discover(OperatingSystem system) {
        String home = System.getenv("JAVA_HOME");

        if (home == null) {
            return null;
        }

        File result = null;

        if (system == OperatingSystem.DARWIN) {
            result = Paths.get(home, "jre", "lib", "libattach.dylib").toFile();
        }

        return (result == null || !result.exists()) ? null : result;
    }

}

package com.njkleiner.sidestep.discovery;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import com.njkleiner.sidestep.util.OperatingSystem;

public class AttachLibraryDiscoverer implements FileDiscoverer {

    @Override
    public Optional<File> discover(OperatingSystem system) {
        String home = System.getenv("JAVA_HOME");

        if (home == null) {
            return null;
        }

        File result = null;

        if (system == OperatingSystem.DARWIN) {
            result = Paths.get(home, "jre", "lib", "libattach.dylib").toFile();
        }

        return (result == null || !result.exists()) ? Optional.empty() : Optional.of(result);
    }

}

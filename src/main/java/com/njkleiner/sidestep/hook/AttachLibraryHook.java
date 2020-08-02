package com.njkleiner.sidestep.hook;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Optional;

import com.njkleiner.sidestep.util.OperatingSystem;
import com.njkleiner.sidestep.discovery.AttachLibraryDiscoverer;
import com.njkleiner.sidestep.discovery.FileDiscoverer;

public class AttachLibraryHook implements SystemFileHook {

    private final OperatingSystem system;
    private final FileDiscoverer discoverer;

    public AttachLibraryHook() {
        this.system = OperatingSystem.detect();
        this.discoverer = new AttachLibraryDiscoverer();
    }

    @Override
    public void install() throws Exception {
        Optional<File> attach = this.discoverer.discover(this.system);

        if (!attach.isPresent()) {
            throw new UnsupportedOperationException("libattach not found");
        }

        File file = attach.get();

        // Add library to `java.library.path`
        String previous = System.getProperty("java.library.path");

        if (previous == null) {
            System.setProperty("java.library.path", file.getParentFile().getAbsolutePath());
        } else {
            System.setProperty(
                "java.library.path",
                String.format("%s:%s", previous, file.getParentFile().getAbsolutePath())
            );
        }

        // Force class loader to reload `sys_paths` by setting it to `null`
        // This is required because `java.library.path` cannot be modified at runtime.
        Field paths = ClassLoader.class.getDeclaredField("sys_paths");
        paths.setAccessible(true);
        paths.set(null, null);

        // Load the system library
        System.load(file.getAbsolutePath());
    }

}

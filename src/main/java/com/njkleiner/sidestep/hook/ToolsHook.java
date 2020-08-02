package com.njkleiner.sidestep.hook;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;

import com.njkleiner.sidestep.util.OperatingSystem;
import com.njkleiner.sidestep.discovery.FileDiscoverer;
import com.njkleiner.sidestep.discovery.ToolsDiscoverer;

public class ToolsHook implements SystemFileHook {

    private final OperatingSystem system;
    private final FileDiscoverer discoverer;

    public ToolsHook() {
        this.system = OperatingSystem.detect();
        this.discoverer = new ToolsDiscoverer();
    }

    @Override
    public void install() throws Exception {
        Optional<File> tools = this.discoverer.discover(this.system);

        if (!tools.isPresent()) {
            throw new UnsupportedOperationException("tools.jar not found");
        }

        File file = tools.get();

        //TODO: Add support for the default class loader not being an instance of `URLClassLoader`.

        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addURL.setAccessible(true);
        addURL.invoke(loader, file.toURI().toURL());
    }

}

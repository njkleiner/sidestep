package com.njkleiner.sidestep.hook;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import com.njkleiner.sidestep.OperatingSystem;
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
        File tools = this.discoverer.discover(this.system);

        if (tools == null) {
            throw new UnsupportedOperationException("tools.jar not found");
        }

        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addURL.setAccessible(true);
        addURL.invoke(loader, tools.toURI().toURL());
    }

}

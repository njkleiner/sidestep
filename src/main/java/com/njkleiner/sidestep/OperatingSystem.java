package com.njkleiner.sidestep;

/**
 * An {@link OperatingSystem} represents one of the three known operating systems,
 * Microsoft Windows, MacOS, and Linux.
 */
public enum OperatingSystem {

    WINDOWS,
    DARWIN,
    LINUX;

    /**
     * Detect the operating system at runtime using the `os.name` system property.
     *
     * @return The detected {@link OperatingSystem}, or `null`.
     */
    public static OperatingSystem detect() {
        String flavor = System.getProperty("os.name").toLowerCase();

        if (flavor.contains("win")) {
            return OperatingSystem.WINDOWS;
        } else if (flavor.contains("mac") || flavor.contains("darwin")) {
            return OperatingSystem.DARWIN;
        } else if (flavor.contains("nix") || flavor.contains("nux")) {
            return OperatingSystem.LINUX;
        }

        return null;
    }

}

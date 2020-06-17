# sidestep

Dynamically inject Java agents at runtime.

## Motivation

Sidestep is an (incomplete) example of how to use the [Java Attach API](https://docs.oracle.com/javase/7/docs/jdk/api/attach/spec/com/sun/tools/attach/package-summary.html). It's meant to be a proof of concept only.

When I initially stumbled upon the Attach API many years ago, I found it hard to understand, mostly due to poor documentation.
I wish someone had made something like this that would've helped me understand it better.

## How It Works

Sidestep makes use of the Java Attach API, which allows you to inject Java agents into running virtual machines.

For this to work, two things are required:

* The `tools.jar` file needs to be inside your classpath (it contains the API code for the Java Attach API).
* The `libattach.{dll,dylib,so}` file needs to be loaded.

While you could simply distribute these files with your project, I prefer to load them dynamically from the operating system's JDK installation. (NOTE: Sidestep currently only has support for MacOS)

Loading these files, however, is non-trivial. Adding a JAR file to the classpath at runtime is somewhat easy if you can safely assume that the default class loader is an instance of `URLClassLoader`, but loading the `libattach` file is more tricky. The problem here is that system libraries can only be loaded if they're inside the `java.library.home` path. Simply overriding the corresponding system property is not enough, since it's cached inside the default class loader's `sys_paths` field. You have to set this field to `null` using reflection magic to force the class loader to reload it (from the system property you've overriden).

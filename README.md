# How to install

Following https://amagovpt.github.io/docs.autenticacao.gov/manual_sdk.html, make sure you have the SDK installed for your OS. Mine is MacOS, so I went to https://www.autenticacao.gov.pt/web/guest/cc-aplicacao, downloaded and installed the software.

After installation, according to the docs, is MacOS the SDK file for Java is in `/usr/local/lib/pteid_jni`. And indeed it is, in that folder there is a `pteidlibj.jar` file.

Now I have to include this JAR in the maven project. Maven provides a utility to install this 3rd party JARs (installing means placing the JAR in our local repository). They document that step [here](https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html).

So, I did:

        mvn install:install-file \
        -Dfile=/usr/local/lib/pteid_jni/pteidlibj.jar \
        -DgroupId=pt.gov.cartaodecidadao \
        -DartifactId=pteidlibj \
        -Dversion=3.7.0 \
        -Dpackaging=jar \
        -DgeneratePom=true

I am not completly sure about the groupId and artifactId, though.

After doing this, I imported the module like so:

        <dependency>
            <groupId>pt.gov.cartaodecidadao</groupId>
            <artifactId>pteidlibj</artifactId>
            <version>3.7.0</version>
        </dependency>

And required the module in the `module-info.java`:

    module hov {
        ...
        requires pteidlibj;
        ...
    }

Then, I created a sample class (`CardReader.java`) that represents the card reader and its functionalities, following [the documentation](https://amagovpt.github.io/docs.autenticacao.gov/manual_sdk.html#inicializa%C3%A7%C3%A3o--finaliza%C3%A7%C3%A3o-do-sdk).

I instanciated that class in the primary controller (just to test), `PrimaryController.java`.

# What happens

When I run the javafx app (using the command `mvn javafx:run`), a window happears. When I click on the button "Switch to Secondary View", that triggers the method `switchToSecondary` declared in `PrimaryController.java`, where the card reader class is instanciated. This causes an exception. The complete stacktrace is below:

    ❯ mvn javafx:run
    [INFO] Scanning for projects...
    [INFO] 
    [INFO] ------------------------------< hov:hov >-------------------------------
    [INFO] Building hov 1.0-SNAPSHOT
    [INFO] --------------------------------[ jar ]---------------------------------
    [INFO] 
    [INFO] >>> javafx-maven-plugin:0.0.8:run (default-cli) > process-classes @ hov >>>
    [INFO] 
    [INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ hov ---
    [INFO] Using 'UTF-8' encoding to copy filtered resources.
    [INFO] Copying 2 resources
    [INFO] 
    [INFO] --- maven-compiler-plugin:3.8.0:compile (default-compile) @ hov ---
    [WARNING] ********************************************************************************************************************
    [WARNING] * Required filename-based automodules detected. Please don't publish this project to a public artifact repository! *
    [WARNING] ********************************************************************************************************************
    [INFO] Nothing to compile - all classes are up to date
    [INFO] 
    [INFO] <<< javafx-maven-plugin:0.0.8:run (default-cli) < process-classes @ hov <<<
    [INFO] 
    [INFO] 
    [INFO] --- javafx-maven-plugin:0.0.8:run (default-cli) @ hov ---
    [WARNING] Required filename-based automodules detected. Please don't publish this project to a public artifact repository!
    Native code library failed to load. 
    java.lang.UnsatisfiedLinkError: no pteidlibj in java.library.path: /Users/amserra/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.
    [ERROR] Command execution failed.
    org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
        at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:404)
        at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:166)
        at org.openjfx.JavaFXBaseMojo.executeCommandLine (JavaFXBaseMojo.java:567)
        at org.openjfx.JavaFXBaseMojo.executeCommandLine (JavaFXBaseMojo.java:434)
        at org.openjfx.JavaFXRunMojo.execute (JavaFXRunMojo.java:105)
        at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo (DefaultBuildPluginManager.java:137)
        at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:210)
        at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:156)
        at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:148)
        at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:117)
        at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:81)
        at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build (SingleThreadedBuilder.java:56)
        at org.apache.maven.lifecycle.internal.LifecycleStarter.execute (LifecycleStarter.java:128)
        at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:305)
        at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:192)
        at org.apache.maven.DefaultMaven.execute (DefaultMaven.java:105)
        at org.apache.maven.cli.MavenCli.execute (MavenCli.java:972)
        at org.apache.maven.cli.MavenCli.doMain (MavenCli.java:293)
        at org.apache.maven.cli.MavenCli.main (MavenCli.java:196)
        at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
        at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:77)
        at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke (Method.java:568)
        at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced (Launcher.java:282)
        at org.codehaus.plexus.classworlds.launcher.Launcher.launch (Launcher.java:225)
        at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode (Launcher.java:406)
        at org.codehaus.plexus.classworlds.launcher.Launcher.main (Launcher.java:347)
    org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
            at org.apache.commons.exec.DefaultExecutor.executeInternal(DefaultExecutor.java:404)
            at org.apache.commons.exec.DefaultExecutor.execute(DefaultExecutor.java:166)
            at org.openjfx.JavaFXBaseMojo.executeCommandLine(JavaFXBaseMojo.java:567)
            at org.openjfx.JavaFXBaseMojo.executeCommandLine(JavaFXBaseMojo.java:434)
            at org.openjfx.JavaFXRunMojo.execute(JavaFXRunMojo.java:105)
            at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo(DefaultBuildPluginManager.java:137)
            at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:210)
            at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:156)
            at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:148)
            at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:117)
            at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:81)
            at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build(SingleThreadedBuilder.java:56)
            at org.apache.maven.lifecycle.internal.LifecycleStarter.execute(LifecycleStarter.java:128)
            at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:305)
            at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:192)
            at org.apache.maven.DefaultMaven.execute(DefaultMaven.java:105)
            at org.apache.maven.cli.MavenCli.execute(MavenCli.java:972)
            at org.apache.maven.cli.MavenCli.doMain(MavenCli.java:293)
            at org.apache.maven.cli.MavenCli.main(MavenCli.java:196)
            at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
            at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
            at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
            at java.base/java.lang.reflect.Method.invoke(Method.java:568)
            at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced(Launcher.java:282)
            at org.codehaus.plexus.classworlds.launcher.Launcher.launch(Launcher.java:225)
            at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode(Launcher.java:406)
            at org.codehaus.plexus.classworlds.launcher.Launcher.main(Launcher.java:347)
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD FAILURE
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  3.138 s
    [INFO] Finished at: 2022-06-16T19:55:41+01:00
    [INFO] ------------------------------------------------------------------------
    [ERROR] Failed to execute goal org.openjfx:javafx-maven-plugin:0.0.8:run (default-cli) on project hov: Error: Command execution failed. Process exited with an error: 1 (Exit value: 1) -> [Help 1]
    [ERROR] 
    [ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
    [ERROR] Re-run Maven using the -X switch to enable full debug logging.
    [ERROR] 
    [ERROR] For more information about the errors and possible solutions, please read the following articles:
    [ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException
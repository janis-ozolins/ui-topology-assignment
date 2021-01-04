package network.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion

class UIBootPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.bootRun {
            javaLauncher = project.javaToolchains.launcherFor {
                languageVersion = JavaLanguageVersion.of(11)
            }
        }
    }
}

package network.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion

class UIJavaPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.repositories {
            mavenCentral()
        }

        project.java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(11))
            }
        }

        project.test {
            useJUnitPlatform()
        }

        project.ext {
            libraries = [
                    "test": [
                            "org.junit.jupiter:junit-jupiter:5.7.0",
                            "com.google.truth:truth:1.1",
                            "com.google.truth.extensions:truth-java8-extension:1.1"
                    ],
                    "testRuntime": [
                            "org.junit.jupiter:junit-jupiter-engine:5.7.0"
                    ]
            ]
        }
    }
}

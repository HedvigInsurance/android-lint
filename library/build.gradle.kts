plugins {
    id("com.android.library")
    id("com.jfrog.bintray") version "1.8.5"
    `maven-publish`
}

group = "com.hedvig.android"
version = file("../VERSION").readText().trim()

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
    }
}

dependencies {
    lintPublish(project(":checks"))
}

publishing {
    publications {
        create<MavenPublication>("hedvig-android-lint") {
            pom.withXml {
                groupId = project.group.toString()
                artifactId = "android-lint"
                artifact("$buildDir/outputs/aar/library-release.aar")
                version = project.version.toString()
            }
        }
    }
}

tasks {
    bintray {
        user = System.getenv("BINTRAY_USER")
        key = System.getenv("BINTRAY_KEY")
        setConfigurations("archives")
        publish = true
        pkg.apply {
            repo = "hedvig-java"
            name = "hedvig-android-lint"
            userOrg = "hedvig"
            vcsUrl = "https://github.com/HedvigInsurance/android-lint"
            setLicenses("AGPL-V3")
            version = VersionConfig().apply {
                name = project.version.toString()
            }
        }
    }
}

plugins {
    kotlin("jvm") version "1.3.61"
    id("com.jfrog.bintray") version "1.8.5"
}

group = "org.example"
version = file("VERSION").readText().trim()

repositories {
    google()
    jcenter()
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("com.android.tools.lint:lint-api:26.6.2")
    testImplementation("com.android.tools.lint:lint:26.6.2")
    testImplementation("com.android.tools.lint:lint-tests:26.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
    }
    jar {
        manifest {
            attributes("Lint-Registry-v2" to "com.hedvig.lint.HedvigIssueRegistry")
        }
    }
    bintray {
        user = System.getenv("BINTRAY_USER")
        key = System.getenv("BINTRAY_KEY")
        setConfigurations("archives")
        pkg = PackageConfig().apply {
            repo = "bintray-hedvig-hedvig-java"
            name = "hedvig-android-lint"
            userOrg = "hedvig"
            vcsUrl = "https://github.com/HedvigInsurance/android-lint"
            setLicenses("AGPL-3.0-only")
            version = VersionConfig().apply {
                name = project.version.toString()
            }
        }
    }
}

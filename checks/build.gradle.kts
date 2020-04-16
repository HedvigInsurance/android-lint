plugins {
    kotlin("jvm")
}

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
}

import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.tasks.JacocoReport

plugins {
    jacoco
}

jacoco {
    toolVersion = "0.8.5"
}

val fileGenerated = listOf(
    "android/**/*.*",
    "**/R.class",
    "**/R$*.class",
    "**/*$ViewBinder*.*",
    "**/*$InjectAdapter*.*",
    "**/*Injector*.*",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*_ViewBinding*.*",
    "**/*_Factory*.*",
    "**/app/ui/screens/**/*DiffCallback*.*",
    "**/*Test*.*",
    // navigation component
    "**/*FragmentArgs*",
    "**/*FragmentDirections*",
    "**/FragmentNavArgsLazy.kt",
    "**/*Fragment*navArgs*",
    "**/screens/common/StartFragment.*",
    // kotlin enum Creator
    "**/*$Creator*"
)

val packagesExcluded = listOf(
    "co/nimblehq/extensions/app/**",
    "com/bumptech/glide"
)

val fileFilter = fileGenerated + packagesExcluded

tasks.register<JacocoReport>("jacocoTestReport") {
    group = "Reporting"
    description = "Generate Jacoco coverage reports for Debug build"

    dependsOn(":app:testDebugUnitTest")
    dependsOn(":common-ktx:testDebugUnitTest")

    classDirectories.setFrom(
        fileTree("$rootDir/app/build/intermediates/javac/stagingDebug/classes") {
            exclude(fileFilter)
        },
        fileTree("$rootDir/common-ktx/build/intermediates/javac/stagingDebug/classes") {
            exclude(fileFilter)
        },
        fileTree("$rootDir/app/build/tmp/kotlin-classes/stagingDebug") {
            exclude(fileFilter)
        },
        fileTree("$rootDir/common-ktx/build/tmp/kotlin-classes/stagingDebug") {
            exclude(fileFilter)
        }
    )

    sourceDirectories.setFrom(
        files(
            "$rootDir/app/src/main/java",
            "$rootDir/common-ktx/src/main/java"
        )
    )

    executionData.setFrom(
        fileTree(rootDir) {
            include(
                "app/build/jacoco/testStagingDebugUnitTest.exec",
                "common-ktx/build/jacoco/testStagingDebugUnitTest.exec"
            )
        }
    )
}

tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed")
    }
}

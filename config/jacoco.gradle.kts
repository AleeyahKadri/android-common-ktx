apply(plugin = "jacoco")

jacoco {
    toolVersion = "0.8.5"
}

val fileGenerated = listOf(
    "android/**/*.*",
    "**/R.class",
    "**/R\$*.class",
    "**/*\$ViewBinder*.*",
    "**/*\$InjectAdapter*.*",
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
    "**/*\$Creator*"
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
        fileTree(mapOf("dir" to "${project.rootDir}/app/build/intermediates/javac/stagingDebug/classes", "excludes" to fileFilter)) +
        fileTree(mapOf("dir" to "${project.rootDir}/common-ktx/build/intermediates/javac/stagingDebug/classes", "excludes" to fileFilter)) +
        fileTree(mapOf("dir" to "${project.rootDir}/app/build/tmp/kotlin-classes/stagingDebug", "excludes" to fileFilter)) +
        fileTree(mapOf("dir" to "${project.rootDir}/common-ktx/build/tmp/kotlin-classes/stagingDebug", "excludes" to fileFilter))
    )

    sourceDirectories.setFrom(files(
        "${project.rootDir}/app/src/main/java",
        "${project.rootDir}/common-ktx/src/main/java"
    ))

    executionData.setFrom(fileTree(mapOf(
        "dir" to project.rootDir,
        "includes" to listOf(
            "app/build/jacoco/testStagingDebugUnitTest.exec",
            "common-ktx/build/jacoco/testStagingDebugUnitTest.exec"
        )
    )))
}

tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed")
    }
}

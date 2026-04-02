plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    config = files("$rootDir/config/detekt.yml")
}

dependencies {
    val detektVersion = rootProject.extra["detekt_version"] as String
    add("detektPlugins", "io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}

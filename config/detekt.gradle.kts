apply(plugin = "io.gitlab.arturbosch.detekt")

detekt {
    config = files("$rootDir/config/detekt.yml")
}

val detektVersion = rootProject.extra["detekt_version"] as String

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}

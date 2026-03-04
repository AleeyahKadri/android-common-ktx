apply(plugin = "io.gitlab.arturbosch.detekt")

val detektVersion: String by rootProject.extra

configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
    config = files("$rootDir/config/detekt.yml")
}

dependencies {
    add("detektPlugins", "io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}

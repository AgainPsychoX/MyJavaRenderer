
plugins {
	application
}

repositories {
	mavenCentral()
}

dependencies {
	testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")

	implementation("com.google.guava:guava:30.1.1-jre")
}

application {
	mainClass.set("MyRenderer.App")
}

tasks.named<Test>("test") {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
}

tasks.jar {
	manifest.attributes["Main-Class"] = "MyRenderer.App"
	val dependencies = configurations
		.runtimeClasspath
		.get()
		.map(::zipTree)
	from(dependencies)
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

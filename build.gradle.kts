plugins {
	java
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.21"
}

group = "org.packman"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}
tasks.jar {
	manifest {
		attributes(
				"Main-Class" to "org.packman.server.ServerApplication"
		)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {

	//data
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.4")
	//boot
	implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
	//liquibase
	implementation("org.liquibase:liquibase-core")
	//postgres
	runtimeOnly("org.postgresql:postgresql")
	//lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	// Logging
	implementation("org.apache.logging.log4j:log4j-core:2.20.0")
	implementation("org.apache.logging.log4j:log4j-api:2.20.0")
	implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
kotlin {
	jvmToolchain(17)
}
tasks.register<Jar>("fatJar") {
	manifest {
		attributes("Main-Class" to "org.packman.server.ServerApplication")
	}
	archiveClassifier.set("fat")

	from(sourceSets.main.get().output)

	dependsOn(configurations.runtimeClasspath)
	from({
		configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
	})
}
tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}

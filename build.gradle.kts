plugins {

    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.21"
    java

}

group "org.example"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.7.0")

    implementation("com.sparkjava:spark-core:2.9.3")

    val kotlinSerializationVer = "1.3.3"
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.6.21")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVer")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    val coroutinesVersion = "1.6.1"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    val slf4jVersion = "1.7.36"
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")

    val loggingVersion = "2.1.23"
    implementation("io.github.microutils:kotlin-logging-jvm:$loggingVersion")

    implementation("com.github.kevinvandenbreemen:kevin-common:1.0.6.1000")

    testImplementation("org.amshove.kluent:kluent:1.68")

}

val fatJar = task("FatJar", type = Jar::class) {

    val jarName = "pointServer.jar"

    archiveName = jarName
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    manifest {
        attributes["Main-Class"] = "com.jared.point.main.MainKt"

    }
    from(configurations.runtimeClasspath.get().map {
        if(it.isDirectory) it else zipTree(it)
    })
    with(tasks.jar.get() as CopySpec)

    copy {
        from("build/libs/$jarName")
        into("./")
    }
    println("Built and copied $jarName")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
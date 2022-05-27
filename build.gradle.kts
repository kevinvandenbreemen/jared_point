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
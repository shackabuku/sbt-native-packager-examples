name := "JDKPackagerPlugin Example"
version := "0.1.0"
organization := "com.foo.bar"

libraryDependencies ++= Seq(
    "com.typesafe" % "config" % "1.2.1"
)

mainClass in Compile := Some("ExampleApp")

enablePlugins(JDKPackagerPlugin)

// This becomes the Start Menu subdirectory for the windows installers.
maintainer := "Previously Owned Cats, Inc."
packageSummary := "JDKPackagerPlugin example package thingy"
packageDescription := "A test package using Oracle's JDK bundled javapackager tool."


lazy val iconGlob = sys.props("os.name").toLowerCase match {
  case os if os.contains("mac") ⇒ "*.icns"
  case os if os.contains("win") ⇒ "*.ico"
  case _ ⇒ "*.png"
}

jdkAppIcon :=  (sourceDirectory.value ** iconGlob).getPaths.headOption.map(file)
jdkPackagerType := "installer"

// this is to help ubuntu 15.10
antPackagerTasks in JDKPackager := (antPackagerTasks in JDKPackager).value orElse {
  for {
    f <- Some(file("/usr/lib/jvm/java-8-oracle/lib/ant-javafx.jar")) if f.exists()
  } yield f
}

fork := true


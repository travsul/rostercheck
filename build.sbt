scalaVersion := "2.12.8"

val http4sVersion = "0.18.11"
val circeVersion = "0.9.3"
val doobieVersion = "0.5.2"

scalacOptions := Seq("-feature","-unchecked","-deprecation","-encoding","utf8")

libraryDependencies ++= {
  Seq(
    "org.http4s"        %% "http4s-dsl"          % http4sVersion,
    "org.http4s"        %% "http4s-blaze-server" % http4sVersion,
    "org.http4s"        %% "http4s-blaze-client" % http4sVersion,
    "org.http4s"        %% "http4s-circe"        % http4sVersion,
    "io.circe"          %% "circe-literal"       % circeVersion,
    "io.circe"          %% "circe-core"          % circeVersion,
    "io.circe"          %% "circe-generic"       % circeVersion,
    "io.circe"          %% "circe-parser"        % circeVersion,
    "org.tpolecat"      %% "doobie-core"         % doobieVersion,
    "org.tpolecat"      %% "doobie-postgres"     % doobieVersion,
    "org.tpolecat"      %% "doobie-scalatest"    % doobieVersion % "test",
    "com.typesafe"      % "config"               % "1.3.2"
  )
}

scalacOptions += "-Ypartial-unification"
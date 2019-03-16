package com.rostercheck

import doobie._
import cats.effect._

object Database {
  val postgresConfig: PostgresConfig = Config.gatherPostgresConfig

  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    postgresConfig.host,
    postgresConfig.user,
    postgresConfig.password.value)

}
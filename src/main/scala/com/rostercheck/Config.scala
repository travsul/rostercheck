package com.rostercheck

import com.typesafe.config._

case class PostgresConfig(host: String, user: String, password: Password)

object Config {
 private[this] val conf: Config = ConfigFactory.load()

 def gatherPostgresConfig: PostgresConfig = {
    PostgresConfig(
      host = conf.getString("sql.host"),
      user = conf.getString("sql.user"),
      password = Password(conf.getString("sql.pass"))
    )
 }
}
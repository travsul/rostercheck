package com.rostercheck
package guild

import com.rostercheck.Database._
import cats.effect._
import doobie.implicits._
import java.util.UUID
import doobie.postgres.implicits._


case class GuildData(id: UUID, name: String, realm: String, locale: String)

object GuildData {
  def get(id: UUID): IO[GuildData] = {
    sql"""
      select id, guild_name, realm, locale from guilds where id=$id
    """.query[GuildData]
      .unique
      .transact(Database.xa)
  }

  def get(name: String): IO[GuildData] = {
    sql"""
      select id, guild_name, realm, locale from guilds where name=$name
    """.query[GuildData]
      .unique
      .transact(Database.xa)
  }
}
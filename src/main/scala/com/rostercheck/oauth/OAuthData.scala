package com.rostercheck
package oauth

import com.rostercheck.Database._
import cats.effect._
import doobie.implicits._
import java.util.UUID
import doobie.postgres.implicits._

case class ApiKey(id: UUID, client_id: String, client_secret: String)

object OAuthData {
  def get(id: UUID): IO[ApiKey] = {
    sql"""
      select id, client_id, client_secret from api_keys where id=$id
    """.query[ApiKey]
       .unique
       .transact(Database.xa)
  }
}
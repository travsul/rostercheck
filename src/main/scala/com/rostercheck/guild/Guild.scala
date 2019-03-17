package com.rostercheck
package guild

import java.util.UUID

import com.rostercheck.oauth.OAuthToken
import io.circe.parser.decode
import org.http4s.client._
import org.http4s.dsl.io._
import cats.effect.IO
import org.http4s._

object Guild {

  def getGuild(clientIO: IO[Client[IO]], id: UUID, oauth: IO[OAuthToken]): IO[Guild] = {
    for {
      hClient <- clientIO
      data    <- GuildData.get(id)
      token   <- oauth
      uri     <- getRequestUri(data.realm, data.name, data.locale, token.access_token)
      resp    <- hClient.expect[String](Request[IO](method = Method.GET, uri = uri, headers = Headers(Header("Accept", "application/json"))))
      decoded <- IO.fromEither(decode[Guild](resp))
    } yield {
      decoded
    }
  }

  private[this] def getRequestUri(realm: String, name: String, locale: String, accessToken: String): IO[Uri] = {
    val location: String = locale match {
      case "en-US" => "us"
      case _       => "us"
    }
    val uriOrError = Uri.fromString(s"https://$location.api.blizzard.com/wow/guild/$realm/$name?fields=members&locale=$locale&access_token=$accessToken".replace(" ","%20"))
    IO.fromEither(uriOrError)
  }
}


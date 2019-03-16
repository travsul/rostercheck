package com.rostercheck.oauth

import java.util.UUID

import cats.effect._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.parser.decode
import org.http4s._
import org.http4s.headers._
import org.http4s.client.Client

case class OAuthToken(access_token: String, token_type: String, expires_in: Int) {
  val expires_at: Long = (System.currentTimeMillis / 1000) + expires_in.toLong
}

object OAuthToken {
  implicit val d: Decoder[OAuthToken] = deriveDecoder[OAuthToken]
}

object OAuth {
  def getToken(clientIO: IO[Client[IO]], id: UUID): IO[OAuthToken] = {
    val oauthdata = OAuthData.get(id)
    getToken(clientIO, oauthdata)
  }

  def getToken(clientIO: IO[Client[IO]], apikeyIO: IO[ApiKey]): IO[OAuthToken] = {
    val tokenUri = Uri.uri("https://us.battle.net/oauth/token")

    for {
      client     <- clientIO
      apikey     <- apikeyIO
      headers    =  Headers(Authorization(BasicCredentials(apikey.client_id, apikey.client_secret)), Header("Accept", "*/*"))
      p          <- Request[IO](method = Method.POST, uri = tokenUri, headers = headers).withBody(UrlForm(("grant_type", "client_credentials")))
      response   <- client.expect[String](p)
      decoded    <- IO.fromEither(decode[OAuthToken](response))
    } yield {
      decoded
    }
  }

  def updateToken(currentTokenIO: IO[OAuthToken], clientIO: IO[Client[IO]], apikey: IO[ApiKey]): IO[OAuthToken] = {
    currentTokenIO.flatMap { currentToken =>
      if (currentToken.expires_at < (System.currentTimeMillis() / 1000)) currentTokenIO
      else getToken(clientIO, apikey)
    }
  }
}

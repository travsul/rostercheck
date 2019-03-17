package com.rostercheck

import java.util.UUID

import cats.effect._
import com.rostercheck.guild.Guild.getGuild
import com.rostercheck.oauth.OAuth._
import fs2.StreamApp.ExitCode
import fs2._
import org.http4s._
import org.http4s.client._
import org.http4s.client.blaze.Http1Client
import org.http4s.dsl.io._
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext.Implicits.global

object Server extends StreamApp[IO] {

  def returnGuild(httpClient: IO[Client[IO]], id: String, level: Int): IO[String] = {
    val idUuid = UUID.fromString(id)
    val oauthToken = getToken(httpClient, idUuid)
    getGuild(httpClient, idUuid, oauthToken).map(_.members.filter(_.character.level >= level).map(_.character.name).mkString("\n"))
  }

  val httpClient: IO[Client[IO]] = Http1Client[IO]()

  val helloWorldService: HttpService[IO] = HttpService[IO] {
    case GET -> Root / "guild" / someId =>
      returnGuild(httpClient, someId, 0).flatMap(Ok(_))
    case GET -> Root / "guild" / someId / IntVar(level) =>
      returnGuild(httpClient, someId, level).flatMap(Ok(_))
    case GET -> Root / "ping" =>
      Ok("pong")
  }

  def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] =
    BlazeBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .mountService(helloWorldService, "/")
      .serve
}
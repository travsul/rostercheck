package com.rostercheck

import io.circe.Decoder

case class Guild(
  lastModified: Long,
  name: String,
  realm: String,
  battlegroup: String,
  level: Int,
  side: Int,
  achievementPoints: Int,
  members: List[Members],
  emblem: Emblem
)

object Guild {
  implicit val d: Decoder[Guild] = {
    Decoder.forProduct9(
      "lastModified",
      "name",
      "realm",
      "battlegroup",
      "level",
      "side",
      "achievementPoints",
      "members",
      "emblem")(Guild.apply)
  }
}

case class Members(
  character: Character,
  rank: Int
)

object Members {
  implicit val d: Decoder[Members] = {
    Decoder.forProduct2(
      "character",
      "rank")(Members.apply)
  }
}

case class Character(
  name: String,
  realm: Option[String],
  battlegroup: String,
  cclass: Int,
  race: Int,
  gender: Int,
  level: Int,
  achievementPoints: Int,
  thumbnail: String,
  spec: Option[Spec],
  guild: String,
  guildRealm: String,
  lastModified: Int
)

object Character {
  implicit val d: Decoder[Character] = {
    Decoder.forProduct13(
    "name",
    "realm",
    "battlegroup",
    "class",
    "race",
    "gender",
    "level",
    "achievementPoints",
    "thumbnail",
    "spec",
    "guild",
    "guildRealm",
    "lastModified")(Character.apply)
  }
}

case class Spec(
  name: String,
  role: String,
  backgroundImage: String,
  icon: String,
  description: String,
  order: Int
)

object Spec {
  implicit val d: Decoder[Spec] = {
    Decoder.forProduct6(
      "name",
      "role",
      "backgroundImage",
      "icon",
      "description",
      "order")(Spec.apply)
  }
}

case class Emblem(
  icon: Int,
  iconColor: String,
  iconColorId: Int,
  border: Int,
  borderColor: String,
  borderColorId: Int,
  backgroundColor: String,
  backgroundColorId: Int
)

object Emblem {
  implicit val d: Decoder[Emblem] = {
    Decoder.forProduct8(
        "icon",
        "iconColor",
        "iconColorId",
        "border",
        "borderColor",
        "borderColorId",
        "backgroundColor",
        "backgroundColorId")(Emblem.apply)
  }
}

case class Password(value: String) extends AnyVal
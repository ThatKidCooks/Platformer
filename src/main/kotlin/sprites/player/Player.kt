package site.thatkid.sprites.player

import site.thatkid.sprites.Entity
import site.thatkid.sprites.SpriteTypes

object Player : Entity() {
    override val name: String
        get() = "Player"
    override val id: String
        get() = "player"


    override val width: Int
        get() = 30
    override val height: Int
        get() = 30


    override val type: SpriteTypes
        get() = SpriteTypes.PLAYER

    override val gravity: Boolean
        get() = true

    val velocity = mutableMapOf<Int, Int>()
}
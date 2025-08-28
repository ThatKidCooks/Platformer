package site.thatkid.level

import site.thatkid.sprites.Entity

abstract class Level {

    abstract val name: String
    abstract val level: Int

    abstract val entities: MutableMap<Entity, MutableMap<Int, Int>>

    abstract val player: Entity
    open val playerVelocity: MutableMap<Int, Int> = mutableMapOf(0 to 0, 1 to 0)
}
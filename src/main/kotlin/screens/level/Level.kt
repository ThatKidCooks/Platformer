package site.thatkid.screens.level

import site.thatkid.screens.Screen
import site.thatkid.sprites.Entity

abstract class Level : Screen() {

    abstract val name: String
    abstract val level: Int

    abstract val entities: MutableMap<Entity, MutableMap<Int, Int>>

    abstract val player: Entity
    open val playerVelocity: MutableMap<Int, Int> = mutableMapOf(0 to 0, 1 to 0)

    abstract val gravity: Int
    abstract val groundFriction: Double
    abstract val airDrag: Double
    abstract val jumpVelocity: Int
}
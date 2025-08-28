package site.thatkid.level.levels

import site.thatkid.level.Level
import site.thatkid.sprites.Entity
import site.thatkid.sprites.player.Player

object Level1 : Level() {
    override val name: String
        get() = "Level 1"
    override val level: Int
        get() = 1
    override val entities: MutableMap<Entity, MutableMap<Int, Int>> = mutableMapOf()

    override val player: Entity
        get() = Player
    
    override val playerVelocity: MutableMap<Int, Int> = mutableMapOf(0 to 0, 1 to 0)

    init {
        // Initialize level with player position
        entities[Player] = mutableMapOf(0 to 100, 1 to 150) // x=100, y=150

        // TODO: Add more enemies
    }
}
package site.thatkid.screens

import site.thatkid.screens.level.Level
import site.thatkid.sprites.Entity
import site.thatkid.sprites.gui.PlayButton
import site.thatkid.sprites.player.Player

class HomeScreen : Level() {

    override val name: String
        get() = "HomeScreen"
    override val level: Int
        get() = TODO("Not yet implemented")

    override val entities: MutableMap<Entity, MutableMap<Int, Int>> = mutableMapOf()

    override val player: Entity
        get() = Player

    private val playButton = PlayButton(300, 50, "main")

    init {
        entities[playButton] = mutableMapOf(0 to 400, 1 to 400)
    }

    override val gravity = 1 // Gravity constant
    override val groundFriction = 0.85 // Ground friction (multiplier)
    override val airDrag = 0.7 // Air drag (multiplier)
    override val jumpVelocity = -18 // Jump velocity (negative is up)
}
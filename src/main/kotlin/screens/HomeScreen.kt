package site.thatkid.screens

import site.thatkid.sprites.Entity
import site.thatkid.sprites.gui.PlayButton

class HomeScreen {

    val name = "HomeScreen"

    val entities: MutableMap<Entity, MutableMap<Int, Int>> = mutableMapOf()

    private val playButton = PlayButton(300, 50, "main")

    init {
        entities[playButton] = mutableMapOf(0 to 400, 1 to 400)
    }
}
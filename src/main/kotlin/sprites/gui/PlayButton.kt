package site.thatkid.sprites.gui

import site.thatkid.sprites.Entity
import site.thatkid.sprites.SpriteTypes

class PlayButton(private val playButtonWidth: Int, private val playButtonHeight: Int, private val playButtonID: String) : Entity() {
    override val name: String
        get() = "Play"
    override val id: String
        get() = playButtonID


    override val width: Int
        get() = playButtonWidth
    override val height: Int
        get() = playButtonHeight


    override val type: SpriteTypes
        get() = SpriteTypes.BUTTON


    override val gravity: Boolean
        get() = false
}
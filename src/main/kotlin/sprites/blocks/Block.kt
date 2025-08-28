package site.thatkid.sprites.blocks

import site.thatkid.sprites.Entity
import site.thatkid.sprites.SpriteTypes

class Block(private val blockWidth: Int, private val blockHeight: Int, private val blockId: String) : Entity() {
    override val name: String
        get() = "Block"
    override val id: String
        get() = blockId

    override val width: Int
        get() = blockWidth
    override val height: Int
        get() = blockHeight

    override val type: SpriteTypes
        get() = SpriteTypes.BLOCK

    override val gravity: Boolean
        get() = false
}
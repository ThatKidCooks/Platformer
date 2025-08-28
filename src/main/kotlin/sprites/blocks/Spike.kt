package site.thatkid.sprites.blocks

import site.thatkid.sprites.Entity
import site.thatkid.sprites.SpriteTypes

class Spike(private val spikeWidth: Int, private val spikeHeight: Int, private val spikeID: String, ) : Entity() {
    override val name: String
        get() = "Spike"
    override val id: String
        get() = spikeID
    override val width: Int
        get() = spikeWidth
    override val height: Int
        get() = spikeHeight
    override val type: SpriteTypes
        get() = SpriteTypes.SPIKE
    override val gravity: Boolean
        get() = false
}
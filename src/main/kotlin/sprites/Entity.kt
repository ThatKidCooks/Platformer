package site.thatkid.sprites

abstract class Entity {

    abstract val name: String
    abstract val id: String

    abstract val width: Int
    abstract val height: Int

    abstract val type: SpriteTypes
    abstract val gravity: Boolean
}
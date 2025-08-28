package site.thatkid.level.levels

import site.thatkid.level.Level
import site.thatkid.sprites.Entity
import site.thatkid.sprites.blocks.Block
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

    // Level blocks
    private val platform1 = Block(200, 40, "platform1")
    private val platform2 = Block(150, 40, "platform2") 
    private val platform3 = Block(100, 40, "platform3")
    private val groundBlock1 = Block(300, 40, "ground1")
    private val groundBlock2 = Block(300, 40, "ground2")

    init {
        // Initialize level with player position (start higher up)
        entities[Player] = mutableMapOf(0 to 50, 1 to 100) // x=50, y=100

        // Add ground platforms
        entities[groundBlock1] = mutableMapOf(0 to 100, 1 to 500) // x=100, y=500
        entities[groundBlock2] = mutableMapOf(0 to 450, 1 to 500) // x=450, y=500
        
        // Add floating platforms
        entities[platform1] = mutableMapOf(0 to 200, 1 to 400) // x=200, y=400
        entities[platform2] = mutableMapOf(0 to 500, 1 to 300) // x=500, y=300
        entities[platform3] = mutableMapOf(0 to 350, 1 to 200) // x=350, y=200
    }
}
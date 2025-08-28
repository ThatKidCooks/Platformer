package site.thatkid.level.levels

import site.thatkid.level.Level
import site.thatkid.sprites.Entity
import site.thatkid.sprites.blocks.Block
import site.thatkid.sprites.blocks.Spike
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
    private val platform4 = Block(200, 40, "platform4")
    private val groundBlock1 = Block(300, 40, "ground1")
    private val groundBlock2 = Block(500, 40, "ground2")

    private val spike = Spike(2000, 20 , "spike1")

    init {
        // Initialize level with player position (start on a platform)
        entities[Player] = mutableMapOf(0 to 50, 1 to 50) // x=50, y=50

        // Add ground platforms at reasonable screen positions
        entities[groundBlock1] = mutableMapOf(0 to 0, 1 to 550) // x=0, y=550 (bottom left)
        entities[groundBlock2] = mutableMapOf(0 to 400, 1 to 550) // x=400, y=550 (bottom right)
        
        // Add floating platforms for jumping
        entities[platform1] = mutableMapOf(0 to 150, 1 to 450) // x=150, y=450
        entities[platform2] = mutableMapOf(0 to 450, 1 to 350) // x=450, y=350
        entities[platform3] = mutableMapOf(0 to 300, 1 to 250) // x=300, y=250
        entities[platform4] = mutableMapOf(0 to 600, 1 to 150) // x=450, y=250

        // Add a spike obstacle
        entities[spike] = mutableMapOf(0 to 0, 1 to 1035) // x=0, y=1035 (on the ground)
    }

    override val gravity = 1 // Gravity constant
    override val groundFriction = 0.85 // Ground friction (multiplier)
    override val airDrag = 0.7 // Air drag (multiplier)
    override val jumpVelocity = -18 // Jump velocity (negative is up)
}
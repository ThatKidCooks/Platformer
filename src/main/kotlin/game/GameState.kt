package game

import site.thatkid.Screen
import site.thatkid.game.KeyListener
import site.thatkid.screens.ScreenManager
import site.thatkid.screens.level.Level
import site.thatkid.screens.level.levels.Level1
import site.thatkid.sprites.Entity
import site.thatkid.sprites.SpriteTypes


class GameState(private val keyListener: KeyListener, private val screen: Screen, private val screenManager: ScreenManager) {

    // Current level being played
    private val currentLevel: Level = screenManager.getCurrentLevel()
    private val pressedKeys = mutableSetOf<String>()

    private val moveSpeed = 4

    // sprite positions mapped by entity and their (x,y) coordinates
    val blocks: MutableMap<Entity, MutableMap<Int, Int>> = mutableMapOf()
    val entities: MutableMap<Entity, MutableMap<Int, Int>> = mutableMapOf()

    private var playerPos: MutableMap<Int, Int> = mutableMapOf()

    private val gravity = currentLevel.gravity // Gravity constant
    private val groundFriction = currentLevel.groundFriction // Ground friction (multiplier)
    private val airDrag = currentLevel.airDrag // Air drag (multiplier)
    private val jumpVelocity = currentLevel.jumpVelocity // Jump velocity (negative is up)

    init {
        loadLevel(currentLevel)
    }

    private fun loadLevel(level: Level) {
        // Clear existing entities
        entities.clear()
        blocks.clear()

        // Load entities from level and separate blocks from other entities
        level.entities.forEach { (entity, position) ->
            if (entity.type == SpriteTypes.BLOCK) {
                blocks[entity] = position
            } else {
                entities[entity] = position
            }
        }

        // Add player to entities if not already there
        if (!entities.contains(level.player)) {
            // Use default position if not specified in level
            entities[level.player] = mutableMapOf(0 to 50, 1 to 50) // x=50, y=50
        }

        playerPos.clear()

        // Reset player velocity
        level.playerVelocity[0] = 0
        level.playerVelocity[1] = 0
    }

    fun handleInput(action: String, add: Boolean) {
        if (action == "ESCAPE" && add) {
            println("Escape pressed - resetting level")
            loadLevel(currentLevel)
            return
        }
        if (add) {
            pressedKeys.add(action)
        } else {
            pressedKeys.remove(action)
        }
    }

    fun updatePositions() {
        // do player movement updates
        playerUpdate()
    }

    private fun applyGravityAndFriction(playerVelocity: MutableMap<Int, Int>, isOnGround: Boolean) {
        // Apply gravity to the vertical velocity
        playerVelocity[1] = (playerVelocity[1] ?: 0) + gravity

        // Apply friction or air drag to horizontal velocity based on ground contact
        val currentX = playerVelocity[0] ?: 0
        playerVelocity[0] = if (isOnGround) {
            // Apply ground friction
            (currentX * groundFriction * airDrag).toInt()
        } else {
            // Apply air drag
            (currentX * airDrag).toInt()
        }
    }

    private fun playerUpdate() {
        playerPos = entities[currentLevel.player]!!
        val playerVelocity = currentLevel.playerVelocity

        if (playerPos != null) {
            val isOnGround = isPlayerOnGround(playerPos, playerVelocity)

            addPlayerVelocity(playerVelocity, isOnGround)
            applyGravityAndFriction(playerVelocity, isOnGround)

            val newX = (playerPos[0] ?: 0) + (playerVelocity[0] ?: 0)
            val newY = (playerPos[1] ?: 0) + (playerVelocity[1] ?: 0)

            val (finalX, finalY) = checkCollisions(newX, newY, playerVelocity)
            
            playerPos[0] = finalX
            playerPos[1] = finalY

            if ((playerPos[0] ?: 0) <= 0) {
                playerPos[0] = 0
                playerVelocity[0] = 0
            } else if ((playerPos[0] ?: 0) >= screen.getMaxSize().width - currentLevel.player.width) {
                playerPos[0] = screen.getMaxSize().width - currentLevel.player.width
                playerVelocity[0] = 0
            }

            if ((playerPos[1] ?: 0) <= 0) {
                playerPos[1] = 0
                playerVelocity[1] = 0
            } else if ((playerPos[1] ?: 0) >= screen.getMaxSize().height - currentLevel.player.height) {
                playerPos[1] = screen.getMaxSize().height - currentLevel.player.height
                playerVelocity[1] = 0
            }
        }

        if (death()) {
            println("Player has died - resetting level")
            loadLevel(currentLevel)
        }
    }

    private fun addPlayerVelocity(playerVelocity: MutableMap<Int, Int>, isOnGround: Boolean): MutableMap<Int, Int> {
        val currentX = playerVelocity[0] ?: 0
        val currentY = playerVelocity[1] ?: 0

        var newX = currentX
        var newY = currentY

        // Horizontal movement
        if (pressedKeys.contains("LEFT")) {
            newX -= moveSpeed
        }
        if (pressedKeys.contains("RIGHT")) {
            newX += moveSpeed
        }
        
        // Jumping - only when on ground
        if (pressedKeys.contains("UP") && isOnGround) {
            newY = jumpVelocity
        }

        playerVelocity[0] = newX
        playerVelocity[1] = newY

        return playerVelocity
    }

    private fun isPlayerOnGround(playerPos: MutableMap<Int, Int>, playerVelocity: MutableMap<Int, Int>): Boolean {
        val playerX = playerPos[0] ?: 0
        val playerY = playerPos[1] ?: 0
        val playerWidth = currentLevel.player.width
        val playerHeight = currentLevel.player.height

        // Check if at bottom of screen (ground level)
        if (playerY >= screen.getMaxSize().height - playerHeight) {
            return true
        }

        // Check if standing on any block
        for ((block, blockPos) in blocks) {
            val blockX = blockPos[0] ?: 0
            val blockY = blockPos[1] ?: 0
            
            // Check if player is on top of this block
            if (playerY + playerHeight <= blockY && 
                playerY + playerHeight + Math.abs(playerVelocity[1] ?: 0) + 1 >= blockY &&
                playerX + playerWidth > blockX && 
                playerX < blockX + block.width) {
                return true
            }
        }
        
        return false
    }

    private fun checkCollisions(newX: Int, newY: Int, playerVelocity: MutableMap<Int, Int>): Pair<Int, Int> {
        val playerWidth = currentLevel.player.width
        val playerHeight = currentLevel.player.height
        
        var finalX = newX
        var finalY = newY
        
        // Check collision with blocks
        for ((block, blockPos) in blocks) {
            val blockX = blockPos[0] ?: 0
            val blockY = blockPos[1] ?: 0
            
            // Check if would collide
            if (finalX + playerWidth > blockX && 
                finalX < blockX + block.width &&
                finalY + playerHeight > blockY && 
                finalY < blockY + block.height) {
                
                // Determine collision side and resolve
                val overlapX = Math.min(finalX + playerWidth - blockX, blockX + block.width - finalX)
                val overlapY = Math.min(finalY + playerHeight - blockY, blockY + block.height - finalY)
                
                if (overlapX < overlapY) {
                    // Horizontal collision
                    if (finalX < blockX) {
                        finalX = blockX - playerWidth
                    } else {
                        finalX = blockX + block.width
                    }
                    playerVelocity[0] = 0
                } else {
                    // Vertical collision
                    if (finalY < blockY) {
                        finalY = blockY - playerHeight
                        playerVelocity[1] = 0 // Stop falling when hitting ground
                    } else {
                        finalY = blockY + block.height
                        playerVelocity[1] = 0
                    }
                }
            }
        }
        
        return Pair(finalX, finalY)
    }

    private fun death(): Boolean {
        if (playerPos[0] == null || playerPos[1] == null) {
            return false
        }

        val playerX = playerPos[0] ?: 0
        val playerY = playerPos[1] ?: 0
        val playerWidth = currentLevel.player.width
        val playerHeight = currentLevel.player.height

        // Check if player has fallen below the screen
        if ((playerPos[1] ?: 0) > screen.getMaxSize().height) {
            return true
        }

        if (playerPos[0]!! < 0 || playerPos[0]!! > screen.getMaxSize().width) {
            return true
        }

        // is touching spike?
        for ((block, blockPos) in entities) {
            if (block.type == SpriteTypes.SPIKE) {
                val blockX = blockPos[0] ?: 0
                val blockY = blockPos[1] ?: 0
                val blockWidth = block.width
                val blockHeight = block.height

                if (playerX < blockX + blockWidth &&
                    playerX + playerWidth > blockX &&
                    playerY < blockY + blockHeight &&
                    playerY + playerHeight > blockY) {
                    return true
                }
            }
        }
        return false
    }

    // Get current level
    fun getCurrentLevel() = currentLevel
}
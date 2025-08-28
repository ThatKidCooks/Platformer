package game

import site.thatkid.Screen
import site.thatkid.game.KeyListener
import site.thatkid.level.Level
import site.thatkid.level.levels.Level1
import site.thatkid.sprites.Entity


class GameState(private val keyListener: KeyListener, private val screen: Screen) {

    // Current level being played
    private val currentLevel: Level = Level1
    private val pressedKeys = mutableSetOf<String>()

    private val moveSpeed = 3

    // You can have multiple sprites
    val blocks: MutableMap<Entity, MutableMap<Int, Int>> = mutableMapOf()
    val entities: MutableMap<Entity, MutableMap<Int, Int>> = mutableMapOf()

    private val gravity = 2 // Gravity constant
    private val friction = 1 // Friction constant

    init {
        loadLevel(currentLevel)
        keyListener
    }

    private fun loadLevel(level: Level) {
        // Clear existing entities
        entities.clear()
        blocks.clear()

        // Load entities from level
        entities.putAll(level.entities)

        // Add player to entities if not already there
        if (!entities.contains(level.player)) {
            entities[level.player] = mutableMapOf(0 to 100, 0 to 150) // x=100, y=150
        }
    }

    fun handleInput(action: String, add: Boolean) {
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

    private fun applyGravityAndFriction(playerVelocity: MutableMap<Int, Int>) {
        // Apply gravity to the vertical velocity
        playerVelocity[1] = (playerVelocity[1] ?: 0) + gravity

        // Apply friction to the horizontal velocity
        playerVelocity[0] = ((playerVelocity[0] ?: 0) / friction)
    }

    private fun playerUpdate() {
        val playerPos = entities[currentLevel.player]
        val playerVelocity = currentLevel.playerVelocity

        if (playerPos != null) {

            addPlayerVelocity(playerVelocity)
            applyGravityAndFriction(playerVelocity)

            playerPos[0] = playerVelocity[0]!!
            playerPos[1] = playerVelocity[1]!!

            // Keep player within bounds and stop movement at edges
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
    }

    private fun addPlayerVelocity(playerVelocity: MutableMap<Int, Int>): MutableMap<Int, Int> {

        var currentX = playerVelocity[0] ?: 0
        var currentY = playerVelocity[1] ?: 0

        // Move based on pressed keys
        if (pressedKeys.contains("LEFT")) {
            currentX -= moveSpeed
        }
        if (pressedKeys.contains("RIGHT")) {
            currentX += moveSpeed
        }
        if (pressedKeys.contains("UP")) {
            currentY -= moveSpeed * 2
        }

        playerVelocity[0] = currentX
        playerVelocity[1] = currentY

        return playerVelocity
    }

    // Get current level
    fun getCurrentLevel(): Level = currentLevel
}
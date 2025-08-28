package site.thatkid.game

import game.GameState
import site.thatkid.sprites.SpriteTypes
import java.awt.Color
import java.awt.Graphics
import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JPanel

class GamePanel(private val gameState: GameState) : JPanel() {
    // Fix: Load image from resources correctly
    private val playerSprite: Image? = try {
        ImageIcon(this::class.java.getResource("/textures/img.png")).image
    } catch (e: Exception) {
        println("Failed to load player sprite: ${e.message}")
        null
    }

    override fun paintComponent(graphics: Graphics) {
        super.paintComponent(graphics) // Clear the panel

        // Draw level name
        graphics.color = Color.BLACK
        graphics.drawString("Level: ${gameState.getCurrentLevel().name}", 10, 20)
        
        // Draw all entities from GameState
        gameState.entities.forEach { (entity, position) ->
            val x = position[0] ?: 0
            val y = position[1] ?: 0
            
            when (entity.type) {
                SpriteTypes.PLAYER -> {
                    // Check if sprite loaded successfully
                    if (playerSprite != null) {
                        graphics.drawImage(playerSprite, x, y, entity.width, entity.height, null)
                    } else {
                        // Fallback to colored rectangle if image fails
                        graphics.color = Color.BLUE
                        graphics.fillRect(x, y, entity.width, entity.height)
                        // Debug text
                        graphics.color = Color.WHITE
                        graphics.drawString("IMG FAILED", x, y + 15)
                    }
                }
                else -> {
                    // Draw other entities as colored rectangles
                    graphics.color = Color.RED
                    graphics.fillRect(x, y, entity.width, entity.height)
                }
            }
        }
        
        // Draw blocks if any
        gameState.blocks.forEach { (entity, position) ->
            val x = position[0] ?: 0
            val y = position[1] ?: 0
            
            graphics.color = Color.GRAY
            graphics.fillRect(x, y, entity.width, entity.height)
        }
    }
}
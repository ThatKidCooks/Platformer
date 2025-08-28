package site.thatkid.game

import game.GameState
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class KeyListener() : KeyListener {

    lateinit var gameState: GameState

    override fun keyTyped(e: KeyEvent?) {
        // TODO: Maybe chat at some point
    }

    override fun keyPressed(event: KeyEvent?) {
        when (event?.keyCode) {
            KeyEvent.VK_RIGHT -> {
                gameState.handleInput("RIGHT", true)
            }
            KeyEvent.VK_D -> {
                gameState.handleInput("RIGHT", true)
            }

            KeyEvent.VK_LEFT -> {
                gameState.handleInput("LEFT", true)
            }
            KeyEvent.VK_A -> {
                gameState.handleInput("LEFT", true)
            }

            KeyEvent.VK_UP -> {
                gameState.handleInput("UP", true)
            }
            KeyEvent.VK_W -> {
                gameState.handleInput("UP", true)
            }
            KeyEvent.VK_SPACE -> {
                gameState.handleInput("UP", true)
            }
        }
    }

    override fun keyReleased(event: KeyEvent?) {
        when (event?.keyCode) {
            KeyEvent.VK_RIGHT -> {
                gameState.handleInput("RIGHT", false)
            }
            KeyEvent.VK_D -> {
                gameState.handleInput("RIGHT", false)
            }

            KeyEvent.VK_LEFT -> {
                gameState.handleInput("LEFT", false)
            }
            KeyEvent.VK_A -> {
                gameState.handleInput("LEFT", false)
            }

            KeyEvent.VK_UP -> {
                gameState.handleInput("UP", false)
            }
            KeyEvent.VK_W -> {
                gameState.handleInput("UP", false)
            }
            KeyEvent.VK_SPACE -> {
                gameState.handleInput("UP", false)
            }
        }
    }
}
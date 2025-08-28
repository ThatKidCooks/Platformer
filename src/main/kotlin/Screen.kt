package site.thatkid

import game.GameState
import site.thatkid.game.GamePanel
import site.thatkid.game.KeyListener
import java.awt.Dimension
import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment
import javax.swing.JFrame
import javax.swing.Timer

class Screen : JFrame() {

    private val keyListener = KeyListener()

    private val gameState = GameState(keyListener, this)

    private val gamePanel = GamePanel(gameState)

    init {
        title = "Platformer"
        val graphicsDevice: GraphicsDevice = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .defaultScreenDevice
        add(gamePanel)
        graphicsDevice.fullScreenWindow = this
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        isResizable = true
        isVisible = true

        keyListener.gameState = gameState
        
        // Add the key listener to this JFrame
        addKeyListener(keyListener)
        // Make sure the frame can receive key events
        isFocusable = true
        requestFocusInWindow()

        startGameLoop()
    }

    fun getMaxSize(): Dimension {
        return size
    }

    private fun startGameLoop() {
        val timer = Timer(16) {
            gameState.updatePositions()
            gamePanel.repaint()
        }
        timer.start()

    }
}
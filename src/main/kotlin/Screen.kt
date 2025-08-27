package site.thatkid

import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.WindowConstants

class Screen : JFrame() {

    init {
        title = "Platformer"
        size = Dimension(800, 600)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        isResizable = true
        isVisible = true
    }
}
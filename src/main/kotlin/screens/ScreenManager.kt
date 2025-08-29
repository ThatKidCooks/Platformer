package site.thatkid.screens

import com.google.gson.GsonBuilder
import site.thatkid.screens.level.Level
import site.thatkid.screens.level.levels.Level1
import java.io.File

class ScreenManager {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    data class SaveData(
        val levelsUnlocked: Int = 0,
    )

    private val homeScreenToggled: Boolean = true
    private val levelScreenToggled: Boolean = false
    private val level: Level = Level1

    private var levelsUnlocked: Int = 1
    
    private val saveFolder = "saves"
    private val saveFileName = "game_save.json"

    private fun getSaveDirectory(): File {
        val saveDir = File(saveFolder)
        if (!saveDir.exists()) {
            saveDir.mkdirs()
        }
        return saveDir
    }

    fun load() {
        load(File(getSaveDirectory(), saveFileName))
    }

    /**
     * Save game data to the default save file
     */
    fun save() {
        save(File(getSaveDirectory(), saveFileName))
    }

    private fun load(file: File) {
        // Ensure parent directory exists if the file has a parent
        file.parentFile?.let { parent ->
            if (!parent.exists()) {
                parent.mkdirs()
            }
        }
        
        if (!file.exists()) return
        
        val json = file.readText()
        val saveData = gson.fromJson(json, SaveData::class.java)

        levelsUnlocked = saveData.levelsUnlocked
    }

    private fun save(file: File) {
        // Ensure parent directory exists if the file has a parent
        file.parentFile?.let { parent ->
            if (!parent.exists()) {
                parent.mkdirs()
            }
        }
        
        val saveData = SaveData(levelsUnlocked)
        val json = gson.toJson(saveData, SaveData::class.java)

        file.writeText(json)
    }

    fun getHomeScreenToggled(): Boolean = homeScreenToggled

    fun getLevelScreenToggled(): Boolean = levelScreenToggled

    fun getLevelsUnlocked(): Int = levelsUnlocked

    fun getCurrentLevel(): Level = level
}
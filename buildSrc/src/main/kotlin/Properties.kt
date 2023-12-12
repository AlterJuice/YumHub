
private const val PROPS_FOLDER_NAME = "properties"

/** A simple structure for storing info about properties files */
interface PropsFile {
    val fileName: String
    val filePath: String
}

private fun PropsFile.defaultPropertiesFilePath(): String {
    return "./${PROPS_FOLDER_NAME}/${fileName}"
}

/** An object which stores the information about YumHub properties file with the
 * given file name, path and fields it should contain, e.g google key
 * */
object YumHubProperties: PropsFile {
    override val fileName: String = "YumHub.properties"
    override val filePath: String get() = defaultPropertiesFilePath()


    const val RELEASE_GOOGLE_KEY = "RELEASE_GOOGLE_KEY"
    const val DEBUG_GOOGLE_KEY = "DEBUG_GOOGLE_KEY"
    const val PIXABAY_API_KEY = "PIXABAY_API_KEY"
    const val OPEN_AI_API_KEY = "OPEN_AI_API_KEY"
    const val NUTRITIONIX_APP_KEY = "NUTRITIONIX_APP_KEY"
    const val NUTRITIONIX_APP_ID = "NUTRITIONIX_APP_ID"
}


/** An object which stores the information about Signing properties file with the
 * given file name, path and fields it should contain, e.g passwords
 * */
object SigningProperties: PropsFile {
    override val fileName: String = "Signing.properties"
    override val filePath: String get() = defaultPropertiesFilePath()

    const val RELEASE_STORE_PASSWORD = "RELEASE_STORE_PASSWORD"
    const val RELEASE_STORE_KEY_0_NAME = "RELEASE_STORE_KEY_0_NAME"
    const val RELEASE_STORE_KEY_0_PASSWORD = "RELEASE_STORE_KEY_0_PASSWORD"

}
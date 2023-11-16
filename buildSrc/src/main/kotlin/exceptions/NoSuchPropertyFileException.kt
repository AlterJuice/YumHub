package exceptions

class NoSuchPropertyFileException(
    missedFileName: String,
    correspondingPath: String,
) : Exception("Please, provide the missed property file '${missedFileName}' to the corresponding path '${correspondingPath}'.")
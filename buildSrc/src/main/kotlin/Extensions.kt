import exceptions.NoSuchPropertyFileException
import exceptions.NoSuchPropertyFieldException
import java.io.File
import java.util.Properties


fun checkPropertyFileExistsOrThrow(file: File, onFileExists: (File) -> Unit) {
    if (file.exists()){
        onFileExists.invoke(file)
    } else {
        throw NoSuchPropertyFileException(file.name, file.path)
    }
}

fun Properties.getFieldValueOrThrow(fieldName: String, onFieldExists: (fieldName: String, value: Any) -> Unit) {
    if (this.containsKey(fieldName)){
        onFieldExists.invoke(fieldName, this[fieldName]!!)
    } else {
        throw NoSuchPropertyFieldException(fieldName)
    }
}

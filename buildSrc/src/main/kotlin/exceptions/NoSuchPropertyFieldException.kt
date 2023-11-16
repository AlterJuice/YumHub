package exceptions

class NoSuchPropertyFieldException(
    missedPropertyField: String
) : Exception("Please, provide the missed property '${missedPropertyField}' to the corresponding properties file.")
package mpsm.api

import mpsm.error.Error

/**
 * Message holds a message meta data for API responses
 */
class Message {
    String errorName     = ''           //High level message
    def    errorDetails  = ''           //Stack trace or field error where applicable
    int    specificHttpErrorCode  = 500 //http error code (default to server error)
    String errorMessage  = 'Please report problems to the system administrator and include the provided date/time: ' + new Date() + '.'

    static Message get400Message(String details = null) {
        def message = defaultMessagesForError(Error.INVALID_REQUEST)
        message.errorDetails = details
        message
    }

    static Message get404Message() {
        defaultMessagesForError Error.RECORD_NOT_FOUND
    }

    static Message get500Message() {
        defaultMessagesForError Error.GENERAL_SERVER_ERROR
    }

    static Message getErrorMessage(String errorName, String errorMessage='') {
        new Message(errorName:errorName, errorMessage:errorMessage)
    }

    Message withErrorName(String errorName) {
        this.errorName = errorName
        this
    }

    Message withErrorCode(int httpErrorCode) {
        this.specificHttpErrorCode = httpErrorCode
        this
    }

    Message withErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage
        this
    }

    Message withErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails
        this
    }

    private static Message defaultMessagesForError(Error error) {
        new Message(
                specificHttpErrorCode:error.code,
                errorName:error.name,
                errorMessage:error.message,
        )
    }
}

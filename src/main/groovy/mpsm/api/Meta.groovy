package mpsm.api

/**
 * Meta represents the meta data block in an API response
 */
class Meta {
    long status             = 200
    List<Message> messages  = []
    Pagination pagination   = new Pagination()
    List<Filter> filters    = null

    static Meta userMessage(String userMessage) {
        Meta meta = new Meta()
        meta.messages << Message.getUserMessage(userMessage)
        this
    }

    def addUserMessage(String userMessage) {
        messages << Message.getUserMessage(userMessage)
        this
    }

    def addMessage(Message message) {
        this.messages << message
        this
    }

    def setMessage(Message message) {
        this.messages = [message]
        this
    }

    def autoFill(params, int dataSize) {
        status = params.int('status') ?: status
        if (status != 200) {
            params.total = 0
        }
        if (params.filters) {
            filters = []
            params.filters.each { filter ->
                filters << new Filter(type:filter.type, value:filter.value, count:filter.count)
            }
        }
        pagination.autoFill(params, dataSize)
    }

    def generateMessagesBlock() {
        List<Filter> messageList = []
        messages.each { message ->
            messageList << [
                    errorName:message.errorName,
                    errorDetails:message.errorDetails,
                    errorMessage:message.errorMessage,
                    specificHttpErrorCode:message.specificHttpErrorCode,
            ]
        }
        messageList
    }
}

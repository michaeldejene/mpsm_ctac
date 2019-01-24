package mpsm.api

/**
 * ApiResponse is a holder and utility class representing the API response returned to the client
 * as a ReST Response.
 */
class ApiResponse {
    Meta meta = new Meta()
    Collection results = []

    //200s----------------------------
    static ApiResponse get200Response(Collection results = []) {
        println('KEY' + results)
        ApiResponse response = new ApiResponse(results:results)
        response
    }
    static ApiResponse get200ResponseCustomUserMessage(String userMessage, Collection results) {
        ApiResponse response = new ApiResponse(results:results)
        response.meta.addUserMessage(userMessage)
        response
    }

    //400s ------------------------
    static ApiResponse get400Response(String details = null) {
        ApiResponse response = new ApiResponse()
        response.meta.status = 400
        response.meta.setMessage(Message.get400Message(details))
        response
    }

    static ApiResponse get400NotFoundResponse() {
        default400ForMessage(Message.get404Message())
    }

    //500s ----------------
    static ApiResponse get500Response() {
        ApiResponse response = new ApiResponse()
        response.meta.status = 500
        response
    }

    private static ApiResponse default400ForMessage(Message message) {
        ApiResponse response = new ApiResponse()
        response.meta.status = 400
        response.meta.setMessage(message)
        response
    }

    ApiResponse autoFill(params) {
        meta.autoFill(params, results?.size() ?: 0)
        this
    }

    Map generateMetaBlock() {
        [
                status:meta.status,
                messages:meta.generateMessagesBlock(),
                pagination:meta.pagination.generatePaginationBlock(),
                filters:meta.filters,
        ]
    }
}

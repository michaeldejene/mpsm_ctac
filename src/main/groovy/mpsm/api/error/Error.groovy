package mpsm.error

/**
 * Error is a holder for system wide error response codes
 */
enum Error {
    GENERAL_SERVER_ERROR('Generic Server Error', 500,
            'The server experienced an un-handled error while processing the ' +
                    "request. Time: ${new Date()}."
    ),

    INVALID_REQUEST('Request was invalid', 400,
            'The request was invalid because the required parameters were not provided'
    ),

    RECORD_NOT_FOUND('Record Not Found', 404,
            'The requested record(s) could not be found.'
    ),

    NOT_AUTHORIZED('Not authorized for resource', 403,
            'The requested operation is not authorized for this resource. Check that your API keys are correct and up to date.'
    )

    Error(String name, int code, String message) {
        this.name = name
        this.code = code
        this.message = message
    }

    String name
    int code
    String message
}

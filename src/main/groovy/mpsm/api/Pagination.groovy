package mpsm.api

import grails.util.Holders

/**
 * Pagination represents the pagination block of an API response
 */
class Pagination {
    int max = 1
    int offset = 0
    int count = 0
    int total = 0
    String sort = 'id'
    String currentUrl = null
    String nextUrl = null
    String previousUrl = null
    String order = null

    def autoFill(params, int dataSize) {
        String serverUrl = grailsServerUrl
        params.max = params.max ?: 20
        if (params.maxOverride) {
            max = params.int('maxOverride')
        } else {
            max = params.id ? 1 : params.int('max')
        }
        offset = Math.max(params.offset ? params.int('offset') : 0, 0)
        //offset unless negative, then offset = 0
        int nextOffset = offset + max
        int lastOffset = offset - max > 0 ? offset - max : 0
        if (params.countOverRide) {
            count = params.int('countOverRide')
        } else {
            count = dataSize
        }
        total = params.int('total', 1)
        sort = params.sort ?: sort                                                          //default to 'id'
        order = params.order ?: order

        //If data size is 1, then we don't need next or previous.
        if (params.customResourcePath) {
            previousUrl = offset == 0 ? null : "${serverUrl}${params.customResourcePath}" + getQueryString(params, lastOffset)
            currentUrl = "${serverUrl}${params.customResourcePath}" + getQueryString(params, offset)
            nextUrl = total > 1 && nextOffset < total ? "${serverUrl}${params.customResourcePath}" + getQueryString(params, nextOffset) : null
        } else {
            String action = ''
            if (params.action != 'index') {
                action = "/${params.action}"
            }
            previousUrl = offset == 0 ? null : "${serverUrl}/${params.controller}${action}" + getQueryString(params, lastOffset)
            currentUrl = "${serverUrl}/${params.controller}${action}" + getQueryString(params, offset)
            nextUrl = total > 1 && nextOffset < total ? "${serverUrl}/${params.controller}${action}" + getQueryString(params, nextOffset) : null
        }
    }

    //Build the default query portion of the string
    private String getQueryString(params, offset) {
        "?offset=${offset}&max=${max}&sort=${sort}${order ? "&order=${order}" : ''}${getAdditionalParams(params)}"
    }

    //Get a string of additional params (custom ones) supplied by the original request
    private String getAdditionalParams(params) {
        String query = '&'
        params.each { key, value ->
            def val
            if (!skip(key)) {
                if (key == 'format' && !value) {
                    val = 'json'
                }
                query += "${key}=${val ?: value}&"
            }
        }
        if (query.size() == 1) {
            return ''
        }
        query[0..(-1 - 1)]
    }

    //Which params should be skipped when appending custom params to the end?
    private boolean skip(String param) {
        def paramsToSkip = [
                'sort',
                'order',
                'max',
                'offset',
                'callback',
                'action',
                'controller',
                'total',
                'format',
                'customResourcePath',
                'filters',
        ]
        param in paramsToSkip
    }

    String toString() {
        "      - max:${max}\n" +
                "      - offset:${offset}\n" +
                "      - count:${count}\n" +
                "      - total:${total}\n" +
                "      - sort:${sort}\n" +
                "      - order:${order}\n" +
                "      - currentUrl:${currentUrl}\n" +
                "      - nextUrl:${nextUrl}\n" +
                "      - previousUrl:${previousUrl}\n"
    }

    def generatePaginationBlock() {
        [
                count:count,
                max:max,
                offset:offset,
                sort:sort,
                order:order,
                total:total,
                currentUrl:currentUrl,
                nextUrl:nextUrl,
                previousUrl:previousUrl,
        ]
    }

    def getGrailsServerUrl() {
        Holders.config.grails.serverURL
    }
}

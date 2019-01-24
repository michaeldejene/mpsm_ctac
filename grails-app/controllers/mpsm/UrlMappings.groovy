package mpsm

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        group "/doctorsList", {
            "(.$format)?"(controller:'doctorsList', action:'index')

            "/issues(.$format)?"(controller:'doctorsList', action:'issues')

        }


        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

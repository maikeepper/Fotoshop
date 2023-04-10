package shop.fotos

class UrlMappings {

    static mappings = {
        // there may be '.'s in the id that do not belong to a format!
        "/fotos/preview/$id"( controller: 'fotos', action: 'preview' )

        "/download/$uuid"( controller: 'purchase', action: 'show' )
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        //"/"(view:"/index")
        "/"( controller: 'fotos', action: 'index' )
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

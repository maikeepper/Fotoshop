package shop.fotos

class UrlMappings {

    static mappings = {
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

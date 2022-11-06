package ea.fotos

class UrlMappings {

    static mappings = {
        "/fotos/preview/$id"( controller: 'fotos', action: 'preview' )
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

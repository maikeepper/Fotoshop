package ea.fotos

import grails.plugin.springsecurity.annotation.Secured

@Secured( 'IS_AUTHENTICATED_FULLY' )
class FotosController {

    def index() {}

    def suche() {
        render view: 'index'
    }

    @Secured( [ 'ROLE_ADMIN', 'ROLE_UPLOADER', 'ROLE_STAFF' ] )
    def upload() {}

//    @Secured( [ 'ROLE_ADMIN', 'ROLE_STAFF' ] )
//    def tag() {}

    @Secured( [ 'ROLE_ADMIN', 'ROLE_UPLOADER', 'ROLE_STAFF' ] )
    def saveMultiple() {
        flash.message = 'Fotos gespeichert!'
        render view: '/upload/multiple', model: [ fotos: [] ]
    }
}

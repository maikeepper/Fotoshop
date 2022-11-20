package shop.fotos.authentication

import grails.plugin.springsecurity.annotation.Secured

@Secured( [ 'ROLE_ADMIN', 'ROLE_STAFF' ] )
class UserController {

    def scaffold = User

//    def index() {}
}

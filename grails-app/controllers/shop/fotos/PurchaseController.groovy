package shop.fotos

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.NOT_FOUND

@Secured( [ 'ROLE_ADMIN' ] )
class PurchaseController {

    def scaffold = Purchase

    @Secured( [ 'ROLE_ADMIN', 'ROLE_STAFF' ] )
    def index() { }

    @Secured( 'IS_AUTHENTICATED_FULLY' )
    def show() {
        final String uuid = ( params.id ?: params.uuid ) as String
        if( !uuid ) {
            respond null
            return
        }

        respond Purchase.findByUuid( uuid )
    }

    def download() {
        final String uuid = ( params.id ?: params.uuid ) as String
        final Purchase purchase = Purchase.findByUuid( uuid )
        if( !uuid || !purchase ) {
            redirect action: 'show'
            return
        }

        println "##########################"
        // TODO Download action zum File Stream bauen
        println "FILE DOWNLOAD OF ${purchase.fotos?.size()} FOTOS (id: ${params.id}, uuid: ${params.uuid})"
        println "##########################"

        redirect action: 'show', id: uuid
    }
}

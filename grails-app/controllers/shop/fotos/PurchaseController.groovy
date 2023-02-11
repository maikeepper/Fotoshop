package shop.fotos

import grails.plugin.springsecurity.annotation.Secured

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

        Purchase purchase = uuid.isNumber() ? Purchase.read( uuid as Long ) : Purchase.findByUuid( uuid )
//        if( params.boolean( 'fixmePaid' ) ) {
//            Purchase.withTransaction {
//                purchase.paid = true
//                purchase.save(failOnError: true, validate: true, flush: true)
//            }
//            respond purchase
//            return
//        }

        if( !purchase ) {
            flash.error = "Bilder nicht gefunden - Download nicht möglich."
        }

        respond purchase
    }

    @Secured( 'IS_AUTHENTICATED_FULLY' )
    def download() {
        final String uuid = ( params.id ?: params.uuid ) as String
        final Purchase purchase = uuid.isNumber() ? Purchase.read( uuid as Long ) : Purchase.findByUuid( uuid )
        if( !uuid || !purchase ) {
            flash.error = "Bilder nicht gefunden - Download nicht möglich."
            redirect action: 'show', id: uuid
            return
        }

        if( !purchase.paid ) {
            flash.error = "Die Bilder wurden noch nicht bezahlt - Download noch nicht erlaubt."
            redirect action: 'show', id: uuid
            return
        }

        println "##########################"
        // TODO Download action zum File Stream bauen
        println "FILE DOWNLOAD OF ${purchase.fotos?.size()} FOTOS (id: ${params.id}, uuid: ${params.uuid})"
        println "##########################"

        redirect action: 'show', id: uuid
    }
}

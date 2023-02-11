package shop.fotos

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured( 'IS_AUTHENTICATED_FULLY' )
class CartController {

    //def scaffold = Purchase nicht nötig, glaub ich

    //def index() {}

    @Transactional
    def checkout() {

        if( params.purchaseUuid ) {
            return [ purchase: Purchase.findByUuid( params.purchaseUuid ) ]
        }

        final Set<Long> selectedFotoIds = params.selectedFotos?.tokenize(',')?.collect {
            try {
                Long.parseLong(it.trim())
            } catch (NumberFormatException nfe) {
            }
        } as Set<Long>

        Purchase purchase = new Purchase(
                uuid: UUID.randomUUID().toString()
        )

        selectedFotoIds?.each { fotoId ->
            try {
                purchase.addToFotos( Foto.read( fotoId ) )
            } catch( Exception e ) {
                log.error( '[' + purchase.uuid + '] Could not find Foto for selected id ' + fotoId )
            }
        }

        purchase = purchase.save( failOnError: true, validate: true, flush: true )

        [ purchase: purchase ]
    }
}

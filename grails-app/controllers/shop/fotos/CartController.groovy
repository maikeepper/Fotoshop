package shop.fotos

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured( 'IS_AUTHENTICATED_FULLY' )
class CartController {

    def scaffold = Purchase

    //def index() {}

    @Transactional
    def checkout() {
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

        purchase = purchase.save()

        // TODO redirect to Paypal Page und die redirected im Erfolgsfall dann zu ->

        flash.message = "Gekaufte Fotos: ${ selectedFotoIds } - ${ purchase.getPriceInCent() / 100 } EUR"
        redirect controller: 'purchase', action: 'show', id: purchase.uuid
    }
}

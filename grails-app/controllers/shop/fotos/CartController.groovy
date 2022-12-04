package shop.fotos

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured( 'IS_AUTHENTICATED_FULLY' )
class CartController {

    def scaffold = Purchase

    //def index() {}

    @Transactional
    def checkout() {
        final List<Long> selectedFotoIds = ( List<Long> ) Eval.me( params.selectedFotos )

        Purchase purchase = new Purchase(
                uuid: UUID.randomUUID().toString()
        )

        selectedFotoIds.each { fotoId ->
            try {
                purchase.addToFotos( Foto.read( fotoId ) )
            } catch( Exception e ) {
                log.error( 'Could not find Foto for selected id ' + fotoId, e )
            }
        }

        purchase = purchase.save()

        // TODO redirect to Paypal Page und die redirected im Erfolgsfall dann zu ->

        flash.message = "Gekaufte Fotos: ${ selectedFotoIds } - ${ purchase.getPriceInCent() / 100 } EUR"
        redirect controller: 'purchase', action: 'show', id: purchase.uuid
    }
}

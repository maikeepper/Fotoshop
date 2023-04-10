package shop.fotos

import grails.plugin.springsecurity.annotation.Secured

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Secured( [ 'ROLE_ADMIN' ] )
class PurchaseController {

    static final DateTimeFormatter BASIC_DATEFORMAT = DateTimeFormatter.ofPattern('yyyyMMdd-HHmmss')

    def scaffold = Purchase

    FileService fileService


    @Secured( [ 'ROLE_ADMIN', 'ROLE_STAFF' ] )
    def index() {
        [ purchaseList: Purchase.list( params ), purchaseCount: Purchase.count() ]
    }

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
            redirect controller: 'cart', action: 'checkout'
        }

        respond purchase
    }

    @Secured( 'IS_AUTHENTICATED_FULLY' )
    def download() {
        final String uuid = ( params.id ?: params.uuid ) as String
        final Purchase purchase = uuid?.isNumber() ? Purchase.read( uuid as Long ) : Purchase.findByUuid( uuid )
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

        final File zipFile = new File( fileService.createPurchaseZip( purchase ) )

        if( !zipFile?.isFile() ) {
            flash.error = "Konnte kein Zip erstellen - Download nicht möglich."
            redirect action: 'show', id: uuid
            return
        }

        response.setHeader( "Content-disposition", "attachment;filename=FotoShop_${LocalDateTime.now().format(BASIC_DATEFORMAT)}.zip" )
        zipFile.withInputStream {
            response.outputStream << it
            response.contentType = 'application/zip'
            response.outputStream.flush()
        }

        null
    }
}

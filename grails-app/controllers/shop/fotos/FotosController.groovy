package shop.fotos

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Secured( 'IS_AUTHENTICATED_FULLY' )
class FotosController {

    def scaffold = Foto

    //def index() {}

    def filter() {
        render view: 'index'
    }

    @Secured( [ 'ROLE_ADMIN', 'ROLE_UPLOADER' ] )
    def upload() {}

    @Secured( [ 'ROLE_ADMIN', 'ROLE_UPLOADER' ] )
    @Transactional
    def saveMultiple() {
        log.debug( "---------------------------------\n$params\n---------------------------------" )

        final List<String> originalFilenames = params.list( 'origFilename' )
        final List<String> thumbnails = params.list( 'thumbnail' )
        final List<String> tagCounts = params.list( 'tagCount' )
        final ArrayList<String> tags = params.list( 'tags' ) as ArrayList
        final List<Foto> fotos = [ originalFilenames, thumbnails, tagCounts ].transpose().collect{
            final Foto foto = new Foto(
                    origFilename: it[ 0 ],
                    thumbnail: it[ 1 ]
            ).save()

            try {
                Integer.parseInt( it[ 2 ]?.toString() ).times {
                    final String tagName = tags.pop()
                    final Tag tag = tagName ? Tag.findOrSaveByName( tagName ) : null
                    if( tag ) {
                        FotoTag.findOrSaveWhere( foto: foto, tag: tag )
                    }
                }
            } catch( Exception e ) {
                log.error( "Error adding Tags to Foto ${ foto.origFilename } from '$tags'.", e )
            }

            foto
        }
        flash.message = "${ fotos.size() } Fotos gespeichert!"

        redirect( action: 'index' )
    }

    @Secured( 'permitAll()' )
    def preview() {
        final Purchase purchase = params.purchaseId ? Purchase.get( params.purchaseId as Long ) : null
        Path filePath
        if( purchase?.paid ) {
            // show orig file for paid purchases
            final Foto foto = params.id?.isNumber() ? Foto.get( params.id as Long ) : Foto.findByThumbnail( params.id?.toString() )
            filePath = Paths.get( FileService.ORIG_DIR_NAME, foto?.origFilename )
        }
        if( !filePath ) {
            // TODO kann man hier eine try-again-later response senden, wenn das Thumbnail noch nicht da sein sollte?
            final String thumbnailFilename = params.id?.toString()
            filePath = Paths.get( FileService.THUMBS_DIR_NAME, thumbnailFilename )
        }

        final byte[] thumbnailBytes = Files.readAllBytes( filePath )
        render( file: thumbnailBytes, contentType: 'image/jpeg' )
    }
}

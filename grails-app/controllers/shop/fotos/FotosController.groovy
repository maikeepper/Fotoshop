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
        // TODO kann man hier eine try-again-later response senden, wenn das Thumbnail noch nicht da sein sollte?
        final String thumbnailFilename = params.id?.toString()
        final Path thumbnailPath = Paths.get( UploadService.THUMBS_DIR_NAME, thumbnailFilename )
        final byte[] thumbnailBytes = Files.readAllBytes( thumbnailPath )
        render( file: thumbnailBytes, contentType: 'image/jpeg' )
    }
}

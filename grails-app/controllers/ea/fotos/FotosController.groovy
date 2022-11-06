package ea.fotos

import grails.plugin.springsecurity.annotation.Secured

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

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
        final List<String> originalFilenames = params.list( 'origFilename' )
        final List<String> thumbnails = params.list( 'thumbnail' )
        final List<Foto> fotos = [ originalFilenames, thumbnails ].transpose().collect{
            new Foto(
                    origFilename: it[ 0 ],
                    thumbnail: it[ 1 ]
            ).save()
        }
        flash.message = "${ fotos.size() } Fotos gespeichert!"

        render view: '/upload/multiple', model: [ fotos: fotos ]
    }

    @Secured( 'permitAll()' )
    def preview() {
        final String thumbnailFilename = params.id?.toString()
        final Path thumbnailPath = Paths.get( UploadService.THUMBS_DIR_NAME, thumbnailFilename )
        final byte[] thumbnailBytes = Files.readAllBytes( thumbnailPath )
        render( file: thumbnailBytes, contentType: 'image/jpeg' )
    }
}

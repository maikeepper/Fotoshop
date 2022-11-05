package ea.fotos

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.Validateable
import groovy.transform.ToString
import org.springframework.web.multipart.MultipartFile

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Secured( [ 'ROLE_ADMIN', 'ROLE_UPLOADER', 'ROLE_STAFF' ] )
class UploadController {

    def multiple( FotoUploadCommand cmd ) {

        if (cmd.hasErrors()) {
            flash.message = cmd.errors.fieldErrors.collect { message(error: it) }.join(', ')
            respond(cmd.errors, model: [cmd: cmd], view: '/fotos/upload')
            return
        }

        // TODO image-hashcode ausrechnen und als unique constraint prop in Domain class Foto aufnehmen?

        for( MultipartFile file : cmd.files ) {
            final Path filePath = Paths.get( 'uploads', file.originalFilename )
            try ( OutputStream os = Files.newOutputStream( filePath ) ) {
                os.write( file.bytes )
            }
        }

        render view: 'multiple', model: [ filenames: cmd.files*.originalFilename.toString() ]
    }
}


@ToString
class FotoUploadCommand implements Validateable {
    List<MultipartFile> files

    static constraints = {
        files  validator: { val, obj ->
            if( val == null || val.empty ) {
                return false
            }

            val.every { file ->
                ['jpeg', 'jpg', 'png'].any { extension -> file.originalFilename?.toLowerCase()?.endsWith( extension ) }
            }
        }
    }
}
package shop.fotos

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.Validateable
import groovy.transform.ToString
import org.springframework.web.multipart.MultipartFile

@Secured( [ 'ROLE_ADMIN', 'ROLE_UPLOADER', 'ROLE_STAFF' ] )
class UploadController {

    UploadService uploadService


    def multiple( FotoUploadCommand cmd ) {

        if (cmd.hasErrors()) {
            flash.message = cmd.errors.fieldErrors.collect { message(error: it) }.join(', ')
            respond(cmd.errors, model: [cmd: cmd], view: '/fotos/upload')
            return
        }

        long tookUpl = System.currentTimeMillis()
        final List<Foto> uploadedFotos = uploadService.uploadFotos( cmd )
        println "Foto upload took ${System.currentTimeMillis() - tookUpl} ms."

        log.info( "Created ${ uploadedFotos?.size() } Fotos from ${ cmd.files?.size() } uploaded files." )
        render view: 'multiple', model: [ fotos: uploadedFotos ]
    }
}


@ToString
class FotoUploadCommand implements Validateable {
    List<MultipartFile> files
    List<String> tags

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
package shop.fotos.authentication

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import grails.web.servlet.mvc.GrailsHttpSession

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

@Secured( [ 'ROLE_ADMIN', 'ROLE_STAFF' ] )
class UserController {

    UserService userService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index( final Integer max ) {
        params.max = Math.min( max ?: 10, 100 )
        respond userService.list( params ), model: [ userCount: userService.count(), sessionId: ( session as GrailsHttpSession ).id ]
    }

    @Secured( [ 'ROLE_ADMIN' ] )
    def show(Long id) {
        respond userService.get(id)
    }

    def create() {
        respond new User(params)
    }

    def save(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            userService.save(user)
        } catch (ValidationException e) {
            respond user.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'Code'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    @Secured( [ 'ROLE_ADMIN' ] )
    def edit(Long id) {
        respond userService.get(id)
    }

    def update( final User user ) {
        if( user == null ) {
            notFound()
            return
        }

        try {
            userService.save( user )
        } catch( ValidationException e ) {
            respond user.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message( code: 'default.updated.message', args: [ message( code: 'user.label', default: 'Code' ), user.id ] )
                redirect user
            }
            '*'{ respond user, [ status: OK ] }
        }
    }

    @Secured( [ 'ROLE_ADMIN' ] )
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        userService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'Code'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        if( request.xhr ) {
            render(
                status: NOT_FOUND,
                message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'Code'), params.id]) as String
            )
            return
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'Code'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

package shop.fotos.authentication

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import grails.web.servlet.mvc.GrailsHttpSession

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.FORBIDDEN
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

@Secured( [ 'ROLE_ADMIN', 'ROLE_STAFF' ] )
@Transactional
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

        if( !user.authorities ) {
            UserRole.findOrSaveWhere(user: user, role: Role.findByAuthority( 'ROLE_USER' ))
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'Code'), user.id])
                redirect action: 'index'
            }
            '*' { redirect action: 'index', [status: CREATED] }
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

        // Not-Admins are not allowed to disable/change Admins
        if( !SpringSecurityUtils.ifAnyGranted( 'ROLE_ADMIN' ) && user.isAdmin() ) {
            if( !user.enabled ) {
                // FIXME @me warum wird das trotzdem gespeichert? Geht das PUT sonst noch wohin??
                user.enabled = true
                userService.save( user )
            }
            render( status: FORBIDDEN, 'Only for Admins' )
            return
        }

        try {
            userService.save( user )
        } catch( ValidationException e ) {
            if( request.xhr ) {
                render( status: BAD_REQUEST, "$e" as String )
                return
            }
            respond user.errors, view:'edit'
            return
        }

        if( SpringSecurityUtils.ifAnyGranted( 'ROLE_ADMIN' ) ) {
            final Set<String> userAuthorities = user.authorities*.authority as Set<String>
            final Set<String> newAuthorities = params.list('authorities') ?: params.list('authorities[]') as Set<String>
            if (newAuthorities && (userAuthorities != newAuthorities)) {
                final Set<String> toRemove = userAuthorities - newAuthorities
                final Set<String> toAdd = newAuthorities - userAuthorities
                toRemove.collect { roleName -> Role.findByAuthority(roleName) }.each { role ->
                    UserRole.findAllByUserAndRole(user, role).each { userRole ->
                        userRole.delete()
                    }
                }
                toAdd.collect { roleName -> Role.findByAuthority( roleName ) }.each { role ->
                    UserRole.findOrSaveWhere(user: user, role: role)
                }
            }
        }

        if( request.xhr ) {
            render status: OK
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

        UserRole.findAllByUser( User.get( id ) ).each {
            it.delete()
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

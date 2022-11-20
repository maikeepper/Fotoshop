package shop.fotos

import grails.plugin.springsecurity.SpringSecurityService
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent
import org.grails.datastore.mapping.engine.event.PreInsertEvent
import org.grails.datastore.mapping.engine.event.PreUpdateEvent
import org.springframework.beans.factory.annotation.Autowired
import grails.events.annotation.gorm.Listener
import groovy.transform.CompileStatic
import shop.fotos.authentication.User

@CompileStatic
class UserPasswordEncoderListener {

    @Autowired
    SpringSecurityService springSecurityService

    @Listener(User)
    void onPreInsertEvent(PreInsertEvent event) {
        encodePasswordForEvent(event)
    }

    @Listener(User)
    void onPreUpdateEvent(PreUpdateEvent event) {
        encodePasswordForEvent(event)
    }

    private void encodePasswordForEvent(AbstractPersistenceEvent event) {
        if (event.entityObject instanceof User) {
            User u = event.entityObject as User
            if (u.password && ((event instanceof  PreInsertEvent) || (event instanceof PreUpdateEvent && u.isDirty('password')))) {
                event.getEntityAccess().setProperty('password', encodePassword(u.password))
            }
        }
    }

    private String encodePassword(String password) {
        //springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
        if( password?.indexOf( '{' ) == 0 ) {
            int endIndex = password?.indexOf( '}' )
            if( endIndex > 0 && endIndex < password.length() ) {
                return "{noop}${password.substring(endIndex + 1)}"
            }
        }
        "{noop}$password"
    }
}

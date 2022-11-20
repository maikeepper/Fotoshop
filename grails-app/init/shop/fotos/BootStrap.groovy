package shop.fotos

import shop.fotos.authentication.Role
import shop.fotos.authentication.User
import shop.fotos.authentication.UserRole

class BootStrap {

    def init = { servletContext ->

        // Create users and roles
        UserRole.withNewTransaction {
            final Role adminRole = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
            final Role uploaderRole = Role.findOrSaveWhere(authority: 'ROLE_UPLOADER')
            final Role staffRole = Role.findOrSaveWhere(authority: 'ROLE_STAFF')
            final Role userRole = Role.findOrSaveWhere(authority: 'ROLE_USER')

            final User admin = User.findOrSaveWhere(username: 'xxx', password: 'xxx')
            //final User admin = User.findOrSaveWhere(username: 'OIDG', password: 'T7R')
            UserRole.findOrSaveWhere(user: admin, role: adminRole)
            final User kita = User.findOrSaveWhere(username: 'DFGR', password: '3GD')
            UserRole.findOrSaveWhere(user: kita, role: staffRole)
            final User kevin = User.findOrSaveWhere(username: 'ZUD6', password: 'PAS')
            UserRole.findOrSaveWhere(user: kevin, role: uploaderRole)
            final User eltern = User.findOrSaveWhere(username: '4EH8', password: 'TDD')
            UserRole.findOrSaveWhere(user: eltern, role: userRole)
        }

        println "Created ${User.count} users and ${Role.count} roles."

        // create default Tags
        Tag.withNewTransaction {
            ['Rote Gruppe', 'Gelbe Gruppe', 'Orangene Gruppe', 'Grüne Gruppe', 'Blaue Gruppe',
             'Klein-Rote Gruppe', 'Klein-Gelbe Gruppe', 'Klein-Orangene Gruppe',
          'Klein-Grüne Gruppe', 'Klein-Blaue Gruppe' ].each { gruppe ->
                Tag.findOrSaveWhere(name: gruppe)
            }
        }

        println "Created ${Tag.count} tags."
    }
    def destroy = {
    }
}

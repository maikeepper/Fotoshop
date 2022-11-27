package shop.fotos

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class FotoServiceSpec extends Specification {

    FotoService fotoService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Foto(...).save(flush: true, failOnError: true)
        //new Foto(...).save(flush: true, failOnError: true)
        //Foto foto = new Foto(...).save(flush: true, failOnError: true)
        //new Foto(...).save(flush: true, failOnError: true)
        //new Foto(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //foto.id
    }

    void "test get"() {
        setupData()

        expect:
        fotoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Foto> fotoList = fotoService.list(max: 2, offset: 2)

        then:
        fotoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        fotoService.count() == 5
    }

    void "test delete"() {
        Long fotoId = setupData()

        expect:
        fotoService.count() == 5

        when:
        fotoService.delete(fotoId)
        sessionFactory.currentSession.flush()

        then:
        fotoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Foto foto = new Foto()
        fotoService.save(foto)

        then:
        foto.id != null
    }
}

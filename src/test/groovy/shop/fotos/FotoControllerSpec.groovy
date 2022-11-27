package shop.fotos

import grails.testing.gorm.DomainUnitTest
import grails.testing.web.controllers.ControllerUnitTest
import grails.validation.ValidationException
import spock.lang.*

class FotoControllerSpec extends Specification implements ControllerUnitTest<FotoController>, DomainUnitTest<Foto> {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {
        given:
        controller.fotoService = Mock(FotoService) {
            1 * list(_) >> []
            1 * count() >> 0
        }

        when:"The index action is executed"
        controller.index()

        then:"The model is correct"
        !model.fotoList
        model.fotoCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
        controller.create()

        then:"The model is correctly created"
        model.foto!= null
    }

    void "Test the save action with a null instance"() {
        when:"Save is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        controller.save(null)

        then:"A 404 error is returned"
        response.redirectedUrl == '/foto/index'
        flash.message != null
    }

    void "Test the save action correctly persists"() {
        given:
        controller.fotoService = Mock(FotoService) {
            1 * save(_ as Foto)
        }

        when:"The save action is executed with a valid instance"
        response.reset()
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        populateValidParams(params)
        def foto = new Foto(params)
        foto.id = 1

        controller.save(foto)

        then:"A redirect is issued to the show action"
        response.redirectedUrl == '/foto/show/1'
        controller.flash.message != null
    }

    void "Test the save action with an invalid instance"() {
        given:
        controller.fotoService = Mock(FotoService) {
            1 * save(_ as Foto) >> { Foto foto ->
                throw new ValidationException("Invalid instance", foto.errors)
            }
        }

        when:"The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def foto = new Foto()
        controller.save(foto)

        then:"The create view is rendered again with the correct model"
        model.foto != null
        view == 'create'
    }

    void "Test the show action with a null id"() {
        given:
        controller.fotoService = Mock(FotoService) {
            1 * get(null) >> null
        }

        when:"The show action is executed with a null domain"
        controller.show(null)

        then:"A 404 error is returned"
        response.status == 404
    }

    void "Test the show action with a valid id"() {
        given:
        controller.fotoService = Mock(FotoService) {
            1 * get(2) >> new Foto()
        }

        when:"A domain instance is passed to the show action"
        controller.show(2)

        then:"A model is populated containing the domain instance"
        model.foto instanceof Foto
    }

    void "Test the edit action with a null id"() {
        given:
        controller.fotoService = Mock(FotoService) {
            1 * get(null) >> null
        }

        when:"The show action is executed with a null domain"
        controller.edit(null)

        then:"A 404 error is returned"
        response.status == 404
    }

    void "Test the edit action with a valid id"() {
        given:
        controller.fotoService = Mock(FotoService) {
            1 * get(2) >> new Foto()
        }

        when:"A domain instance is passed to the show action"
        controller.edit(2)

        then:"A model is populated containing the domain instance"
        model.foto instanceof Foto
    }


    void "Test the update action with a null instance"() {
        when:"Save is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(null)

        then:"A 404 error is returned"
        response.redirectedUrl == '/foto/index'
        flash.message != null
    }

    void "Test the update action correctly persists"() {
        given:
        controller.fotoService = Mock(FotoService) {
            1 * save(_ as Foto)
        }

        when:"The save action is executed with a valid instance"
        response.reset()
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        populateValidParams(params)
        def foto = new Foto(params)
        foto.id = 1

        controller.update(foto)

        then:"A redirect is issued to the show action"
        response.redirectedUrl == '/foto/show/1'
        controller.flash.message != null
    }

    void "Test the update action with an invalid instance"() {
        given:
        controller.fotoService = Mock(FotoService) {
            1 * save(_ as Foto) >> { Foto foto ->
                throw new ValidationException("Invalid instance", foto.errors)
            }
        }

        when:"The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(new Foto())

        then:"The edit view is rendered again with the correct model"
        model.foto != null
        view == 'edit'
    }

    void "Test the delete action with a null instance"() {
        when:"The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then:"A 404 is returned"
        response.redirectedUrl == '/foto/index'
        flash.message != null
    }

    void "Test the delete action with an instance"() {
        given:
        controller.fotoService = Mock(FotoService) {
            1 * delete(2)
        }

        when:"The domain instance is passed to the delete action"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(2)

        then:"The user is redirected to index"
        response.redirectedUrl == '/foto/index'
        flash.message != null
    }
}







package shop.fotos

import grails.gorm.services.Service

@Service(Foto)
interface FotoService {

    Foto get(Serializable id)

    List<Foto> list(Map args)

    Long count()

    void delete(Serializable id)

    Foto save(Foto user)

}
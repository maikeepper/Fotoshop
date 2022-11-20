package shop.fotos

class Tag {

    String name

    static constraints = {
        name    nullable: false, blank: false, unique: true
    }

    @Override
    String toString() {
        name
    }

    Set<Foto> getFotos() {
        (FotoTag.findAllByTag(this) as List<FotoTag>)*.foto as Set<Foto>
    }
}

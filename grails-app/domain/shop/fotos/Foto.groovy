package shop.fotos

class Foto {

    Long id                   // eindeutige Nummer, auch zur Benutzung in einem Auslage-Katalog
    String origFilename       // Dateiname des Original-Fotos auf der Festplatte
    String thumbnail          // Pfad zum Thumbnail für img-Tags

    Set<Tag> tagsToAdd = [] as Set<Tag>        // save tags to add for temporary Fotos (after upload, before save)

    // Preis
    // Image hash -> unique?

    static constraints = {
        origFilename       nullable: false, blank: false
    }

    static transients = [ 'tagsToAdd' ]

    Set<Tag> getTags() {
        if( !this.id ) return Collections.emptySet()
        (FotoTag.findAllByFoto(this) as List<FotoTag>)*.tag as Set<Tag>
    }
}

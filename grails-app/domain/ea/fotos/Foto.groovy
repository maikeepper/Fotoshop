package ea.fotos

class Foto {

    Long id                   // eindeutige Nummer, auch zur Benutzung in einem Auslage-Katalog
    String orgFilename        // Dateiname des Original-Fotos auf der Festplatte
    String thumbnail          // Pfad zum Thumbnail für img-Tags

    // Preis
    // Tags
    // Image hash -> unique?

    static constraints = {
        orgFilename       nullable: false, blank: false
    }
}

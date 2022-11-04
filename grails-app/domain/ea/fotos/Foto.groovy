package ea.fotos

class Foto {

    // id <=> eindeutige Nummer, auch zur Benutzung in einem Auslage-Katalog
    String orgDateiname    // Dateiname des Original-Fotos auf der Festplatte
    String tnName          // Dateiname des Thumbnails

    // Preis
    // Tags

    static constraints = {
        orgDateiname       nullable: false, blank: false
    }
}

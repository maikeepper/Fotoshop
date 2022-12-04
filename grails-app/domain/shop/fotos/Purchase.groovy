package shop.fotos

import java.time.OffsetDateTime

class Purchase {

    String uuid
    OffsetDateTime dateCreated
    OffsetDateTime lastUpdated

    static hasMany = [ fotos: Foto ]

    static constraints = {
    }

    static mapping = {
        autoTimestamp( true )
        uuid          index: 'purchase_uuid_idx'
    }

    /**
     * Get price in cent (!)
     * @return 100 for 1,00 EUR
     */
    Integer getPriceInCent() {
        getFotos().size() * 50
    }
}

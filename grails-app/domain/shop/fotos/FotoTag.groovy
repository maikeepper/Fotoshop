package shop.fotos

import grails.compiler.GrailsCompileStatic
import grails.gorm.DetachedCriteria
import groovy.transform.ToString
import org.codehaus.groovy.util.HashCodeHelper

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class FotoTag implements Serializable {

	private static final long serialVersionUID = 1

    Foto foto
    Tag tag

	@Override
	boolean equals(other) {
		if (other instanceof FotoTag) {
			other.fotoId == foto?.id && other.tagId == tag?.id
		}
	}

    @Override
	int hashCode() {
	    int hashCode = HashCodeHelper.initHash()
        if (foto) {
            hashCode = HashCodeHelper.updateHash(hashCode, foto.id)
		}
		if (tag) {
		    hashCode = HashCodeHelper.updateHash(hashCode, tag.id)
		}
		hashCode
	}

	static FotoTag get(long fotoId, long tagId) {
		criteriaFor(fotoId, tagId).get()
	}

	static boolean exists(long fotoId, long tagId) {
		criteriaFor(fotoId, tagId).count()
	}

	private static DetachedCriteria criteriaFor(long fotoId, long tagId) {
		FotoTag.where {
			foto == Foto.load(fotoId) &&
			tag == Tag.load(tagId)
		}
	}

	static FotoTag create(Foto foto, Tag tag, boolean flush = false) {
		def instance = new FotoTag(foto: foto, tag: tag)
		instance.save(flush: flush)
		instance
	}

	static boolean remove(Foto f, Tag t) {
		if (f != null && t != null) {
			FotoTag.where { foto == f && tag == t }.deleteAll()
		}
	}

	static int removeAll(Foto f) {
		f == null ? 0 : FotoTag.where { foto == f }.deleteAll() as int
	}

	static int removeAll(Tag t) {
		t == null ? 0 : FotoTag.where { tag == t }.deleteAll() as int
	}

	static constraints = {
	    foto nullable: false
		tag nullable: false, validator: { Tag t, FotoTag ft ->
			if (ft.foto?.id) {
				if (FotoTag.exists(ft.foto.id, t.id)) {
				    return ['fotoTag.exists']
				}
			}
		}
	}

	static mapping = {
		id composite: ['foto', 'tag']
		version false
	}
}

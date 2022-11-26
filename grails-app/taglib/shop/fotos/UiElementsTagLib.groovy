package shop.fotos

import grails.util.TypeConvertingMap

class UiElementsTagLib {
    static defaultEncodeAs = [taglib:'none']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    static namespace = "ui"

    /**
     * &lt;ui:message data="${flash}"/&gt; displays flash.message, flash.error<br/>
     * &lt;ui:message data="${cmd}"/&gt; displays cmd?.errors<br/>
     * &lt;ui:message text="${someText}"/&gt; displays someText<br/>
     * &lt;ui:message error="${someError}"/&gt; displays someError
     *
     * @param attrs
     */
    Closure message = { TypeConvertingMap attrs ->
        final String msg = ( attrs[ 'text' ] ?: ( attrs[ 'data' ] ? attrs[ 'data' ][ 'message' ] : '' ) ) as String
        final String error = ( attrs[ 'error' ] ?: ( attrs[ 'data' ] ? ( attrs[ 'data' ][ 'error' ] ?: attrs[ 'data' ][ 'errors' ] ) : '' ) ) as String
        // print element, even if not visible, to be available via JS
        out << '<div class="container"><div class="row">'
        if( msg ) {
            out << '<div id="flashMessage" class="message" role="status">'
            out << msg
        } else {
            out << '<div id="flashMessage" class="message" role="status" style="display:none;">'
        }
        out << '</div>'

        if( error ) {
            out << '<div id="flashError" class="errors" role="status">'
            out << error
        } else {
            out << '<div id="flashError" class="errors" role="status" style="display:none;">'
        }
        out << '</div>'
        out << '</div></div>'
    }
}

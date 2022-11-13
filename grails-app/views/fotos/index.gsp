<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Fotos St. Michael, Trier-Mariahof</title>
</head>
<body>

<div id="content" role="main">
    <g:if test="${ flash.message ?: cmd?.errors }">
    <div class="flash_message error">${ flash.message ?: cmd?.errors }</div>
    </g:if>

    <div id="fotosList" class="container justify-content-center">
        <div class="row">
            <div class="col-md-12">
                <h2>FOTOS</h2>
            </div>
        </div>
        <div class="row sticky-top">
            <div class="col-4">
                <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_UPLOADER,ROLE_STAFF">
                    <g:link controller="fotos" action="index" class="btn btn-outline-success btn-flow">
                        ${ message( code: 'fotos.button.list', default: 'Zu allen Fotos' ) }
                    </g:link>
                </sec:ifAnyGranted>
            </div>
            <div class="col-4">
                <g:form name="selectFotos" controller="fotos" action="checkout">
                    <g:submitButton class="btn-success btn-flow" name="checkoutSubmit"
                                    value="${ message( code: 'fotos.checkout', default: 'Zum Warenkorb' ) }"/>
                    <span id="cartCount" class="cartCount badge">${ selectedFotos?.size() }</span>
                </g:form>
            </div>
            <div class="col-4">
                <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_UPLOADER,ROLE_STAFF">
                    <%-- TODO Katalog drucken --%>
                    <g:form name="createCatalogue" controller="fotos" action="createCatalogue">
                        <g:submitButton class="btn-outline-success btn-flow" name="createCatalogueSubmit"
                                        value="${ message( code: 'fotos.printCatalogue', default: 'Katalog erstellen' ) }"/>
                    </g:form>
                </sec:ifAnyGranted>
            </div>
        </div>

        <% int itemsPerRow = 2 %>
        <g:each in="${ fotoList }" var="foto" status="i">
        <g:if test="${ i%itemsPerRow == 0 }">
        <div class="row">
        </g:if>
            <div class="col col-${ 12.intdiv( itemsPerRow ) }">
                <div class="preview-entry">
                    <g:img uri="${ g.createLink( controller: 'fotos', action: 'preview', id: foto.thumbnail ) }"
                           class="preview manageable" alt="${ foto.thumbnail }" width="480px"/>
                    <div class="imageNumber fotoBadge"
                         title="${ g.message( code: 'fotos.buyMe', default: 'In den Warenkorb' ) }">${ foto.id }</div>
                    <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_UPLOADER,ROLE_STAFF">
                    <%-- TODO confirm, ajax call für kein redirect --%>
                    <g:link controller="fotos" action="delete" id="${ foto.id }" class="uploadHandle fotoBadge"
                        title="${ g.message( code: 'fotos.remove', default: 'Foto löschen' ) }">X</g:link>
                    </sec:ifAnyGranted>
                </div>
            </div>
        <g:if test="${ i%itemsPerRow == itemsPerRow-1 }">
        </div>
        </g:if>
        </g:each>
    </div>
</div>

%{--<content tag="footer"></content>--}%
<script>
    $('.imageNumber.fotoBadge').on( 'click', function( event ) {
        const badge = $( event.target );
        const fotoId = event.target.innerText;
        if( badge.hasClass( 'selected' ) ) {
            // TODO removeFromCart( fotoId );
            badge.removeClass( 'selected' );
            badge.attr( 'title', '${ g.message( code: 'fotos.buyMe', default: 'In den Warenkorb' ) }' );
        } else {
            // TODO addToCart( fotoId );
            badge.addClass( 'selected' );
            badge.attr( 'title', '${ g.message( code: 'fotos.remove', default: 'Entfernen' ) }' );
        }
    });
</script>
</body>
</html>

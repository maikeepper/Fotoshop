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

    <%-- TODO: padding etc., remove-Button größer, Wiederherstellen zentrieren --%>
    <div id="fotosList" class="container justify-content-center">
        <div class="row">
            <div class="col-md-12">
                <h2>FOTOS</h2>
            </div>
        </div>
        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_UPLOADER,ROLE_STAFF">
        <%-- TODO Katalog drucken --%>
        <g:form name="createCatalogue" controller="fotos" action="createCatalogue">
            <g:submitButton class="btn-outline-success btn-flow" name="createCatalogueSubmit"
                            value="${ message( code: 'fotos.printCatalogue', default: 'Katalog erstellen' ) }"/>
        </g:form>
        </sec:ifAnyGranted>
        <g:form name="selectFotos" controller="fotos" action="checkout">
            <%-- FIXME sticky-top --%>
            <g:submitButton class="btn-success sticky-top btn-flow" name="checkoutSubmit"
                            value="${ message( code: 'fotos.checkout', default: 'Zum Warenkorb' ) }"/>
            <span id="cartCount" class="cartCount badge">${ selectedFotos?.size() }</span>
        </g:form>

        <g:each in="${ fotoList }" var="foto" status="i">
        <g:if test="${ i%2 == 0 }">
        <div class="row">
        </g:if>
            <div class="col col-md-6">
                <div class="preview-entry">
                    <g:img uri="${ g.createLink( controller: 'fotos', action: 'preview', id: foto.thumbnail ) }"
                           class="preview manageable" alt="${ foto.thumbnail }" width="480px"/>
                    <div class="imageNumber fotoBadge">${ foto.id }</div>
                    <div class="buyMe"><g:message code="fotos.buyMe" default="In den Warenkorb"/></div>
                </div>
            </div>
        <g:if test="${ i%2 != 0 }">
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
        } else {
            // TODO addToCart( fotoId );
            badge.addClass( 'selected' );
        }
    });
</script>
</body>
</html>

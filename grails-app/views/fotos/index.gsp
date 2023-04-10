<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Fotos St. Michael, Trier-Mariahof</title>
</head>
<body>

<div id="content" role="main">
    <ui:message data="${flash}"/>

    <div id="fotosList" class="container justify-content-center">
        <div class="row">
            <div class="col-md-12">
                <h2>FOTOS</h2>
            </div>
        </div>
        <div class="row sticky-top">
            <div class="col-4">
                <%-- params.offset is null or a String... --%>
                <% int currentOffset = Integer.parseInt( params.offset?.toString() ?: '0' )
                   int prevOffset = Math.max( currentOffset - ( params.max ?: 10 ), 0 ) %>
                <g:link elementId="fotosPrev" controller="fotos" action="index"
                        class="btn btn-outline-success btn-flow ${ currentOffset == 0 ? 'disabled' : ''}"
                        params="${ params + [ 'offset': prevOffset ] }">
                    ${ message( code: 'fotos.button.prev', default: '<<' ) }
                </g:link>
                <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_UPLOADER,ROLE_STAFF">
                    <g:link controller="fotos" action="index" class="btn btn-outline-success btn-flow">
                        ${ message( code: 'fotos.button.list', default: 'Zu allen Fotos' ) }
                    </g:link>
                </sec:ifAnyGranted>
            </div>
            <div class="col-4">
                <g:form name="selectFotos" controller="cart" action="checkout">
                    <input type="hidden" name="selectedFotos" value=""/>
                    <g:submitButton class="btn-success btn-flow" name="cartCheckoutSubmit"
                                    value="${ message( code: 'cart.checkout', default: 'Fotos kaufen' ) }"
                                    disabled="true"/>
                </g:form>
            </div>
            <div class="col-4">
                <%-- params.offset is null or a String... --%>
                <% int nextOffset = Math.max( currentOffset + ( params.max ?: 10 ), 0 ) %>
                <g:link elementId="fotosNext" controller="fotos" action="index"
                        class="btn btn-outline-success btn-flow ${ fotoCount <= nextOffset ? 'disabled' : ''}"
                        params="${ params + [ 'offset': nextOffset ] }">
                    ${ message( code: 'fotos.button.next', default: '>>' ) }
                </g:link>
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
                <div class="preview-entry" title="${ foto.tags?.join( ', ' ) }">
                    <g:img uri="${ g.createLink( controller: 'fotos', action: 'preview', id: foto.thumbnail ) }"
                           class="preview manageable" alt="${ foto.thumbnail }" width="${ 960.intdiv( itemsPerRow ) }px"/>
                    <div class="imageNumber fotoBadge" data-id="${ foto.id }"
                         title="${ g.message( code: 'fotos.buyMe', default: 'In den Warenkorb' ) }">${ foto.id }</div>
                    <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_UPLOADER,ROLE_STAFF">
                    <div class="uploadHandle fotoBadge" data-id="${ foto.id }"
                         title="${ g.message( code: 'fotos.remove', default: 'Entfernen' ) }">X</div>
                    </sec:ifAnyGranted>
                </div>
                <sec:ifAnyGranted roles="ROLE_ADMIN">
                <div class="tag-selection">
                    ${ foto.tags?.join( ', ' ) }
                </div>
                </sec:ifAnyGranted>
            </div>
        <g:if test="${ i%itemsPerRow == itemsPerRow-1 }">
        </div>
        </g:if>
        </g:each>
    </div>
</div>

%{--<content tag="footer"></content>--}%
<script>
    initSelectedFotos( '${ message( code: 'fotos.buyMe', default: 'In den Warenkorb' ) }',
                       '${ message( code: 'fotos.remove', default: 'Entfernen' ) }',
                       '${ message( code: 'cart.checkout', default: 'Fotos kaufen' ) }' );

    $('.imageNumber.fotoBadge').on( 'click', function( event ) {
        const badge = $( event.target );
        const fotoId = badge.data( 'id' );
        if( badge.hasClass( 'selected' ) ) {
            removeFromCart( fotoId, badge );
        } else {
            addToCart( fotoId, badge );
        }
    });

    const $fotoList = $('#fotosList');
    $fotoList.on( 'click', '.uploadHandle.fotoBadge', function( event ) {
        if( !confirm('${message(code: 'default.button.delete.confirm.message', default: 'Sind Sie sicher? Die Aktion kann nicht rückgängig gemacht werden.')}') ) {
            return;
        }
        // hide Foto display and delete Foto
        const target = event.target;
        const previewEntry = target.closest( '.preview-entry' );
        const fotoId = $( event.target ).data( 'id' );
        $.ajax({
            url: '${g.createLink( controller: "fotos", action: "delete" )}/' + fotoId,
            method: 'DELETE',
            success: function( data, textStatus, jqXHR ) {
                if( jqXHR.status < 300 ) {
                    $(previewEntry).hide();
                } else {
                    const $flashError = $('#flashError');
                    $flashError.text(jqXHR.responseText ? jqXHR.responseText : JSON.stringify(jqXHR));
                    $flashError.show();
                }
            },
            error: function( jqXHR, textStatus, errorThrown ) {
                if( jqXHR ) {
                    const $flashError = $('#flashError');
                    $flashError.text(jqXHR.responseText ? jqXHR.responseText : JSON.stringify(jqXHR));
                    $flashError.show();
                }
            }
        });
    });
</script>
</body>
</html>

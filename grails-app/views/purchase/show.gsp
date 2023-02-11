<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'purchase.label', default: 'Purchase')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <sec:ifAnyGranted roles="ROLE_ADMIN">
            <section class="row">
                <a href="#show-purchase" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </section>
            </sec:ifAnyGranted>
            <section class="row">
                <div id="show-purchase" class="col-12 content scaffold-show" role="main">
                    <h1><g:message code="purchase.show.label" default="Download" /></h1>
                    <ui:message data="${flash}"/>

                    <div id="fotosList" class="container justify-content-center">
                        <div class="row sticky-top">
                            <div class="col-4"></div>
                            <div class="col-4">
                                <g:if test="${purchase?.paid}">
                                <g:form name="downloadFotos" controller="purchase" action="download">
                                    <input type="hidden" name="id" value="${purchase?.uuid}"/>
                                    <g:submitButton class="btn-success btn-flow" name="downloadPurchase"
                                                    value="${ message( code: 'purchase.download.button.label', default: 'Download ZIP' ) }"/>
                                </g:form>
                                </g:if><g:else>
                                <g:form name="payFotos" controller="cart" action="checkout">
                                    <input type="hidden" name="purchaseUuid" value="${purchase?.uuid}"/>
                                    <g:submitButton class="btn-success btn-flow" name="paySubmit"
                                                    value="${ message( code: 'cart.checkout', default: 'Fotos kaufen' ) }"/>
                                </g:form>
                                </g:else>
                            </div>
                            <div class="col-4"></div>
                        </div>

                        <% int itemsPerRow = 4 %>
                        <g:each in="${ purchase?.fotos }" var="foto" status="i">
                        <g:if test="${ i%itemsPerRow == 0 }">
                        <div class="row">
                        </g:if>
                            <div class="col col-${ 12.intdiv( itemsPerRow ) }">
                                <div class="preview-entry" title="${ foto.tags?.join( ', ' ) }">
                                    <g:img uri="${ g.createLink( controller: 'fotos', action: 'preview', id: foto.thumbnail ) }"
                                           class="preview manageable" alt="${ foto.thumbnail }" width="${ 960.intdiv( itemsPerRow ) }px"/>
%{--                                    <div class="imageNumber fotoBadge" data-id="${ foto.id }"--}%
%{--                                         title="${ g.message( code: 'fotos.download', default: 'Download' ) }">${ foto.id }</div>--}%
                                </div>
                            </div>
                        <g:if test="${ i%itemsPerRow == itemsPerRow-1 }">
                        </div>
                        </g:if>
                        </g:each>
                    </div>

                    <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <g:form resource="${this.purchase}" method="DELETE">
                        <fieldset class="buttons">
                            <g:link class="edit" action="edit" resource="${this.purchase}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                            <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                        </fieldset>
                    </g:form>
                    </sec:ifAnyGranted>
                </div>
            </section>
        </div>
    </div>
%{--    <script>--}%
%{--        $('.imageNumber.fotoBadge').on( 'click', function( event ) {--}%
%{--            const badge = $( event.target );--}%
%{--            const fotoId = badge.data( 'id' );--}%
%{--            if( badge.hasClass( 'selected' ) ) {--}%
%{--                removeFromCart( fotoId, badge );--}%
%{--            } else {--}%
%{--                addToCart( fotoId, badge );--}%
%{--            }--}%
%{--        });--}%
%{--    </script>--}%
    </body>
</html>

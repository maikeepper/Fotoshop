<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Checkout</title>
</head>
<body>

<div id="content" role="main">
    <ui:message data="${flash}"/>

    <div id="checkout" class="container justify-content-center">
        <div class="row">
            <div class="col-md-12">
                <h2>Checkout</h2>
            </div>
        </div>

        <div class="row">
            <pre>
                Download: ${ purchase?.uuid }
                #Bilder: ${ purchase?.fotos?.size() }
                Bildnummern: ${ purchase?.fotos?.collect{ it.id } }

                Preis: ${ purchase?.getPriceInCent() / 100 } €
            </pre>
        </div>

        <div class="row">
            <%-- TODO Paypal, then redirect to --%>
            <div class="col-4">
%{--            <g:link controller="purchase" action="show" id="${ purchase?.uuid }" params="[ fixmePaid: true ]">--}%
%{--                Paypal bezahlt--}%
%{--            </g:link>--}%
            </div>
            <div class="col-4">
            <g:link controller="purchase" action="show" id="${ purchase?.uuid }">
                Paypal nicht bezahlt
            </g:link>
            </div>
            <div class="col-4"></div>
        </div>
    </div>
</div>

</body>
</html>

<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Uploaded Files</title>
</head>
<body>

<div id="content" role="main">
    <g:if test="${ flash.message ?: cmd?.errors }">
    <div class="flash_message error">${ flash.message ?: cmd?.errors }</div>
    </g:if>

    <div class="container justify-content-center">
        <div class="row">
            <div class="col-md-12">

                <h2>UPLOADED FILES</h2>

                <g:form controller="fotos" action="saveMultiple">
                    <g:each in="${ fotos }" var="foto">
                        <f:all bean="${ foto }" except=""/>
                    </g:each>
                    <g:submitButton name="${ message( code: 'fotos.save', default: 'Speichern' ) }"/>
                </g:form>
            </div>
        </div>
    </div>
</div>

%{--<content tag="footer"></content>--}%
</body>
</html>

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

    <div id="uploadedFiles" class="container justify-content-center">
        <div class="row">
            <div class="col-md-12">
                <h2>UPLOADED FILES</h2>
            </div>
        </div>
        <g:form name="saveMultiple" controller="fotos" action="saveMultiple">
            <div class="row sticky-top">
            <span id="fotosCount" style="display: none;">${ fotos?.size() }</span>
            <g:submitButton class="btn-success btn-flow" name="saveMultipleSubmit"
                            value="${ message( code: 'fotos.save', args: [ fotos?.size() ], default: "${ fotos?.size() } Fotos speichern" ) }"/>
            </div>

            <% int itemsPerRow = 3 %>
            <g:each in="${ fotos }" var="foto" status="i">
            <g:if test="${ i%itemsPerRow == 0 }">
            <div class="row">
            </g:if>
                <div class="col col-${ 12.intdiv( itemsPerRow ) }">
                    <div class="preview-entry">
                        <input type="hidden" name="origFilename" value="${ foto.origFilename }"/>
                        <input type="hidden" name="thumbnail" value="${ foto.thumbnail }"/>
                        <g:img uri="${ g.createLink( controller: 'fotos', action: 'preview', id: foto.thumbnail ) }"
                               class="preview manageable" alt="${ foto.thumbnail }" width="480px"/>
                        <div class="uploadHandle fotoBadge"
                             title="${ g.message( code: 'fotos.remove', default: 'Entfernen' ) }">X</div>
                    </div>
                    <div class="btn btn-outline-secondary preview-removed" style="display: none;">
                        ${ message( code: 'upload.recreate', args: [ foto.origFilename ], default: "${ foto.origFilename } wiederherstellen" ) }
                    </div>
                </div>
            <g:if test="${ i%itemsPerRow == itemsPerRow-1 }">
            </div>
            </g:if>
            </g:each>
        </g:form>
    </div>
</div>

%{--<content tag="footer"></content>--}%
<script>
    // Update the inner HTML (should represent a number) of the '#fotosCount' element.
    function updateFotosCount( add ) {
        const fotosCountElem = document.getElementById( 'fotosCount' );
        const fotosCount = fotosCountElem.innerText;
        const newFotosCount = '' + ( Number( fotosCount ) + add );
        const submitButton = $( 'input[name="saveMultipleSubmit"]' );
        submitButton.val( submitButton.val().replace( fotosCount, newFotosCount ) );
        fotosCountElem.innerHTML = newFotosCount;
    }

    const $uploadedFiles = $('#uploadedFiles');
    $uploadedFiles.on( 'click', '.uploadHandle.fotoBadge', function( event ) {
        // hide Foto display and show placeholder
        const target = event.target;
        const previewEntry = target.closest( '.preview-entry' );
        const previewRemoved = previewEntry.nextSibling.nextSibling;
        $( previewEntry ).hide();
        $( previewRemoved ).show();

        // update fotos.save count
        updateFotosCount( -1 );

        // disable input fields for Foto-to-be-saved
        $( previewEntry ).find( 'input[name="origFilename"]' ).prop( 'disabled', true );
        $( previewEntry ).find( 'input[name="thumbnail"]' ).prop( 'disabled', true );
    });
    $uploadedFiles.on( 'click', '.preview-removed', function( event ) {
        // show Foto display again
        const target = event.target;
        const previewRemoved = target.closest( '.preview-removed' );
        const previewEntry = previewRemoved.previousSibling.previousSibling;
        $( previewRemoved ).hide();
        $( previewEntry ).show();

        // update fotos.save count
        updateFotosCount( 1 );

        // enable input fields for Foto-to-be-saved again
        $( previewEntry ).find( 'input[name="origFilename"]' ).removeAttr( 'disabled' );
        $( previewEntry ).find( 'input[name="thumbnail"]' ).removeAttr( 'disabled' );
    });
</script>
</body>
</html>

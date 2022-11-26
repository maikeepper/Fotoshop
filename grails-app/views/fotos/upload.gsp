<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Upload</title>
</head>
<body>

<div id="content" role="main">
    <ui:message data="${flash ?: cmd}"/>

    <div class="container d-flex justify-content-center mt-100">

        <div class="row">
            <div class="col-12">

                <h2>FILE UPLOAD</h2>

                <g:uploadForm name="uploadFotos" controller="upload" action="multiple">
                    <div class="file-drop-area">
                        <span class="choose-file-button"><g:message code="upload.chooseFiles.label" default="Choose files"/></span>
                        <span class="file-message"><g:message code="upload.dragDropFilesHere.label" default="or drag and drop files here"/></span>
                        <input class="file-input" type="file" name="files" multiple/>
                    </div>
                    <div class="tag-selection">
                        <g:select class="js-select2" name="tags" from="${ shop.fotos.Tag.all }" optionKey="name" value="${ cmd?.tags }" multiple="true" />
                    </div>
                    <fieldset class="buttons-light">
                        <input class="upload-button" type="submit" value="${message(code: 'upload.submit.label', default: 'Upload')}" disabled/>
                    </fieldset>
                </g:uploadForm>

            </div>
        </div>
    </div>
</div>

%{--<content tag="footer"></content>--}%
<script>
    $(document).on('change', '.file-input', function() {

      const filesCount = $(this)[0].files.length;
      const textbox = $(this).prev();

      if (filesCount === 1) {
        const fileName = $(this).val().split('\\').pop();
        textbox.text(fileName);
      } else {
        textbox.text(filesCount + ' files selected');
      }

      $('.upload-button').attr( 'disabled', false );
    });
</script>
</body>
</html>

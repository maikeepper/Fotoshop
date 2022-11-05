<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Upload</title>
</head>
<body>

<div id="content" role="main">
    <g:if test="${ flash.message ?: cmd?.errors }">
    <div class="flash_message error">${ flash.message ?: cmd?.errors }</div>
    </g:if>

    <div class="container d-flex justify-content-center mt-100">

        <div class="row">
            <div class="col-md-12">

                <h2>FILE UPLOAD</h2>

                <g:uploadForm name="uploadFotos" controller="upload" action="multiple">
                    <div class="file-drop-area">
                        <span class="choose-file-button"><g:message code="upload.chooseFiles.label" default="Choose files"/></span>
                        <span class="file-message"><g:message code="upload.dragDropFilesHere.label" default="or drag and drop files here"/></span>
                        <input class="file-input" type="file" name="files" multiple/>
                    </div>
                    <fieldset class="buttons-light">
                        <input class="upload-button" type="submit" value="${message(code: 'upload.submit.label', default: 'Upload')}" disabled/>
                    </fieldset>
                </g:uploadForm>

                %{--                <form action="/upload/multiple">--}%
%{--                    <div class="file-drop-area">--}%
%{--                        <span class="choose-file-button">Choose files</span>--}%
%{--                        <span class="file-message">or drag and drop files here</span>--}%
%{--                        <input class="file-input" type="file" name="files" multiple>--}%
%{--                    </div>--}%
%{--                    <g:submitButton name="Dateien hochladen"/>--}%
%{--                </form>--}%

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

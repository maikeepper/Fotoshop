// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-3.5.1.min
//= require popper.min
//= require bootstrap
//= require_self

function handleServerResponse( data, textStatus, jqXHR, displayText ) {
    if( jqXHR.status < 300 ) {
        const $flashMessage = $('#flashMessage');
        if( displayText ) {
            $flashMessage.text( displayText );
            $flashMessage.show();
        } else if( data[ 'message' ] ) {
            $flashMessage.text( data[ 'message' ] );
            $flashMessage.show();
        }
    } else {
        const $flashError = $('#flashError');
        $flashError.text(jqXHR.responseText ? jqXHR.responseText : JSON.stringify(jqXHR));
        $flashError.show();
    }
}

function handleServerError( jqXHR, textStatus, errorThrown ) {
    const $flashError = $('#flashError');
    if( jqXHR ) {
        $flashError.text( jqXHR.responseText ? jqXHR.responseText : JSON.stringify(jqXHR) );
    } else {
        $flashError.text( textStatus + ': ' + errorThrown );
    }
    $flashError.show();
}
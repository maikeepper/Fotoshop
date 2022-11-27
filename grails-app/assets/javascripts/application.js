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
        } else if( data && data[ 'message' ] ) {
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


const INPUT_SELECTED_FOTOS_NAME = 'selectedFotos';
let DESELECTED_TITLE = 'In den Warenkorb';
let SELECTED_TITLE = 'Entfernen';
let CHECKOUT_BUTTON_LABEL = 'Fotos kaufien';

/*
 * On page load initialize the selected items.
 *
 * @param deselectedTitle      translated content for titles of deselected Fotos
 * @param selectedTitle        translated content for titles of selected Fotos
 * @param checkoutButtonLabel  translated content for the checkout button
 */
function initSelectedFotos( deselectedTitle, selectedTitle, checkoutButtonLabel ) {
    if( deselectedTitle ) DESELECTED_TITLE = deselectedTitle;
    if( selectedTitle ) SELECTED_TITLE = selectedTitle;
    if( checkoutButtonLabel ) CHECKOUT_BUTTON_LABEL = checkoutButtonLabel;

    const selectedFotos = getSelectedItems();
    if( selectedFotos ) {
        for( const fotoId of selectedFotos ) {
            addToCart( fotoId, null, true );
        }
    }
}

/*
 * Get the over all pages (currently in this session) selected items.
 */
function getSelectedItems() {
    const selectedFotos = JSON.parse( sessionStorage.getItem( INPUT_SELECTED_FOTOS_NAME ) );
    if ( selectedFotos ) {
        return Array.from( selectedFotos );
    }
    return null;
}

/*
 * Set the over all pages (currently in this session) selected items.
 * @param items (Array)
 */
function setSelectedItems( items ) {
    sessionStorage.setItem( INPUT_SELECTED_FOTOS_NAME, JSON.stringify( items ) );
}

/*
 * Clear selection of items.
 */
function clearSelectedItems() {
    sessionStorage.removeItem( INPUT_SELECTED_FOTOS_NAME );
}

function addToCart( fotoId, $badgeElem, fromInit ) {
    let selectedFotos = getSelectedItems();
    if( !fromInit ) {
        // update session state
        if (!selectedFotos) {
            selectedFotos = [];
        }
        if (selectedFotos.indexOf(fotoId) < 0) {
            selectedFotos.push(fotoId);
        }
        setSelectedItems(selectedFotos);
    }

    // update GUI elements - foto badge
    const $badge = $badgeElem ? $badgeElem : $( '.imageNumber[data-id="' + fotoId + '"]' );
    $badge.addClass( 'selected' );
    $badge.attr( 'title', SELECTED_TITLE );

    // update GUI elements - checkout button label + disabled state
    const $checkoutButton = $( 'input[name="cartCheckoutSubmit"]' );
    $checkoutButton.val( CHECKOUT_BUTTON_LABEL + ' (' + selectedFotos.length + ')');
    $checkoutButton.removeAttr( 'disabled' );

    // update GUI elements - form parameter
    $( 'input[name="' + INPUT_SELECTED_FOTOS_NAME + '"]' ).val( JSON.stringify( selectedFotos ) );
}

function removeFromCart( fotoId, $badgeElem, fromInit ) {
    let selectedFotos = getSelectedItems();
    if( !selectedFotos ) {
        selectedFotos = [];
    }
    if( !fromInit ) {
        // update session state
        // remove all appearances of fotoId in selectedFotos - should be only one, but better miss on the safe side
        for( let i = selectedFotos.length - 1; i >= 0; i-- ) {
            if( selectedFotos[ i ] === fotoId ) {
                selectedFotos.splice( i, 1 );
            }
        }
        setSelectedItems( selectedFotos );
    }

    // update GUI elements - foto badge
    const $badge = $badgeElem ? $badgeElem : $( '.imageNumber[data-id="' + fotoId + '"]' );
    $badge.removeClass( 'selected' );
    $badge.attr( 'title', DESELECTED_TITLE );

    // update GUI elements - checkout button label + disabled state
    const $checkoutButton = $( 'input[name="cartCheckoutSubmit"]' );
    if( selectedFotos.length ) {
        $checkoutButton.val( CHECKOUT_BUTTON_LABEL + ' (' + selectedFotos.length + ')' );
    } else {
        $checkoutButton.val( CHECKOUT_BUTTON_LABEL );
        $checkoutButton.attr( 'disabled', true );
    }

    // update GUI elements - form parameter
    $( 'input[name="' + INPUT_SELECTED_FOTOS_NAME + '"]' ).val( JSON.stringify( selectedFotos ) );
}
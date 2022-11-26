<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.codes.list.label" default="Codes" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                        <li><g:link class="create" action="create"><g:message code="default.new.label" args="['Code']" /></g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="list-user" class="col-12 content scaffold-list" role="main">
                    <h1><g:message code="default.list.label" args="['Code']" /></h1>
                    <ui:message data="${flash}"/>

                    <table>
                        <thead>
                            <tr>
                                <th class="sortable"><a href="/user/index?sort=username&amp;max=10&amp;order=asc">
                                    <g:message code="user.username.label" default="Code"/>
                                </a></th>
                                <th class="sortable"><a href="/user/index?sort=enabled&amp;max=10&amp;order=asc">
                                    <g:message code="user.enabled.label" default="Enabled"/>
                                </a></th>
                                <th><g:message code="authorities.label" default="Roles"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        <g:each in="${ userList }" var="user" status="i">
                            <tr class="${i%2==0 ? 'even' : 'odd'}">
                                <td><f:display bean="${ user }" property="username"/></td>
                                <td>
                                    <input type="checkbox" name="enabled" ${ user.enabled ? 'checked="checked"' : '' }
                                           value="${ user.enabled }" data-id="${ user.id }" id="enabled${ user.id }"/>
                                </td>
                                <td>
                                    <g:select class="js-select2" name="authorities" data-id="${ user.id }" multiple="true"
                                              from="${ shop.fotos.authentication.Role.all }"
                                              optionKey="authority" value="${ user.authorities }"/>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>

                    <g:if test="${userCount > params.int('max')}">
                    <div class="pagination">
                        <g:paginate total="${userCount ?: 0}" />
                    </div>
                    </g:if>
                </div>
            </section>
        </div>
    </div>
    <script>
        const enabledText = '<g:message code="message.user.enabled" default="Code enabled"/>';
        const disabledText = '<g:message code="message.user.disabled" default="Code disabled"/>';
        const rolesChangedText = '<g:message code="message.user.rolesChanged" default="Roles changed"/>';

        $('input[name="enabled"]').on( 'change', function( event ) {
            const elem = event.target;
            const userId = $( elem ).data( 'id' );
            const userEnabled = $( elem ).is( ':checked' );
            $.ajax({
                url: '/user/update/' + userId + '?sessionId=${ sessionId }',
                method: 'PUT',
                data: {
                    'enabled': userEnabled
                },
                success: ( data, textStatus, jqXHR ) => handleServerResponse( data, textStatus, jqXHR, (userEnabled ? enabledText : disabledText) ),
                error: handleServerError
            });
        });

        $('select[name="authorities"]').on( 'change', function( event ) {
            const $selectBox = $( event.target );
            const userId = $selectBox.data( 'id' );
            //const authorities = $selectBox.find( 'option:selected' ).val();
            const authorities = $selectBox.val();
            console.log(authorities);
            $.ajax({
                url: '/user/update/' + userId + '?sessionId=${ sessionId }',
                method: 'PUT',
                data: {
                    'authorities': authorities
                },
                success: ( data, textStatus, jqXHR ) => handleServerResponse( data, textStatus, jqXHR, rolesChangedText ),
                error: handleServerError
            });
        });
    </script>
    </body>
</html>
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
                success: function( data, textStatus, jqXHR ) {
                    if( jqXHR.status < 300 ) {
                        const enabledText = '<g:message code="message.user.enabled" default="Code enabled"/>';
                        const disabledText = '<g:message code="message.user.disabled" default="Code disabled"/>';
                        $('#flashMessage').text((userEnabled ? enabledText : disabledText));
                        $('#flashMessage').show();
                    } else {
                        $('#flashError').text(jqXHR.responseText ? jqXHR.responseText : JSON.stringify(jqXHR));
                        $('#flashError').show();
                    }
                },
                error: function( jqXHR, textStatus, errorThrown ) {
                    if( jqXHR ) {
                        $('#flashError').text(jqXHR.responseText ? jqXHR.responseText : JSON.stringify(jqXHR));
                        $('#flashError').show();
                    }
                }
            });
        });
    </script>
    </body>
</html>
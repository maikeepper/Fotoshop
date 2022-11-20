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
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>

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
                        <g:each in="$userList" var="user" status="i">
                            <tr class="${i%2==0 ? 'even' : 'odd'}">
                                <td><f:display bean="${user}" property="username"/></td>
                                <td>
                                    %{--<input type="hidden" name="_enabled"/>--}%
                                    <input type="checkbox" name="enabled" checked="checked" value="true"
                                           data-id="${user.id}" id="enabled${user.id}"/>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>

                    <f:table collection="${userList}" except="['id','password','passwordExpired','accountLocked','accountExpired']"/>

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
            const userId = elem.data( 'id' );
            console.log( 'UserId ' + userId.toString() + ' was ' + $( elem ).val() );
            // $.ajax({
            //    url: '/user/update/' + userId,
            //    method: 'POST',
            //    data: {
            //        'enabled': $( elem ).val()
            //    }
            // });
        });
    </script>
    </body>
</html>
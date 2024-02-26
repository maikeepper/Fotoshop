<%@ page import="grails.plugin.springsecurity.SpringSecurityUtils" %>
<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Fotos"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

    <asset:javascript src="application.js"/>

    <asset:stylesheet src="bootstrap.css"/>
    <asset:stylesheet src="select2.css"/>
    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-dark navbar-static-top" role="navigation">
    <div class="container-fluid">
%{--        <a class="navbar-brand" href="/#"><asset:image src="documentation.svg" alt="Fotos"/></a>--}%

        <div id="contact" class="header contact" role="navigation">
            <h2>Anmerkungen, Fragen oder Bitten</h2>
            <p>
                Senden Sie uns eine E-Mail an <a href="mailto:mail@to.info">mail@to.info</a>.
                Sie erreichen uns unter <a href="tel:0049123456789">+49&nbsp;123&nbsp;456789</a>.
            </p>
        </div>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" aria-expanded="false" style="height: 0.8px;" id="navbarContent">
            <ul class="nav navbar-nav ml-auto">
                <ul class="nav-menu">
                    <% String fotoNavEntry = 'Fotos';
                       if( SpringSecurityUtils.ifAnyGranted( 'ROLE_ADMIN' ) ) {
                           fotoNavEntry += ' (' + applicationContext.getBean( 'fotoService' )?.count() + ')';
                       } %>
                    <li class="item"><a href="/fotos/" class="nav-link">${ fotoNavEntry }</a></li>
                    <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_UPLOADER">
                    <li class="item"><a href="/fotos/upload" class="nav-link">Upload</a></li>
                    </sec:ifAnyGranted>
                    <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_STAFF">
                    <li class="item"><a href="/user/" class="nav-link">Codes</a></li>
                    </sec:ifAnyGranted>
                    <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_STAFF">
                    <li class="item"><a href="/gutscheine/" class="nav-link">Gutscheine</a></li>
                    </sec:ifAnyGranted>
                    <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <li class="item"><a href="/purchase/" class="nav-link">Einkäufe</a></li>
                    <li class="item"><a href="/h2-console" class="nav-link">DB Console</a></li>
                    <li class="item"><a href="/logout" class="nav-link" onclick="clearSelectedItems();">Logout</a></li>
                    </sec:ifAnyGranted>
                </ul>
                <g:pageProperty name="page.nav"/>
            </ul>
        </div>
    </div>
</nav>

<g:layoutBody/>

<div class="footer" role="contentinfo">
    <div class="container-fluid">
        <div class="row contact">
            <g:pageProperty name="page.footer"/>
        </div>
    </div>
</div>

<div id="spinner" class="spinner" style="display:none;">
    <g:message code="spinner.alt" default="Loading&hellip;"/>
</div>

<asset:javascript src="select2.js"/>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Fotos St. Michael</title>
<style type="text/css" media="screen">

	#login {
		margin: 15px 0px;
		padding: 0px;
		text-align: center;
		font-family: Arial, Helvetica, sans-serif;
	}

	#login .inner {
		width: 400px;
		padding-bottom: 6px;
		margin: 60px auto;
		text-align: center;
		border: 1px solid #aab;
		-moz-box-shadow: 2px 2px 2px #eee;
		-webkit-box-shadow: 2px 2px 2px #eee;
		-khtml-box-shadow: 2px 2px 2px #eee;
		box-shadow: 2px 2px 2px #eee;
	}

	#login .inner .fheader {
		padding: 18px 26px 14px 26px;
		margin: 0px 0 14px 0;
		color: #2e3741;
		font-size: 18px;
		font-weight: bold;
	}

	#login .inner .input-fields {
		margin: 10px auto;
	}

	</style>
</head>

<body>
<div id="login">
	<div class="inner">
		<div class="fheader"><g:message code="login.header" default="Bitte geben Sie Ihren Code ein:"/></div>

		<g:if test="${ flash.message }">
		    <ui:message error="${message( code: 'login.error', default: 'Es konnte leider kein entsprechender Code gefunden werden.')}"/>
		</g:if>

		<form action="/login/authenticate" method="POST" id="loginForm" class="cssform">
%{--			<input type="checkbox" class="chk" name="remember-me" id="remember_me" checked style="display: none;">--}%

			<p class="input-fields">
				<input type="text" class="text" name="username" id="username" autocapitalize="characters"/>
				<input type="text" class="text" name="password" id="password" autocapitalize="characters"/>
			</p>

			<p>
				<g:submitButton name="submit" value="${message( code:'login.login', default:'Absenden' )}"/>
			</p>
		</form>
	</div>
</div>

<script type="text/javascript">
	document.addEventListener("DOMContentLoaded", function(event) {
		document.forms['loginForm'].elements['username'].focus();
	});
</script>
</body>
</html>
<!DOCTYPE html>
<html>
	<head>
		<title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error</g:else></title>
		<meta name="layout" content="main">
	</head>
	<body>
		<g:renderException exception="${exception}" />
	</body>
</html>

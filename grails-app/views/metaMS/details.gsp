<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>MetaMS submissions</title>    
</head>
<body>

 <div class="container">
	
	<h2>MetaMS details</h2>
	
	  	 <!-- Show errors -->
    <g:if test="${flash.error}">
  		<div class="alert alert-block alert-danger">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>Error: </strong>${flash.error}
  		</div>
	</g:if>
	
		<!-- Show message -->
    <g:if test="${flash.message}">
  		<div class="alert alert-block alert-success">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>OK: </strong>${flash.message}
  		</div>
	</g:if>
	
	<h3>Command line</h3> 
	<div class="container">
		${command}
	</div>
	
	<h3>Standard error</h3> 
	<div class="container">
		${stdErr}
	</div>
	
	<h3>Standard output</h3> 
	<div class="container">
		${stdOut}
	</div>

</div> <!-- /container -->

</body>
</html>
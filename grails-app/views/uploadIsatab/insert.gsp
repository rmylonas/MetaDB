<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>ISAtab inserted</title>    
</head>
<body>

	 <div class="container">
	 
	    <h3>Inserted ISAtab data</h3>
	      
	    <!-- Show errors -->
	    <g:if test="${flash.error}">
	  		<div class="alert alert-block alert-error">
	  			<button type="button" class="close" data-dismiss="alert">&times;</button>
	  			<strong>Error: </strong>${flash.error}
	  		</div>
		</g:if>
		
		<!-- Show messages -->
		<g:if test="${flash.message}">
	  		<div class="alert alert-block alert-success">
	  			<button type="button" class="close" data-dismiss="alert">&times;</button>
	  			<strong>OK: </strong>${flash.message}
	  		</div>
		</g:if>
	
 	</div>
	
</body>
</html>
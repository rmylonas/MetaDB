<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>PCA plots</title>    
</head>
<body>

 <div class="container">
	
	 	<h4>PCA plots</h4>
	
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
	
	<g:each in="${flash.plotFileList}">
		<img src="${createLink(controller: 'pca', action: 'displayPca', params='[file: ${it}]')}"/>
	</g:each>
	
</div> <!-- /container -->

</body>
</html>
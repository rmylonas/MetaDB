<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Manage groups</title>    
</head>
<body>

 <div class="container">
 
 <div class="container">
 	<g:link action="detail" controller="group" class="btn btn-default btn-sm">Back</g:link>
    <h3>Project details</h3>

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
	
	<g:uploadForm action="updateProject" class="form-horizontal">
	 		
 		<div class="col-md-4">	 		 		
	   		<div class="form-group">	
				<label for="project">Name</label>
				<input class="form-control" name="name" value="${session.project.name}">
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="project">Description</label>
				<input class="form-control" name="description" value="${session.project.description}">
	   		</div> <!-- form-group -->
			
			<div class="form-group">
				<input class="btn btn-primary" type="submit" value="Update details">
			</div>
	  	</div> <!-- col-md-4 -->
	  	
    </g:uploadForm>
 </div>  
	 
</div> <!-- /container -->


</body>
</html>
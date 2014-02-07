<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Update organism</title>    
</head>
<body>

 <div class="container">
 
 	 <!-- Show errors -->
    <g:if test="${flash.error}">
  		<div class="alert alert-block alert-danger">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>Error: </strong>${flash.error}
  		</div>
	</g:if>
 
    <h3>Organism entry</h3>

	<g:uploadForm action="${(flash.newOrganism) ? 'saveOrganism' : 'updateOrganism'}" class="form-horizontal"> 		
 		<div class="col-md-4">	 		 		
	   		<div class="form-group">	
				<label for="name">Name</label>
				<input class="form-control" name="name" value="${(flash.newOrganism) ? '' : session.organism?.name}">
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="alternativeNames">Alternative names (and keywords)</label>
				<input type="alternativeNames" class="form-control" name="alternativeNames" value="${(flash.newOrganism) ? '' : session.organism?.alternativeNames}">
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="description">Description</label>
				<input type="description" class="form-control" name="description" value="${(flash.newOrganism) ? '' : session.organism?.description}">
	   		</div> <!-- form-group -->			
			
			<div class="form-group">
				<input class="btn btn-primary" type="submit" value="Save">
				<g:link action="index" class="btn btn-warning">Cancel</g:link>
			</div>
	  	</div> <!-- col-md-4 -->
	  	
    </g:uploadForm>
	    
</div> <!-- /container -->


</body>
</html>
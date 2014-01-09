<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Manage methods</title>    
</head>
<body>
 
 <div class="container">
 	<g:link action="detail" controller="instrument" class="btn btn-default btn-sm">Back</g:link>
    <h3>Method details</h3>

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
	
	<g:uploadForm action="saveNewMethod" class="form-horizontal" role="form">
	 		
 		<div class="col-md-4">	 		 		
	   		<div class="form-group">	
				<label for="name">Name</label>
				<input class="form-control" name="name" >
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="description">Description</label>
				<input class="form-control" name="description" >
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">
				<label for="tag">Tag at end of filenames</label>
				<input class="form-control" name="tag" >
	   		</div> <!-- form-group -->
			
			<div class="form-group checkbox">
				<label>					
					<input type="checkbox" name="randomization" checked>					
					Randomize runs
				</label>
			</div>
			<p class="help-block"></p>
			
	   		<div class="form-group">	
				<label for="startPattern">Randomization start pattern</label>
				<input class="form-control" name="startPattern" >
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="repeatPattern">Randomization repeat pattern</label>
				<input class="form-control" name="repeatPattern" >
	   		</div> <!-- form-group -->
			
			<div class="form-group">	
				<label for="endPattern">Randomization end pattern</label>
				<input class="form-control" name="endPattern" >
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="metaMsDb">MetaMS database</label>
				<g:select class="form-control input" name="metaMsDb" optionValue="name" from="${flash.metaMsDb}" optionKey="id" />
	   		</div> <!-- form-group -->
			
			<div class="form-group">	
				<label for="metaMsParameterFile">MetaMS parameters filename</label>
				<input class="form-control" name="metaMsParameterFile" ">
	   		</div> <!-- form-group -->
			
			<div class="form-group">
				<input class="btn btn-primary" type="submit" value="Update method">
			</div>
	  	</div> <!-- col-md-4 -->
	  	
    </g:uploadForm>
	 
</div> <!-- /container -->


</body>
</html>
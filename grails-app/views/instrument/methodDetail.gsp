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
	
	<g:uploadForm action="updateMethod" class="form-horizontal" role="form">
	 		
 		<div class="col-md-4">	 		 		
	   		<div class="form-group">	
				<label for="name">Name</label>
				<input class="form-control" name="name" value="${(session.method.name)}">
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="description">Description</label>
				<input class="form-control" name="description" value="${session.method.description}">
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">
				<label for="tag">Tag at end of filenames</label>
				<input class="form-control" name="tag" value="${session.method.tag}">
	   		</div> <!-- form-group -->
			
			<div class="form-group checkbox">
				<label>					
					<g:if test="${session.method.randomization}">
						<input type="checkbox" name="randomization" value="randomize" checked>
					</g:if>
					<g:else>
   						 <input type="checkbox" name="randomization" value="randomize" >
					</g:else>
					
					Randomize runs
				</label>
			</div>
			<p class="help-block"></p>
			
	   		<div class="form-group">	
				<label for="startPattern">Randomization start pattern</label>
				<input class="form-control" name="startPattern" value="${session.method.startPattern}">
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="repeatPattern">Randomization repeat pattern</label>
				<input class="form-control" name="repeatPattern" value="${session.method.repeatPattern}">
	   		</div> <!-- form-group -->
			
			<div class="form-group">	
				<label for="endPattern">Randomization end pattern</label>
				<input class="form-control" name="endPattern" value="${session.method.endPattern}">
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="metaMsDb">MetaMS database</label>
				<g:select class="form-control input" name="metaMsDb" optionValue="name" from="${session.metaMsDb}" optionKey="id" />
	   		</div> <!-- form-group -->
			
			<div class="form-group">	
				<label for="metaMsParameterFile">MetaMS parameters filename</label>
				<input class="form-control" name="metaMsParameterFile" value="${session.method.metaMsParameterFile}">
	   		</div> <!-- form-group -->
			
			<div class="form-group">
				<input class="btn btn-primary" type="submit" value="Update method">
			</div>
	  	</div> <!-- col-md-4 -->
	  	
    </g:uploadForm>
	 
</div> <!-- /container -->


</body>
</html>
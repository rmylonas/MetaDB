<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>New MetaMS DB</title>    
</head>
<body>

 <div class="container">
    <h3>New MetaMS DB</h3>

	<g:uploadForm action="saveNewDb" class="form-horizontal">
	 		
 		<div class="col-md-4">	 		 		
	   		<div class="form-group">	
				<label for="project">Name</label>
				<input class="form-control" name="name" >
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="project">Absolute path to RData file</label>
				<input class="form-control" name="rDataPath" >
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
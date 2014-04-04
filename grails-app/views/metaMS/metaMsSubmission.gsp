<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>MetaMS submission</title>    
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
	
	<!-- Show messages -->
	<g:if test="${flash.message}">
	  		<div class="alert alert-block alert-success">
	  			<button type="button" class="close" data-dismiss="alert">&times;</button>
	  			<strong>OK: </strong>${flash.message}
	  		</div>
	</g:if>
 
    <h3>Submission parameters</h3>
		
	<g:uploadForm action="runMetaMS" class="form-horizontal">
	 		
 		<div class="col-md-4">	 		 		
 			<div class="form-group">	
				<label for="project">Description</label>
				<input class="form-control" name="comment">
	   		</div> <!-- form-group -->
 		
	   		<div class="form-group">	
				<label for="project">Minimal RT</label>
				<input class="form-control" name="minRt">
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="project">Maximal RT</label>
				<input class="form-control" name="maxRt">
	   		</div> <!-- form-group -->
			
			<div class="form-group checkbox">
				<label>					
					<input type="checkbox" name="identification" >					
					Feature annotation
				</label>
			</div>
			
			<div class="form-group"></div>
			
			<div class="form-group">
				<input onclick="$(this).button('loading')" data-loading-text="Starting.." class="btn btn-primary" type="submit" value="Submit">
				<g:link action="acquired" controller="runs" class="btn btn-warning">Cancel</g:link>
			</div>
	  	</div> <!-- col-md-4 -->
	  	
    </g:uploadForm>

</div> <!-- /container -->


  

</body>
</html>
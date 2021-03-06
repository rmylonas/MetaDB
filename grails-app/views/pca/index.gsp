<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Plot PCA</title>    
</head>
<body>

 <div class="container">
	
	 	<h4>Plot PCA</h4>
	
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
	
	<g:uploadForm action="plotPCA" class="form-horizontal">
		<div class="col-md-4">	
						
	 		<div class="form-group">
	 			<label for="factorSelection">Color by</label>
				<g:select class="form-control input" name="factorSelection" from="${session.factorList}" />
		    </div>
			
			<div class="form-group checkbox">
				<label>					
					<input type="checkbox" name="sqrtScaling" >					
					Square root scaling
				</label>
			</div>
			
			<div class="form-group checkbox">
				<label>					
					<input type="checkbox" name="sumNorm" >					
					TIC normalization
				</label>
			</div>
			
			<div class="form-group"> </div>
			
			<div class="form-group">
				<input class="btn btn-primary" type="submit" value="Plot">
				<g:link action="index" controller="metaMS" class="btn btn-warning">Cancel</g:link>
			</div>
			
		</div>
    </g:uploadForm>
	

</div> <!-- /container -->

</body>
</html>
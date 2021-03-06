<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Runs</title>    
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
	
 <g:uploadForm action="uploadAssayNames" class="form-horizontal">
		<div class="form-group">
    		<label class="control-label" for="assayNames">Assay names</label>
		    <div class="controls">
		      <g:textArea name="assayNames" rows="30" cols="50"/>
		    </div>
  		</div>
				
		<div class="form-group">
			<div class="controls">
				<input onclick="$(this).button('loading')" data-loading-text="Uploading.." class="btn btn-primary" type="submit" value="Upload">
				<g:link action="randomized" controller="runs" class="btn btn-warning">Cancel</g:link>
			</div>
		</div>
    </g:uploadForm>
	    
</div> <!-- /container -->

</body>
</html>
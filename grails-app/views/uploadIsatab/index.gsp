<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Upload ISAtab</title>    
</head>
<body>

 <div class="container">
    <h3>Upload ISAtab zip file</h3>
    
    <h5>Upload a zipped ISAtab folder</h5>
      
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
	

	
	<g:uploadForm action="upload" role="form">
		<div class="col-xs-4">
			<div class="form-group">
		    	<label for="isaTabInput">ISAtab file</label>
				<input name="isaTabFile" class="form-control" type="file" style="display:none">
				
				<div class="input-group">	
					<input id="isaTabInput" class="form-control" type="text">
					<span class="input-group-btn">
						<button type="button" class="btn btn-default" onclick="$('input[name=isaTabFile]').click();">Browse</button>
					</span>
				</div>
				
			</div> <!-- /form-group -->
			
			<div class="form-group">
				<button onclick="$(this).button('loading')" data-loading-text="Uploading.." class="btn btn-primary" type="submit">Upload</button>
				<g:link action="index" controller="studies" class="btn btn-warning">Cancel</g:link>
			</div>
			
		</div> <!-- /col-xs-3 -->
		
    </g:uploadForm>

</div> <!-- /container -->


    <script type="text/javascript">
		$('input[name=isaTabFile]').change(function() {
			$('#isaTabInput').val($(this).val());
		});
	</script>

</body>
</html>
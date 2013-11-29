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
	
	 <g:uploadForm action="uploadExtracted" role="form">
		<div class="col-xs-4">
			<div class="form-group">
		    	<label for="extractedInput">ZIP file containing extracted spectra files</label>
				<input name="extractedFile" class="form-control" type="file" style="display:none">
				
				<div class="input-group">	
					<input id="extractedInput" class="form-control" type="text">
					<span class="input-group-btn">
						<button type="button" class="btn btn-default" onclick="$('input[name=extractedFile]').click();">Browse</button>
					</span>
				</div>
				
			</div> <!-- /form-group -->
			
			<div class="form-group">
				<button class="btn btn-primary" type="submit">Upload</button>
			</div>
			
		</div> <!-- /col-xs-3 -->
		
    </g:uploadForm>
	    
</div> <!-- /container -->

    <script type="text/javascript">
		$('input[name=extractedFile]').change(function() {
			$('#extractedInput').val($(this).val());
		});
	</script>

</body>
</html>
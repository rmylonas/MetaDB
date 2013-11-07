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
  		<div class="alert alert-block alert-error">
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
	
	<g:uploadForm action="upload" class="form-horizontal">
	
		<div class="control-group">
	    	<label class="control-label" for="isaTabInput">ISAtab file</label>
	    	<div class="controls">
				<input name="isaTabFile" type="file" style="display:none">
				<div class="input-append">
					<input id="isaTabInput" class="input-large" type="text">
					<a class="btn" onclick="$('input[name=isaTabFile]').click();">Browse</a>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="controls">
				<input class="btn btn-primary" type="submit" value="Upload">
			</div>
		</div>
		
    </g:uploadForm>
    
    <g:if test="${flash.parsedFiles}">
  		<h5>Processed files:</h5>
	</g:if>
    
    <ul>
	    <g:each in="${flash.parsedFiles}">
	    	<li><a href="download?filepath=${it.filepath}&filename=${it.name}">${it.name}</a></li>
		</g:each>
	</ul>

</div> <!-- /container -->


    <script type="text/javascript">
		$('input[name=isaTabFile]').change(function() {
		$('#isaTabInput').val($(this).val());
		});
	</script>

</body>
</html>
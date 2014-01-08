<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>New instrument</title>    
</head>
<body>

 <div class="container">
    <h3>New instrument</h3>

	<g:uploadForm action="saveNewInstrument" class="form-horizontal">
	 		
 		<div class="col-md-4">	 		 		
	   		<div class="form-group">	
				<label for="project">Name</label>
				<input class="form-control" name="name" >
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="project">MetaboLigths name</label>
				<input class="form-control" name="metabolightsName" >
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="project">BookingSystem name</label>
				<input class="form-control" name="bookingSystemName" >
	   		</div> <!-- form-group -->
			
			<div class="form-group">	
				<label for="project">Chromatrography</label>
				<select class="form-control" name="chromatography" >
					<option value="LC">LC</option>
					<option value="GC">GC</option>
				</select>
	   		</div> <!-- form-group -->
			
			<div class="form-group">
				<input class="btn btn-primary" type="submit" value="Save">
				<g:link action="index" controller="instrument" class="btn btn-warning">Cancel</g:link>
			</div>
	  	</div> <!-- col-md-4 -->
	  	
    </g:uploadForm>
	    
</div> <!-- /container -->


</body>
</html>
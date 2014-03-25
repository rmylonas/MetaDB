<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Manage instruments</title>    
</head>
<body>

 <div class="container">
 
 <div class="container">
 	<g:link action="index" controller="instrument" class="btn btn-default btn-sm">Back</g:link>
    <h3>Instrument details</h3>

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
	
	<g:uploadForm action="updateInstrument" class="form-horizontal">
	 		
 		<div class="col-md-4">	 		 		
	   		<div class="form-group">	
				<label for="project">Name</label>
				<input class="form-control" name="name" value="${session.instrument.name}">
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="project">MetaboLigths name</label>
				<input class="form-control" name="metabolightsName" value="${session.instrument.metabolightsName}">
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="project">BookingSystem name</label>
				<input class="form-control" name="bookingSystemName" value="${session.instrument.bookingSystemName}">
	   		</div> <!-- form-group -->
			
			<div class="form-group">	
				<label for="project">Chromatrography</label>
				<select class="form-control" name="chromatography" >
					<option value="LC" >LC</option>
					<option value="GC" ${(session.instrument.chromatography == 'GC') ? ('selected="selected"') : ('') }>GC</option>
				</select>
	   		</div> <!-- form-group -->
			
			<div class="form-group">
				<input class="btn btn-primary" type="submit" value="Update details">
			</div>
	  	</div> <!-- col-md-4 -->
	  	
    </g:uploadForm>
 </div>  

 <div class="container">
    <h3>Methods</h3>
    
    <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>Name</th>
              <th>Description</th>
              <th></th>
            </tr>
          </thead>
          
          <tbody id="table">
          <g:each in="${session.instrument.methods}">
            	<tr>
            			<td><g:link action='methodDetail' params="${[id: it.id]}">${it.name}</g:link></td>
            			<td>${it.description}</td>
            			<td><g:link action="deleteMethod" params="${[id: it.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link></td>
	 			</tr>
		  </g:each>
		  </tbody>
        
	  </table>
	  
	  <g:link action="newMethod" controller="instrument" class="btn btn-primary">New method</g:link>
	 
	</div> 
	 
</div> <!-- /container -->


</body>
</html>
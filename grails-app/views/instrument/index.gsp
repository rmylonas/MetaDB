<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Manage instruments</title>    
</head>
<body>

 <div class="container">
    <h3>Instruments</h3>

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

	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>Name</th>
              <th>MetaboLigths name</th>
              <th>BookingSystem name</th>
              <th>Chromatrography</th>
              <th></th>
            </tr>
          </thead>
          
          <tbody id="table">
          <g:each in="${flash.instrumentList}">
            	<tr>
            			<td><g:link action='detail' params="${[id: it.id]}">${it.name}</g:link></td>
            			<td>${it.metabolightsName}</td>
            			<td>${it.bookingSystemName}</td>
            			<td>${it.chromatography}</td>
            			<td><g:link action="delete" params="${[id: it.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link></td>
	 			</tr>
		  </g:each>
		  </tbody>
        
	  </table>
	  
	  <g:link action="newInstrument" controller="instrument" class="btn btn-primary">New instrument</g:link>
	    
</div> <!-- /container -->


</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Manage MetaMS settings</title>    
</head>
<body>

 <div class="container">
    <h3>MetaMS identification DB</h3>

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
              <th>Abolute path</th>
              <th>Creation date</th>
              <th></th>
            </tr>
          </thead>
          
          <tbody id="table">
          <g:each in="${metaMsDb}">
            	<tr>
            			<td>${it.name}</td>
            			<td>${it.rDataPath}</td>
            			<td>${it.dateCreated}</td>
            			<td><g:link action="delete" params="${[id: it.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link></td>
	 			</tr>
		  </g:each>
		  </tbody>
        
	  </table>
	  
	  <g:link action="newDb" class="btn btn-primary">New database</g:link>
	    
</div> <!-- /container -->


</body>
</html>
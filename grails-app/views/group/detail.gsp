<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Manage groups</title>    
</head>
<body>

 <div class="container">
 
 <div class="container">
 	<g:link action="index" controller="group" class="btn btn-default btn-sm">Back</g:link>
    <h3>Group details</h3>

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
	
	<g:uploadForm action="updateGroup" class="form-horizontal">
	 		
 		<div class="col-md-4">	 		 		
	   		<div class="form-group">	
				<label for="project">Name</label>
				<input class="form-control" name="name" value="${session.group.name}">
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="project">Description</label>
				<input class="form-control" name="description" value="${session.group.description}">
	   		</div> <!-- form-group -->
			
			<div class="form-group">
				<input class="btn btn-primary" type="submit" value="Update details">
			</div>
	  	</div> <!-- col-md-4 -->
	  	
    </g:uploadForm>
 </div>  
 
 <div class="container">
    <h3>Projects</h3>
    
    <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>Name</th>
              <th>Description</th>
              <th></th>
            </tr>
          </thead>
          
          <tbody id="table">
          <g:each in="${session.group.projects}">
            	<tr>
            			<td><g:link action='projectDetail' params="${[id: it.id]}">${it.name}</g:link></td>
            			<td>${it.description}</td>
            			<td><g:link action="deleteProject" params="${[id: it.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link></td>
	 			</tr>
		  </g:each>
		  </tbody>
        
	  </table>
	  
	  <g:link action="newProject" controller="group" class="btn btn-primary">New project</g:link>
	 
	</div> 
	 
</div> <!-- /container -->


</body>
</html>
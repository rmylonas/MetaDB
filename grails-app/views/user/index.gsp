<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Manage users</title>    
</head>
<body>

 <div class="container">
    <h3>Users</h3>

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
              <th>Working directory</th>
              <th>Isatab directory</th>
              <th>Admin</th>
              <td></td>
            </tr>
          </thead>
          
          <tbody id="table">
          <g:each in="${session.userList}">
            	<tr>
            			<td><g:link action='detail' params="${[id: it.id]}">${it.username}</g:link></td>
            			<td>${it.workDir}</td>
            			<td>${session.workDir + it.workDir + '/isatab'}         			
            			<!-- show OK sign if the user is admin -->
            			<g:if test="${it.getAuthorities().toList().get(0).authority == 'ROLE_ADMIN'}">
     						<td><span class="glyphicon glyphicon-ok"></span></td>
          				</g:if>
          				<g:else>
          					<td></td>
          				</g:else>
          				<g:if test="${it.username != sec.username().toString()}">
        					<td><g:link action="delete" params="${[id: it.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link></td>
           				</g:if>
          				<g:else>
          					<td></td>
          				</g:else>         				
 	 			</tr>
		  </g:each>
		  </tbody>
        
	  </table>
	  
	  <g:link action="newUser" controller="user" class="btn btn-primary">New user</g:link>
	    
</div> <!-- /container -->


</body>
</html>
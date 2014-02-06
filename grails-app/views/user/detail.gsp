<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Update user</title>    
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
 
    <h3>Update user</h3>

	<g:uploadForm action="saveUserUpdate" class="form-horizontal">
	 		
 		<div class="col-md-4">	 		 		
	   		<div class="form-group">	
				<label for="name">Name</label>
				<input class="form-control" name="name" value="${session.user.username}">
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="password">New password</label>
				<input type="password" class="form-control" name="password" >
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="retypedPassword">Retype password</label>
				<input type="password" class="form-control" name="retypedPassword" >
	   		</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="workDir">Data storage path </label>
				<p class="help-block">Root directory: <em>${session.workDir}</em></p>
				<input class="form-control" name="workDir" value="${session.user.workDir}">
	   		</div> <!-- form-group -->
			
			<div class="form-group checkbox">
				<label>					
					<input type="checkbox" name="admin" ${session.isAdmin}>					
					Administrator rights
				</label>
			</div>
			
			<!-- placeholder for layout reason -->
			<div class="form-group checkbox">
			</div>
			
			
			<div class="form-group">
				<input class="btn btn-primary" type="submit" value="Save">
				<g:link action="index" controller="user" class="btn btn-warning">Cancel</g:link>
			</div>
	  	</div> <!-- col-md-4 -->
	  	
    </g:uploadForm>
	    
</div> <!-- /container -->


</body>
</html>
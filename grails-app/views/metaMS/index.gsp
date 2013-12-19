<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>MetaMS submissions</title>    
</head>
<body>

 <div class="container">
	
	 	<h4>MetaMs submissions</h4>
	
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
              <th>Status</th>
              <th>Directory path</th>
            </tr>
          </thead>
          
          <tbody id="submissionTable">
          <g:each var="submission" in="${flash.metaMsSubmissions}">
          	   <g:if test="${submission.status == 'failed'}">      	
            		<tr class="danger">
               </g:if>
                <g:if test="${submission.status == 'running'}">      	
            		<tr class="warning">
               </g:if>	
                <g:if test="${submission.status == 'done'}">
            		<tr class="success">
               </g:if>	
            			<td>${submission.name}</td>
            			<td>${submission.status}</td>
            			<td>${submission.workDir}</td>
				</tr> 			
		  </g:each>
		  </tbody>
		  
	</table>

</div> <!-- /container -->


  

</body>
</html>
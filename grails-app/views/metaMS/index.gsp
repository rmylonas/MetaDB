<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>MetaMS submissions</title>    
</head>
<body>

 <div class="container">
	
	 	<h4>MetaMS submissions</h4>
	
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
              <th>Id</th>
              <th>Status</th>
              <th>Comment</th>
              <th>Min RT</th>
              <th>Max RT</th>
              <th>Selected runs</th>
              <!-- <th>Directory path</th> -->
              <th></th>
            </tr>
          </thead>
          
          <tbody id="submissionTable">
          <g:each var="submission" in="${session.metaMsSubmissions}">          	
               		<tr>
               			<td><g:link action="details" params="${[id: submission.id]}">${submission.name}</g:link></td>
            			
            			<!-- put the corresponding color to the status -->
            			<td>
	            			<g:if test="${submission.status == 'failed'}">
            					<span class="label label-danger">${submission.status}</span>
		               		</g:if>
		               		<g:if test="${submission.status == 'running'}">
		               			<span class="label label-info">${submission.status}</span>
		               		</g:if>
		               		<g:if test="${submission.status == 'done'}">
		               			<span class="label label-success">${submission.status}</span>
		               		</g:if>
	            		</td>
            		
            			<td>${submission.comment}</td>
            			<td>${submission.rtMin}</td>
            			<td>${submission.rtMax}</td>
            			<td>${submission.selectedRuns.size()}</td>
            			<!-- <td>${submission.workDir}</td>  -->
            			
            			<!-- put the corresponding color to the status -->
            			<td>
            				<!-- params="[submissionId: ${submission.id}]" -->
            				<!-- <g:link action="delete" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link> -->
            			<g:if test="${submission.status == 'done'}">
           					<g:link action="downloadZip" id="${submission.id}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-down"></span>ZIP</g:link>
           					<g:link action="index" controller="pca" id="${submission.id}" class="btn btn-default btn-xs">PCA</g:link>
               			</g:if>
               			</td>
				</tr> 			
		  </g:each>
		  </tbody>
		  
	</table>

</div> <!-- /container -->


  

</body>
</html>
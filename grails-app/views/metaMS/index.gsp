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
              <th>Id</th>
              <th>Status</th>
              <th>Selected</th>
              <th>Directory path</th>
              <th></th>
            </tr>
          </thead>
          
          <tbody id="submissionTable">
          <g:each var="submission" in="${flash.metaMsSubmissions}">
          
          	<!-- put the corresponding color to the status -->
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
            			<td>${submission.selectedRuns.size()}</td>
            			<td>${submission.workDir}</td>
            			
            			<!-- put the corresponding color to the status -->
            			<td>
            				<!-- params="[submissionId: ${submission.id}]" -->
            				<g:link action="delete" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link>
            			<g:if test="${submission.status == 'done'}">
           					<g:link action="downloadRData" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-down"></span>  RData</g:link>
           					<g:link action="downloadCSV" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-down"></span>  CSV</g:link>
           					<g:link action="plotPCA" class="btn btn-default btn-xs">PCA</g:link>
               			</g:if>
               			</td>
				</tr> 			
		  </g:each>
		  </tbody>
		  
	</table>

</div> <!-- /container -->


  

</body>
</html>
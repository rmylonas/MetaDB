<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Assays</title>    
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
	
		<!-- Show message -->
    <g:if test="${flash.message}">
  		<div class="alert alert-block alert-success">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>OK: </strong>${flash.message}
  		</div>
	</g:if>
 
	<!-- "Are you sure" popup when deleting data -->
	<div id="myModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	   <div class="modal-dialog modal-sm">
	      <div class="modal-content">
	      
	        <div class="modal-header">
	          <h4 class="modal-title" id="mySmallModalLabel">Are you sure?</h4>
	        </div>    
	
	      <div class="modal-body">
	        <g:link action="delete" class="btn btn-danger">Delete</g:link>
	        <button type="button" class="btn btn-warning" data-dismiss="modal">Cancel</button>
	      </div>
	      </div><!-- /.modal-content -->
	   </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
 
    <h3>Assays ${(id?"from Study"+id:'')}</h3>

	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>Access code</th>
              <th>Name</th>
              <th>Instrument</th>
              <th>Method</th>
              <th>Polarity</th>
              <th>Group</th>
              <th>Project</th>
              <th>Creation date</th>
              <th>Owner</th>
              <th></th>
            </tr>
          </thead>
          
          <tbody>
          <g:each var="assay" in="${session.assays}">         	 
            	<tr>
            		<g:each in="${assay}">
            			<td><g:link controller='runs' params="${[id: it.id]}">${it.accessCode.code}</g:link></td>
            			<td>${it.shortName}</td>
            			<td>${it.instrument.name}</td>
            			<td>${it.method.name}</td>
            			<td>${it.instrumentPolarity}</td>
            			<td>${it.group.name}</td>
            			<td>${it.project.name}</td>
            			<td>${it.dateCreated}</td>
            			<td>${it.owner.username}</td>
            			
            			<!-- if we're a user, we only let you delete if its your own -->
            			<sec:access expression="hasRole('ROLE_USER')">
          					<g:if test="${it.owner.username == sec.username().toString()}">
          						<td><button onclick="prepareModal('assay', ${it.id})" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></button></td>
          					</g:if>
          					<g:else>
          						<td></td>
          					</g:else>
          				</sec:access>
          				
          				<!-- admin can delete everything -->
          				<sec:access expression="hasRole('ROLE_ADMIN')">
          					<td><button onclick="prepareModal('assay', ${it.id})" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></button></td>
          				</sec:access>
            			
            		</g:each>
	 			</tr>	 		
		  </g:each>
		  </tbody>
        
	  </table>
	  
	  <g:myPaginate total="${session.totalEntries}" />
	    
</div> <!-- /container -->

<script>

	function prepareModal(target, id){
		// add the right id to the end of the delete link (in Modal)
		var newLink = $( "a[href*='delete']" ).attr("href") + "/" + id;
		$( "a[href*='delete']" ).attr("href", newLink);
		$('#mySmallModalLabel').text("Are you sure you want to delete " + target + " #" + id + "?");
		$('#myModal').modal();		
	}

</script>


</body>
</html>
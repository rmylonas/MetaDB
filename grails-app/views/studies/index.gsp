<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Studies</title>    
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
 
    <h3>Studies</h3>

	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Description</th>
              <!-- <th>Filename</th>  -->
              <th>Group</th>
              <th>Project</th>
              <th>Creation date</th>
              <th>Owner</th>
              <th></th>
            </tr>
          </thead>
          
          <tbody id="table">
          <g:each var="study" in="${session.studies}">
            	<tr>
            		<g:each in="${study}">
            			<td><g:link controller='assays' params="${[id: it.id]}">${it.identifier}</g:link></td>
            			<td>${it.title}</td>
            			<td id="popover_${it.id}" data-container="body" data-toggle="popover" data-placement="bottom" data-content="${it.description}">${it.description.substring(0,(it.description.size() < 50)?(it.description.size()):(50))}
            				  <g:if test="${it.description.size() >= 50}">
            				  	...
              				  </g:if>
            			</td>
            			<!-- <td>${it.originalFilename}</td> -->
            			<td>${it.group.name}</td>
            			<td>${it.project.name}</td>
            			<td>${it.dateCreated}</td>
            			<td>${it.owner.username}</td>
            			
            			<!-- if we're a user, we only let you delete if its your own -->
            			<sec:access expression="hasRole('ROLE_USER')">
          					<g:if test="${it.owner.username == sec.username().toString()}">
          						<td><button onclick="prepareModal('study', ${it.id})" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></button></td>
          					</g:if>
          					<g:else>
          						<td></td>
          					</g:else>
          				</sec:access>
          				
          				<!-- admin can delete everything -->
          				<sec:access expression="hasRole('ROLE_ADMIN')">
          					<td><button onclick="prepareModal('study', ${it.id})" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></button></td>
          				</sec:access>
            			
            		</g:each>  	
	 			</tr>
		  </g:each>
		  </tbody>
        
	  </table>
	    
	  <g:myPaginate total="${session.totalEntries}" />
	    
</div> <!-- /container -->

<script>

$(function() {
	$('#table tr td').each(function(i){
		 // if id contains "popover", we add popover
		 if(this.id){$(this).popover()}
	});
});


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
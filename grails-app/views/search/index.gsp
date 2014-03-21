<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Search</title>    
</head>
<body>

<div class="container">

	<g:uploadForm action="index" class="form-horizontal">
	<div class="row">
	  <div class="col-lg-4">
	    <div class="input-group">
	      <input type="text" class="form-control" name='searchTerm' id='searchText'>
	      <span class="input-group-btn">
	        <button class="btn btn-default" type="submit">Search</button>
	      </span>
	    </div><!-- /input-group -->
	 </div><!-- /.col-lg-4 -->
	 <div class="col-lg-2">
	    <div class="input-group">
	      	<select class="form-control" name="level" >
				<option value="Assay">Assay</option>
				<!-- <option value="Study" ${cookie(name:'studySelected')}>Study</option>  -->
				<option value="Study" selected>Study</option>
			</select>
	    </div><!-- /input-group -->
	 </div><!-- /.col-lg-4 -->
	  <div class="col-lg-2">
		 <div class="checkbox">
		    <label>
		      <!-- <input type="checkbox" name="showAll" ${cookie(name:'showAll')}> Show all users -->
		      <input type="checkbox" name="showAll"> Show all users
		    </label>
	  	</div>
	 </div><!-- /.col-lg-4 -->
	</div><!-- /.row -->
	</g:uploadForm>
	
	
	     <!-- Show errors -->
    <g:if test="${flash.error}">
  		<div class="alert alert-block alert-danger">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>Error: </strong>${flash.error}
  		</div>
	</g:if>
	
	<!-- Show warning -->
    <g:if test="${flash.warning}">
  		<div class="alert alert-block alert-warning">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			${flash.warning}
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
	
	<g:if test="${session.assays}">
	
 	<h3>Assays</h3>

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
          							<td><button onclick="prepareModal('assay', ${it.id}, 'assays')" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></button></td>
          					</g:if>
          					<g:else>
          						<td></td>
          					</g:else>
          				</sec:access>
          				
          				<!-- admin can delete everything -->
          				<sec:access expression="hasRole('ROLE_ADMIN')">
          					<td><button onclick="prepareModal('assay', ${it.id}, 'assays')" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></button></td>
          				</sec:access>
            			
            		</g:each>
	 			</tr>	 		
		  </g:each>
		  </tbody>
        
	  </table>
	  
	  </g:if> <!-- / if flash.assays -->
	  
	  <g:if test="${session.studies}">
	  
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
            			
            				<!-- ZIP download button -->
 						<td><g:link controller='studies' action="downloadZip" id="${it.id}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-down"></span>ZIP</g:link></td>            			
            			
            			<!-- if we're a user, we only let you delete if its your own -->
            			<sec:access expression="hasRole('ROLE_USER')">
          					<g:if test="${it.owner.username == sec.username().toString()}">
          						<td><button onclick="prepareModal('study', ${it.id}, 'studies')" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></button></td>
          					</g:if>
          					<g:else>
          						<td></td>
          					</g:else>
          				</sec:access>
          				
          				<!-- admin can delete everything -->
          				<sec:access expression="hasRole('ROLE_ADMIN')">
          						<td><button onclick="prepareModal('study', ${it.id}, 'studies')" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></button></td>
          				</sec:access>
            			
            		</g:each>  	
	 			</tr>
		  </g:each>
		  </tbody>
        
	  </table>
	  
	  </g:if> <!-- / if flash.studies -->

		<!--  make correct pagination in case a search was done -->
		<g:if test="${flash.lastSearchTerm}">
	    	<g:myPaginate total="${session.totalEntries}" params='[searchTerm: "${flash.lastSearchTerm}", level: "${flash.lastLevel}", showAll: "${flash.lastShowAll}"]'/>
	    </g:if>
	    <g:else>
	    	<g:myPaginate total="${session.totalEntries}" />
	    </g:else>
	    
	    
	    
</div> <!-- /container -->

<script>

$(function() {
	$('#table tr td').each(function(i){
		 // if id contains "popover", we add popover
		 if(this.id){$(this).popover()}
	});

	$('#searchText').focus();
});


function prepareModal(target, id, controller){
	// add the right id to the end of the delete link (in Modal)
	var newLink = $( "a[href*='delete']" ).attr("href") + "/" + id;
	// replace the "search" controller by "assay" or "study"
	newLink = newLink.replace("search", controller);
	// replace the href
	$( "a[href*='delete']" ).attr("href", newLink);
	// change Modal title
	$('#mySmallModalLabel').text("Are you sure you want to delete " + target + " #" + id + "?");
	// start Modal
	$('#myModal').modal();		
}

</script>

</body>
</html>
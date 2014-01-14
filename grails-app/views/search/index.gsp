<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Runs</title>    
</head>
<body>



<div class="container">

	<g:uploadForm action="index" class="form-horizontal">
	<div class="row">
	  <div class="col-lg-4">
	    <div class="input-group">
	      <input type="text" class="form-control" name='searchTerm'>
	      <span class="input-group-btn">
	        <button class="btn btn-default" type="submit">Search</button>
	      </span>
	    </div><!-- /input-group -->
	 </div><!-- /.col-lg-4 -->
	 <div class="col-lg-2">
	    <div class="input-group">
	      	<select class="form-control" name="level" >
				<option value="Assay">Assay</option>
				<option value="Study">Study</option>
			</select>
	    </div><!-- /input-group -->
	 </div><!-- /.col-lg-4 -->
	  <div class="col-lg-2">
		 <div class="checkbox">
		    <label>
		      <input type="checkbox" name="showAll"> Show all entries
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
	
	
	<g:if test="${flash.assays}">
	
 	<h3>Assays</h3>

	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>Access code</th>
              <th>Name</th>
              <th>Instrument</th>
              <th>Method</th>
              <th>Polarity</th>
              <th>Owner</th>
              <th></th>
            </tr>
          </thead>
          
          <tbody>
          <g:each var="assay" in="${flash.assays}">         	 
            	<tr>
            		<g:each in="${assay}">
            			<td><g:link controller='runs' params="${[id: it.id]}">${it.accessCode.code}</g:link></td>
            			<td>${it.shortName}</td>
            			<td>${it.instrument.name}</td>
            			<td>${it.method.name}</td>
            			<td>${it.instrumentPolarity}</td>
            			<td>${it.owner.username}</td>
            			
            			<!-- if we're a user, we only let you delete if its your own -->
            			<sec:access expression="hasRole('ROLE_USER')">
          					<g:if test="${it.owner.username == sec.username().toString()}">
          						<td><g:link action="delete" controller="assays" params="${[id: it.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link></td>
          					</g:if>
          					<g:else>
          						<td></td>
          					</g:else>
          				</sec:access>
          				
          				<!-- admin can delete everything -->
          				<sec:access expression="hasRole('ROLE_ADMIN')">
          					<td><g:link action="delete" controller="assays" params="${[id: it.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link></td>
          				</sec:access>
            			
            		</g:each>
	 			</tr>	 		
		  </g:each>
		  </tbody>
        
	  </table>
	  
	  </g:if> <!-- / if flash.assays -->
	  
	  <g:if test="${flash.studies}">
	  
	   <h3>Studies</h3>

	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Description</th>
              <th>Filename</th>
              <th>Group</th>
              <th>Project</th>
              <th>Creation date</th>
              <th>Owner</th>
              <th></th>
            </tr>
          </thead>
          
          <tbody id="table">
          <g:each var="study" in="${flash.studies}">
            	<tr>
            		<g:each in="${study}">
            			<td><g:link controller='assays' params="${[id: it.id]}">${it.identifier}</g:link></td>
            			<td>${it.title}</td>
            			<td id="popover_${it.id}" data-container="body" data-toggle="popover" data-placement="bottom" data-content="${it.description}">${it.description.substring(0,(it.description.size() < 50)?(it.description.size()):(50))}
            				  <g:if test="${it.description.size() >= 50}">
            				  	...
              				  </g:if>
            			</td>
            			<td>${it.originalFilename}</td>
            			<td>${it.group.name}</td>
            			<td>${it.project.name}</td>
            			<td>${it.dateCreated}</td>
            			<td>${it.owner.username}</td>
            			
            			<!-- if we're a user, we only let you delete if its your own -->
            			<sec:access expression="hasRole('ROLE_USER')">
          					<g:if test="${it.owner.username == sec.username().toString()}">
          						<td><g:link action="delete" params="${[id: it.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link></td>
          					</g:if>
          					<g:else>
          						<td></td>
          					</g:else>
          				</sec:access>
          				
          				<!-- admin can delete everything -->
          				<sec:access expression="hasRole('ROLE_ADMIN')">
          					<td><g:link action="delete" params="${[id: it.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link></td>
          				</sec:access>
            			
            		</g:each>  	
	 			</tr>
		  </g:each>
		  </tbody>
        
	  </table>
	  
	  </g:if> <!-- / if flash.studies -->
	    
</div> <!-- /container -->

<script>

$(function() {
	$('#table tr td').each(function(i){
		 // if id contains "popover", we add popover
		 if(this.id){$(this).popover()}
	});
});

</script>

</body>
</html>
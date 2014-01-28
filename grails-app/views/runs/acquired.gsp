<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Acquired runs</title>    
</head>
<body>

<script>
// select all runs
function selectAll() {
	$('#runTable').find('input[type=checkbox]').each(function(){ this.checked='checked'});
}

// de-select all runs
function selectNone() {
	$('#runTable').find('input[type=checkbox]').each(function(){ this.checked=''});
}

// submit runs
function startMetaMs(){
	$('[name="runForm"]').submit();
}

</script>

<div class="container">
 
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
  			<strong>Warning: </strong>${flash.warning}
  		</div>
	</g:if>
	
		<!-- Show message -->
    <g:if test="${flash.message}">
  		<div class="alert alert-block alert-success">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>OK: </strong>${flash.message}
  		</div>
	</g:if>
	
 	<div class="row">
 		<div class="col-md-4">
    		<h3>Runs <em>${session.assay.shortName}</em></h3>
    	</div>
    	
    	<!-- if the assay status is acquired, we can add the extracted files -->
     	<div class="btn-group">
     		<g:link class="btn btn-primary" action="downloadAcquiredCSV">Download CSV</g:link>
     		<div class="btn-group">
			  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
			    Add processed files <span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu" role="menu">
			    <li><g:link action="chooseExtracted">Upload ZIP file</g:link></li>
			    <li><g:link action="localExtractedUpload">Check local directory</g:link></li>
			  </ul>
			</div>
   		 	<button class="btn btn-primary" onClick="startMetaMs()" >Start MetaMS</button>		
		</div>
 
	</div> <!-- row -->

	  <div class="row">
		Select
		<div class="btn-group">
			<button class="btn btn-default btn-xs" onClick="selectAll()">all</button>
			<button class="btn btn-default btn-xs" onClick="selectNone()">none</button>
		</div>
	</div>

	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>Selected</th>
              <th>Assay name</th>
              <th>Additional acquisition</th>
              <th>Status</th>
              <th>Sample name</th>
            </tr>
          </thead>
          
          <g:form name="runForm" controller="metaMS" action="metaMsSubmission">
          
          <tbody id="runTable">
          <g:each var="run" in="${session.runs}">
            		<tr>
            			<td><g:checkBox name="runSelection" value="${run.msAssayName}" checked="true" /></td>
            			<td>${run.msAssayName}</td>
            			
            			<!-- set flag if its an additional run -->
            			<g:if test="${run.additionalRun == true}">
            			<td><span class="glyphicon glyphicon-ok"></span></td>
            			</g:if>
		               	<g:else>
		               	<td></td>
		               	</g:else>
            			
            			<!-- color the status -->
            			<td>
            			<g:if test="${run.status == 'extracted'}">
            				<span class="label label-success">processed</span>
	               		</g:if>
	               		<g:else>
	               			<span class="label label-info">${run.status}</span>
	               		</g:else>
            			</td>
            			
            			<td>${run.sample.name}</td>
				</tr>
	 			
		  </g:each>
		  </tbody>
		  
		  </g:form>
        
	  </table>
	    
</div> <!-- /container -->

</body>
</html>
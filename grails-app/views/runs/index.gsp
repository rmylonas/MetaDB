<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Runs</title>    
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
    	
    	<div class="col-md-2">
   		 	<g:link action="downloadCsv" class="btn btn-primary"><span class="glyphicon glyphicon-arrow-down"></span>  CSV file</g:link>
		</div>
		
		<div class="col-md-2">
   		 	<g:link action="assayNames" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>  Assay names</g:link>
		</div>
 
 		<!-- if the assay status is acquired, we can add the extracted files -->
     	<div class="col-md-2">
   		 	<g:link action="chooseExtracted" class="btn btn-primary"><span class="glyphicon glyphicon-arrow-up"></span>  Extracted files</g:link>
		</div>
 		
 		<!-- if the assay status is extracted, we can submit to MetaMS -->
		<div class="col-md-2">
   		 	<button class="btn btn-primary" onClick="startMetaMs()" >MetaMS</button>			
		</div>

	</div> <!-- row -->
	
	<div class="row">
		<button class="btn btn-default" onClick="selectAll()">select all</button>
		<button class="btn btn-default" onClick="selectNone()">select none</button>
	</div>

	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>Selected</th>
              <th>Row nr</th>
              <th>Assay name</th>
              <th>Additional acquisition</th>
              <th>Status</th>
              <th>Sample name</th>
            </tr>
          </thead>
          
          <g:form name="runForm" controller="metaMS" action="metaMsSubmission">
          
          <tbody id="runTable">
          <g:each var="run" in="${flash.runs}">
            	<tr>	
            			<td><g:checkBox name="runSelection" value="${run.msAssayName}" checked="true" /></td>
            			<td>${run.rowNumber}</td>
            			<td>${run.msAssayName}</td>
            			<td></td>
            			<td>${run.status}</td>
            			<td>${run.sample.name}</td>
				</tr>
	 			
	 			<g:each var="additional" in="${run.additionalRuns}">
		 			<tr>	
		 					<td><g:checkBox name="runSelection" value="${additional.msAssayName}" checked="true" /></td>
	            			<td>${additional.rowNumber}</td>
	            			<td>${additional.msAssayName}</td>
	            			<td><span class="glyphicon glyphicon-ok"></span></td>
	            			<td>${additional.status}</td>
	            			<td>${additional.sample.name}</td>
					</tr>
	 			</g:each>
	 			
		  </g:each>
		  </tbody>
		  
		  </g:form>
        
	  </table>
	    
</div> <!-- /container -->

<script>
$(function() {
	$('#metams-button').popover();
});
</script>

</body>
</html>
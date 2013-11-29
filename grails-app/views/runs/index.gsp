<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Runs</title>    
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
 		 		<g:if test="${session.assay.status == 'acquired' || session.assay.status == 'extracted'}">
	     	<div class="col-md-2">
	   		 	<g:link action="chooseExtracted" class="btn btn-primary"><span class="glyphicon glyphicon-arrow-up"></span>  Extracted files</g:link>
			</div>
		</g:if>
 		
 		<!-- if the assay status is extracted, we can submit to MetaMS -->
 		<g:if test="${session.assay.status == 'extracted'}">
	     	<div class="col-md-2">
	   		 	<a href="#" class="btn btn-primary" data-toggle="popover" data-content="Nono, not yet.." role="button">MetaMS</a>
			</div>
		</g:if>		

	</div> <!-- row -->


	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>Row nr</th>
              <th>Assay name</th>
              <th>Status</th>
              <th>Sample name</th>
            </tr>
          </thead>
          
          <tbody>
          <g:each var="run" in="${flash.runs}">

            	<tr>
            		<g:each in="${run}">
            			<td>${it.rowNumber}</td>
            			<td>${it.msAssayName}</td>
            			<td>${it.status}</td>
            			<td>${it.sample.name}</td>
            		</g:each>  	
	 			</tr>
		  </g:each>
		  </tbody>
        
	  </table>
	    
</div> <!-- /container -->

</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Randomized runs</title>    
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
 
	</div> <!-- row -->

	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>Row nr</th>
              <th>Assay name</th>
              <th>Sample name</th>
            </tr>
         </thead>
          
         <tbody id="runTable">
          <g:each var="run" in="${flash.runs}">
            	<tr>
            		<td>${run.rowNumber}</td>
            		<td>${run.msAssayName}</td>     			
            		<td>${run.sample.name}</td>
				</tr>	 			
		  </g:each>
		  </tbody>
        
	  </table>
	    
</div> <!-- /container -->

</body>
</html>
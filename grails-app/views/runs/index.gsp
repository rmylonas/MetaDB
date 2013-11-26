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
  		<div class="alert alert-block alert-error">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>Error: </strong>${flash.error}
  		</div>
	</g:if>
	
 	<div class="row">
 		<div class="col-md-4">
    		<h3>Runs ${(id?"from Assay"+id:'')}</h3>
    	</div>
    	<div class="col-md-3 col-md-offset-5">
   		 	<g:link action="downloadCsv" class="btn btn-primary"><span class="glyphicon glyphicon-list-alt"></span>  CSV file</g:link>
		</div>
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
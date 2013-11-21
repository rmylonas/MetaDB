<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Runs</title>    
</head>
<body>

 <div class="container">
    <h3>Runs ${(id?"from Assay"+id:'')}</h3>

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
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Assays</title>    
</head>
<body>

 <div class="container">
    <h3>Available Assays ${(id?"from Study"+id:'')}</h3>

	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>Access code</th>
              <th>Name</th>
              <th>Instrument</th>
              <th>Description</th>   
            </tr>
          </thead>
          
          <g:each var="assay" in="${flash.assays}">
          	 <tbody>
            	<tr>
            		<g:each in="${assay}">
            			<td>${it.accessCode.code}</td>
            			<td>${it.name}</td>
            			<td>${it.instrument}</td>
            			<td>${it.description}</td>
            		</g:each>  	
	 			</tr>
	 		</tbody>
		  </g:each>
        
	  </table>
	    
</div> <!-- /container -->

</body>
</html>
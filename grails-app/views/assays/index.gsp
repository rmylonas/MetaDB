<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Assays</title>    
</head>
<body>

 <div class="container">
    <h3>Assays ${(id?"from Study"+id:'')}</h3>

	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>Access code</th>
              <th>Name</th>
              <th>Instrument</th>
              <th>Description</th>   
            </tr>
          </thead>
          
          <tbody>
          <g:each var="assay" in="${flash.assays}">         	 
            	<tr>
            		<g:each in="${assay}">
            			<td><g:link controller='runs' params="${[id: it.id]}">${it.accessCode.code}</g:link></td>
            			<td>${it.shortName}</td>
            			<td>${it.instrument.name}</td>
            			<td>${it.description}</td>
            		</g:each>
	 			</tr>	 		
		  </g:each>
		  </tbody>
        
	  </table>
	    
</div> <!-- /container -->

</body>
</html>
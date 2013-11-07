<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Studies</title>    
</head>
<body>

 <div class="container">
    <h3>Available studies</h3>

	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Description</th>
              <th>Filename</th>
              <th>Creation date</th>
            </tr>
          </thead>
          
          <g:each var="study" in="${flash.studies}">
          	 <tbody>
            	<tr>
            		<g:each in="${study}">
            			<td><g:link controller='assays' params="${[id: it.id]}">${it.identifier}</g:link></td>
            			<td>${it.title}</td>
            			<td>${it.description}</td>
            			<td>${it.iSATabFilePath}</td>
            			<td>${it.dateCreated}</td>
            		</g:each>  	
	 			</tr>
	 		</tbody>
		  </g:each>
        
	  </table>
	    
</div> <!-- /container -->

</body>
</html>
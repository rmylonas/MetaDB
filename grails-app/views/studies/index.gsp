<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Studies</title>    
</head>
<body>

 <div class="container">
    <h3>Studies</h3>

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
          
          <tbody>
          <g:each var="study" in="${flash.studies}">
            	<tr>
            		<g:each in="${study}">
            			<td><g:link controller='assays' params="${[id: it.id]}">${it.identifier}</g:link></td>
            			<td>${it.title}</td>
            			<td>${it.description.substring(0,(it.description.size() < 50)?(it.description.size()):(50))}
            				  <g:if test="${it.description.size() >= 50}">
            				  	...
            				  	<button type="button" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-resize-full"></span></button>
            				  </g:if>
            			</td>
            			<td>${it.iSATabFilePath}</td>
            			<td>${it.dateCreated}</td>
            		</g:each>  	
	 			</tr>
		  </g:each>
		  </tbody>
        
	  </table>
	    
</div> <!-- /container -->

</body>
</html>
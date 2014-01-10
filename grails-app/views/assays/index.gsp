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
              <th>Method</th>
              <th>Polarity</th>
              <th>Owner</th>
              <th></th>
            </tr>
          </thead>
          
          <tbody>
          <g:each var="assay" in="${flash.assays}">         	 
            	<tr>
            		<g:each in="${assay}">
            			<td><g:link controller='runs' params="${[id: it.id]}">${it.accessCode.code}</g:link></td>
            			<td>${it.shortName}</td>
            			<td>${it.instrument.name}</td>
            			<td>${it.method.name}</td>
            			<td>${it.instrumentPolarity}</td>
            			<td>${it.owner.username}</td>
            			
            			<!-- if we're a user, we only let you delete if its your own -->
            			<sec:access expression="hasRole('ROLE_USER')">
          					<g:if test="${it.owner.username == sec.username().toString()}">
          						<td><g:link action="delete" params="${[id: it.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link></td>
          					</g:if>
          					<g:else>
          						<td></td>
          					</g:else>
          				</sec:access>
          				
          				<!-- admin can delete everything -->
          				<sec:access expression="hasRole('ROLE_ADMIN')">
          					<td><g:link action="delete" params="${[id: it.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link></td>
          				</sec:access>
            			
            		</g:each>
	 			</tr>	 		
		  </g:each>
		  </tbody>
        
	  </table>
	    
</div> <!-- /container -->

</body>
</html>
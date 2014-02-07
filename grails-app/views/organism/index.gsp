<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Manage organism onthology</title>    
</head>
<body>

 <div class="container">
    <h3>Organism</h3>

	 <!-- Show errors -->
    <g:if test="${flash.error}">
  		<div class="alert alert-block alert-danger">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>Error: </strong>${flash.error}
  		</div>
	</g:if>
	
		<!-- Show message -->
    <g:if test="${flash.message}">
  		<div class="alert alert-block alert-success">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>OK: </strong>${flash.message}
  		</div>
	</g:if>

	  <table class="table table-striped">
	  
	  	<thead>
            <tr>
              <th>Name</th>
              <th>Alternative names</th>
              <th>description</th>
              <th></th>
            </tr>
          </thead>
          
          <tbody id="table">
          <g:each in="${session.organism}">
            	<tr>
            			<td><g:link action='editOrganism' params="${[id: it.id]}">${it.name}</g:link></td>
            			<td>${it.alternativeNames}</td>
            			<td id="popover_${it.id}" data-container="body" data-toggle="popover" data-placement="bottom" data-content="${it.description}">${it.description?.substring(0,(it.description?.size() < 50)?(it.description?.size()):(50))}
            				  <g:if test="${it.description?.size() >= 50}">
            				  	...
              				  </g:if>
            			</td>       			
        				<td><g:link action="deleteOrganism" params="${[id: it.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span></g:link></td>    				
 	 			</tr>
		  </g:each>
		  </tbody>
        
	  </table>
	  
	  <g:link action="newOrganism" class="btn btn-primary">New organism</g:link>
	    
</div> <!-- /container -->

<script>

$(function() {
	$('#table tr td').each(function(i){
		 // if id contains "popover", we add popover
		 if(this.id){$(this).popover()}
	});
});

</script>

</body>
</html>
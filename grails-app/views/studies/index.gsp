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
              <th>Group</th>
              <th>Project</th>
              <th>Creation date</th>
            </tr>
          </thead>
          
          <tbody id="table">
          <g:each var="study" in="${flash.studies}">
            	<tr>
            		<g:each in="${study}">
            			<td><g:link controller='assays' params="${[id: it.id]}">${it.identifier}</g:link></td>
            			<td>${it.title}</td>
            			<td id="popover_${it.id}" data-container="body" data-toggle="popover" data-placement="bottom" data-content="${it.description}">${it.description.substring(0,(it.description.size() < 50)?(it.description.size()):(50))}
            				  <g:if test="${it.description.size() >= 50}">
            				  	...
              				  </g:if>
            			</td>
            			<td>${it.originalFilename}</td>
            			<td>${it.group.name}</td>
            			<td>${it.project.name}</td>
            			<td>${it.dateCreated}</td>
            		</g:each>  	
	 			</tr>
		  </g:each>
		  </tbody>
        
	  </table>
	    
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
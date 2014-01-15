<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>ISAtab content</title>    
</head>
<body>

	  	<g:javascript>
			function updateProjects(data) {
				var $el = $("#project")
				// remove all options from select
				$el.empty()
				// add new ones
				$.each(data, function(k1, v1){
					$el.append($("<option></option>")
						.attr("value", v1.id).text(v1.name))
				})								
			}
		</g:javascript>	


 <div class="container">
 
     <!-- Show warning -->
    <g:if test="${session.already}">
  		<div class="alert alert-block alert-warning">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>Warning: </strong>A study with the same identifier already exists. New assays will be added to existing study.
  		</div>
	</g:if>
 
 
    <h3>ISAtab content</h3>
		
	<g:uploadForm action="insert" class="form-horizontal">
	
	 	<h4>Select assays</h4>
	
		  <div class="row">
		   	<div class="col-md-4">Study: <strong>${session.study.title}</strong></div>
		  </div>
	    	<g:each var="assay" in="${session.study.assays}">
	    		
	    			<!-- desactivate assays which are already inserted -->
	    			<g:if test="${session.already[assay.name]}">
	    				<div class="row text-muted">
	    					<div class="col-md-1 col-md-offset-1"><g:checkBox name="${assay.name + '_cb'}" disabled="${true}"/></div>
	    					<div class="col-md-2"><strong>already inserted</strong></div>
	    			</g:if>
	    			<g:elseif test="${! assay.instrument.methods}">
	    				<div class="row text-muted">
	    					<div class="col-md-1 col-md-offset-1"><g:checkBox name="${assay.name + '_cb'}" disabled="${true}"/></div>
	    					<div class="col-md-2"><strong>no valid instrument</strong></div>	    				
	    			</g:elseif>
	    			<g:else>
	    			<div class="row">	
	    				<div class="col-md-1 col-md-offset-1"><g:checkBox name="${assay.name + '_cb'}" value="${true}" /></div>
	    				<div class="col-md-2">
							<g:select class="form-control input-sm" name="${assay.name + '_me'}" optionValue="name" from="${assay.instrument.methods}" optionKey="id"/>
	    				</div>
	    			</g:else>
	    			<div class="col-md-2">${assay.shortName}</div>
	    			<div class="col-md-2">${assay.instrument.name}</div>
	    			<div class="col-md-2">${assay.instrumentPolarity}</div>
	    		</div>
	    </g:each>

		<!-- we can only add group info, if it is not a Study already existing -->
		<g:if test="${! session.already}">

 		<h4>Additional parameters</h4> 		
 		
 		<div class="col-md-4">
	 		<div class="form-group">
	 		<label for="group">Group</label>
		   		<g:select class="form-control input-sm" name="group" optionValue="name" from="${session.groups}" optionKey="id"
		   				onchange="${remoteFunction(
						            action:'ajaxProjects',
							   		params:'\'groupId=\' + this.value',
						            onSuccess:'updateProjects(data)')}"
		   		></g:select>
			</div> <!-- form-group -->
	   		
	   		<div class="form-group">	
				<label for="project">Project</label>
	   			<g:select class="form-control input-sm" name="project" optionValue="name" from="${session.projects}" optionKey="id" />
			</div> <!-- form-group -->
		</g:if>
		<g:else>
			<div class="col-md-4">
		</g:else>

		
			<div class="form-group">
				<input onclick="$(this).button('loading')" data-loading-text="Inserting.." class="btn btn-primary" type="submit" value="Insert">
				<g:link action="index" controller="uploadIsatab" class="btn btn-warning">Cancel</g:link>
			</div>
	  	</div> <!-- col-md-4 -->
	  	
	  	
	  	
    </g:uploadForm>

</div> <!-- /container -->


  

</body>
</html>
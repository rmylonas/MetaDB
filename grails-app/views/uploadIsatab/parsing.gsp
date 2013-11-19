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
 
    <h3>ISAtab content</h3>
		
	<g:uploadForm action="insert" class="form-horizontal">
	
	 	<h4>Select assays</h4>
	
	  	<g:each var="study" in="${session.investigation.studyList}">
		  <div class="row">
		   	<div class="col-md-4">Study: <strong>${study.title}</strong></div>
		  </div>
	    	<g:each var="assay" in="${study.assays}">
	    		<div class="row">
	    			<div class="col-md-1 col-md-offset-1"><g:checkBox name="${assay.name + '_cb'}" value="${true}" /></div>
	    			<div class="col-md-2">
						<g:select class="form-control input-sm" name="${assay.name + '_me'}" optionValue="name" from="${assay.instrument.methods}" optionKey="id"/>
	    			</div>
	    			<div class="col-md-2">${assay.shortName}</div>
	    			<div class="col-md-2">${assay.instrument.name}</div>
	    			<div class="col-md-2">${assay.instrumentPolarity}</div>
	    		</div>
	    	</g:each>
	  	</g:each>

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
			
			<div class="form-group">
				<input class="btn btn-primary" type="submit" value="Insert">
			</div>
	  	</div> <!-- col-md-4 -->
	  	
    </g:uploadForm>

</div> <!-- /container -->


  

</body>
</html>
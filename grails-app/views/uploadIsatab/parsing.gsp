<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>ISAtab content</title>    
</head>
<body>

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
	    			<div class="col-md-1 col-md-offset-1"><g:checkBox name="${assay.name}" value="${true}" /></div>
	    			<div class="col-md-2">
						<g:select class="form-control input-sm" name="method" optionValue="name" from="${assay.instrument.methods}" optionKey="id"/>
	    			</div>
	    			<div class="col-md-2">${assay.shortName}</div>
	    			<div class="col-md-2">${assay.instrument.name}</div>
	    			<div class="col-md-2">${assay.instrumentPolarity}</div>
	    		</div>
	    	</g:each>
	  	</g:each>

 		<h4>Additional parameters</h4>
				

		<input class="btn btn-primary" type="submit" value="Insert">

		
    </g:uploadForm>

</div> <!-- /container -->


  

</body>
</html>
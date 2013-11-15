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
		  <div class="row-fluid">
		   	<div class="span4">Study: <strong>${study.title}</strong></div>
		  </div>
	    	<g:each var="assay" in="${study.assays}">
	    		<div class="row-fluid">
	    			<div class="span1 offset1"><g:checkBox name="${assay.name}" value="${true}" /></div>
	    			<div class="span1">
						<g:select name="method" optionValue="name" from="${assay.instrument.methods}" optionKey="id"/>
	    			</div>
	    			<div class="span2">${assay.shortName}</div>
	    			<div class="span1"><strong>${assay.instrument.name}</strong></div>
	    			<div class="span1">${assay.instrumentPolarity}</div>
	    		</div>
	    	</g:each>
	  	</g:each>

 		<h4>Additional parameters</h4>
				

		<input class="btn btn-primary" type="submit" value="Insert">

		
    </g:uploadForm>

</div> <!-- /container -->


  

</body>
</html>
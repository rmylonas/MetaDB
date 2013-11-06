<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>ISAtab content</title>    
</head>
<body>

 <div class="container">
 
 	<!-- Show messages -->
	<g:if test="${flash.message}">
  		<div class="alert alert-block alert-success">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>OK: </strong>${flash.message}
  		</div>
	</g:if>
 
    <h3>ISAtab content</h3>
    
    <h5>Select assays to insert</h5>
		
	<g:uploadForm action="insert" class="form-horizontal">
	
	  <g:each var="study" in="${session.investigation.studyList}">
		  <div class="row-fluid">
		   	<div class="span4"><b>Study: </b>${study.title}</div>
		  </div>
	    	<g:each var="assay" in="${study.assays}">
	    		<div class="row-fluid">
	    			<div class="span5 offset1">${assay.name}</div>
	    			<div class="span1"><g:checkBox name="${assay.name}" value="${true}" /></div>
	    		</div>
	    	</g:each>
	  	</g:each>

		<input class="btn btn-primary" type="submit" value="Insert">

		
    </g:uploadForm>

</div> <!-- /container -->


  

</body>
</html>
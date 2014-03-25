<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>

    <div class="container">
      

	<g:uploadForm action="index" controller="search" class="form-horizontal" id="searchForm">
	<div class="row">
	  <div class="col-lg-4">
	    <div class="input-group">
	      <input type="text" class="form-control" name='searchTerm' id='searchText'>
	      <span class="input-group-btn">
	        <button class="btn btn-default" type="submit">Search</button>
	      </span>
	    </div><!-- /input-group -->
	 </div><!-- /.col-lg-4 -->
	 <div class="col-lg-2">
	    <div class="input-group">
	      	<select class="form-control" name="level" >
				<option value="Study" >Study</option>
				<option value="Assay">Assay</option>
			</select>
	    </div><!-- /input-group -->
	 </div><!-- /.col-lg-4 -->
	  <div class="col-lg-3">
		 <div class="checkbox">
		    <label>
		      <input type="checkbox" name="showAll" > Show data from all users
		    </label>
	  	</div>
	 </div><!-- /.col-lg-4 -->
	</div><!-- /.row -->
	</g:uploadForm>

	<div class="container">
	<h1>Welcome to MetaDB</h1>
	<p class="lead">
		<a href="https://github.com/rmylonas/MetaDB">MetaDB</a> is an open-source web application for metadata management and data processing of metabolomics data. 
		It is based on <a href="http://www.isa-tools.org/">ISA tab</a> as the input format. The analysis of untargeted data is done using the R package MetaMS. 
		This software is a project from <a href="http://www.fmach.it">Fondazione Edmund Mach</a>.
	</p>
	</div>

    </div> <!-- /container -->

<script>

$(function() {
	$('#searchText').focus();
});

</script>


</body>
</html>
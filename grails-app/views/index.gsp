<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>

    <div class="container">
      <h3>Welcome to MetaDB</h3>

	<g:uploadForm action="index" controller="search" class="form-horizontal">
	<div class="row">
	  <div class="col-lg-4">
	    <div class="input-group">
	      <input type="text" class="form-control" name='searchTerm'>
	      <span class="input-group-btn">
	        <button class="btn btn-default" type="submit">Search</button>
	      </span>
	    </div><!-- /input-group -->
	 </div><!-- /.col-lg-4 -->
	 <div class="col-lg-2">
	    <div class="input-group">
	      	<select class="form-control" name="level" >
				<option value="Assay">Assay</option>
				<option value="Study">Study</option>
			</select>
	    </div><!-- /input-group -->
	 </div><!-- /.col-lg-4 -->
	  <div class="col-lg-2">
		 <div class="checkbox">
		    <label>
		      <input type="checkbox" name="showAll"> Show all entries
		    </label>
	  	</div>
	 </div><!-- /.col-lg-4 -->
	</div><!-- /.row -->
	</g:uploadForm>


    </div> <!-- /container -->


</body>
</html>
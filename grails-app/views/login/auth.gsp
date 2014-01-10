
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>New group</title>    
</head>
<body>

 <div class="container">
   	 	
	<!-- Show message -->
	<g:if test="${flash.message}">
 		<div class="alert alert-block alert-danger">
 			<button type="button" class="close" data-dismiss="alert">&times;</button>
 			${flash.message}
 		</div>
	</g:if>

<div id='login'>
	<div class='inner'>
		<h3>Please login</h3>

		<form class="form-horizontal" role="form" action='/MetaDB/j_spring_security_check' method='POST' id='loginForm' class='cssform' autocomplete='off'>
			<div class="form-group">
				<label class="col-sm-2 control-label" for='username'>Username</label>
				<div class="col-sm-4">
					<input class="form-control" type='text' class='text_ form-control' name='j_username' id='username'/>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label" for='password'>Password</label>
				<div class="col-sm-4">
					<input class="form-control" type='password' class='text_' name='j_password' id='password'/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-4">
					<input class="btn btn-default" type='submit' id="submit" value='Login'/>
				</div>
			</div>
		</form>
	</div>
</div>
<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
</script>

		
		 
		
		
		
	
	<!-- deactivate the View menu if no assay is selected 
	
		<script>
		$(function() {
			$('#dropView').addClass('disabled');
		});
		</script>
	 -->
</div> <!-- /container -->

	</body>
</html>

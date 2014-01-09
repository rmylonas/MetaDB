<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Login</title>    
</head>
<body>

 <div class="container">

	<h3>Login</h3>

	<g:uploadForm class="form-horizontal" action="j_springsecurity_check" method='POST' id="loginForm" name="loginForm" autocomplete='off'>

		<div class="col-md-3">	 		 		
		   		<div class="form-group">	
					<label for="username">Name</label>
					<input class="form-control" name="j_username" id="username">
		   		</div> <!-- form-group -->
		   		
		   		<div class="form-group">	
					<label for="password">Password</label>
					<input type="password" class="form-control" name="j_password" id="password">
		   		</div> <!-- form-group -->
				
				<div class="form-group">
					<input class="btn btn-primary" type="submit" value="Login">
				</div>
		  	</div> <!-- col-md-4 --><%--

        <table>
                <tr>
                        <td><label for="username"><g:message code='spring.security.ui.login.username'/></label></td>
                        <td><input name="j_username" id="username" size="20" /></td>
                </tr>
                <tr>
                        <td><label for="password"><g:message code='spring.security.ui.login.password'/></label></td>
                        <td><input type="password" name="j_password" id="password" size="20" /></td>
                </tr>
                <tr>
                        <td colspan='2'>
                                <input type="checkbox" class="checkbox" name="${rememberMeParameter}" id="remember_me" checked="checked" />
                                <label for='remember_me'><g:message code='spring.security.ui.login.rememberme'/></label> |
                                <span class="forgot-link">
                                        <g:link controller='register' action='forgotPassword'><g:message code='spring.security.ui.login.forgotPassword'/></g:link>
                                </span>
                        </td>
                </tr>
                <tr>
                        <td colspan='2'>
                                <s2ui:linkButton elementId='register' controller='register' messageCode='spring.security.ui.login.register'/>
                                <s2ui:submitButton elementId='loginButton' form='loginForm' messageCode='spring.security.ui.login.login'/>
                        </td>
                </tr>
        </table>

	--%></g:uploadForm>

</div> <!-- /container -->

<script>
$(document).ready(function() {
        $('#username').focus();
});

</script>

</body>
</html>
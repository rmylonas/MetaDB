<!DOCTYPE html>
<!-- saved from url=(0061)http://getbootstrap.com/2.3.2/examples/starter-template.html# -->
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
		<title><g:layoutTitle default="MetaDB"/></title>
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Metadata and data analysis tool based on ISAtab files">
    <meta name="author" content="Roman Mylonas">

    <!-- Le styles -->
    <link href="${resource(dir: 'css', file: 'bootstrap.css')}" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
        margin-bottom: 60px;
      }
      #footer {
		  position: fixed;
		  bottom: 0;
		  width: 100%;
		  /* Set the fixed height of the footer here */
		  height: 40px;
		  background-color: #f5f5f5;
	  }
    </style>
    <%--
    <link href="${resource(dir: 'css', file: 'bootstrap-responsive.css')}" rel="stylesheet">
    --%>
    <script src="${resource(dir: 'js', file: 'jquery.js')}"></script>
    <script src="${resource(dir: 'js', file: 'bootstrap.js')}"></script>

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="shortcut icon" href="${resource(dir: 'img', file: 'favicon.png')}">
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
	
	<!-- Wrap all page content here -->
    <div id="wrap">
	
	<!-- Fixed navbar -->
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="${createLink(uri: '/search/index')}">MetaDB</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
          
          <!-- New -->
          	<li class="dropdown">
              <a id="dropNew" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">New <b class="caret"></b></a>
              <ul class="dropdown-menu" role="menu" aria-labelledby="dropNew">
             	<li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/uploadIsatab')}">Upload IsaTab file</a></li>
              	<!-- <li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/localIsatab')}">Check for IsaTab in upload folder</a></li>  -->
              </ul>
            </li>
          
            <!-- Load -->
          	<li class="dropdown">
              <a id="dropLoad" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">Search <b class="caret"></b></a>
              <ul class="dropdown-menu" role="menu" aria-labelledby="dropLoad">
                <li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/search')}">Search all</a></li>
                <li role="presentation" class="divider"></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/assays/myAssays')}">My assays</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/studies/myStudies')}">My studies</a></li>
                <li role="presentation" class="divider"></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/assays/allAssays')}">All assays</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/studies/allStudies')}">All studies</a></li>
              </ul>
            </li>
            
            <!-- View -->
                <!-- if no assay is selected, don't show the menus -->
            <g:if test="${session.assay}">
          	<li class="dropdown">
              <a id="dropView" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">View <b class="caret"></b></a>
              <ul class="dropdown-menu" role="menu" aria-labelledby="dropView">              
             	<li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/runs/randomized')}">Planned runs</a></li>
       	    	<li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/runs/acquired')}">Acquired runs</a></li>
       	    	<li role="presentation" class="divider"></li>      	
              	<li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/metaMS')}">MetaMS submissions</a></li>       	
              </ul>
            </li>
            </g:if>
            
            <!-- Statistics -->
            <li class="dropdown">
              <a id="dropManage" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">Statistics <b class="caret"></b></a>
              <ul class="dropdown-menu" role="menu" aria-labelledby="dropManage">
              	<li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/statistics/group')}">Group</a></li>       	
               	<li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/statistics/project')}">Project</a></li>
              </ul>
            </li>
            
            <!-- Settings -->
            <li class="dropdown">
              <a id="dropManage" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">Settings <b class="caret"></b></a>
              <ul class="dropdown-menu" role="menu" aria-labelledby="dropManage">
              	<li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/user')}">Users</a></li>       	  
             <sec:access expression="hasRole('ROLE_ADMIN')">            
              	<li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/group')}">Groups and Projects</a></li>       	
               	<li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/instrument')}">Instrument and Methods</a></li>       	
                <li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/metaMSSettings')}">MetaMS</a></li>       	
                <li role="presentation"><a role="menuitem" tabindex="-1" href="${createLink(uri: '/organism')}">Organism onthologies</a></li>
             </sec:access> 
              </ul>
            </li>
          </ul>
          
          
		  <!-- Loged user -->    
          <ul class="nav navbar-nav navbar-right">
          	<sec:access expression="hasRole('ROLE_ADMIN')">
          		<li><p class="navbar-text">Administrator</p></li>
          	</sec:access>
          	<li><p class="navbar-text"><strong><sec:username/></strong></p></li>
      		<li><a href="${createLink(uri: '/logout')}">Logout</a></li>
      	  </ul>
         	
          
        </div><!--/.nav-collapse -->
        
      </div>
    </div>
   	 	
		<g:layoutBody/>
		
		 
		
		<r:layoutResources />
		
	
	<!-- deactivate the View menu if no assay is selected 
	<g:if test="! ${assay}">
		<script>
		$(function() {
			$('#dropView').addClass('disabled');
		});
		</script>
	</g:if> -->
	
	<!-- wrap -->
	</div>
		
	</body>
	

	<div id="footer">
      <div class="container">
	      <div class="col-md-2 col-md-offset-4">
				<a href="http://www.fmach.it"><img height="40" src="${resource(dir: 'img', file: 'FEMlogo.png')}"/></a>
		  </div>
		  <div class="col-md-2">
				<a href="http://www.isa-tools.org/"><img src="${resource(dir: 'img', file: 'poweredByISAtools.png')}"/></a>
	      </div>
      </div>
    </div>
	
</html>

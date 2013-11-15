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
    <link href="${resource(dir: 'css', file: 'bootstrap-select.css')}" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <%--
    <link href="${resource(dir: 'css', file: 'bootstrap-responsive.css')}" rel="stylesheet">
    --%>
    <script src="${resource(dir: 'js', file: 'jquery.js')}"></script>
    <script src="${resource(dir: 'js', file: 'bootstrap.js')}"></script>
    <script src="${resource(dir: 'js', file: 'bootstrap-select.js')}"></script>

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
	
	
	    <!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="${createLink(uri: '/')}">MetaDB</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="${createLink(uri: '/uploadIsatab')}">Upload</a></li>
	        <li><a href="${createLink(uri: '/studies')}">Studies</a></li>
	        <li><a href="${createLink(uri: '/assays')}">Assays</a></li>
	        <li><a href="${createLink(uri: '/runs')}">Runs</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div><%--
	
	
	
		<div class="navbar navbar-default" role="navigation">
			<div class="container">
	        <div class="navbar-header">
	          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	            <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <a class="navbar-brand" href="${createLink(uri: '/')}">MetaDB</a>
	          <div class="collapse navbar-collapse">
	            <ul class="nav navbar-nav">
	         	  <li><a href="${createLink(uri: '/uploadIsatab')}">Upload</a></li>
	         	  <li><a href="${createLink(uri: '/studies')}">Studies</a></li>
	         	  <li><a href="${createLink(uri: '/assays')}">Assays</a></li>
	         	  <li><a href="${createLink(uri: '/runs')}">Runs</a></li>
	            </ul>
	          </div><!--/.nav-collapse -->
	        </div>
	        </div>
   	 	</div>
   	 	
		--%><g:layoutBody/>
		
		<r:layoutResources />
	</body>
</html>

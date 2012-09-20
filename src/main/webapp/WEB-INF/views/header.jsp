<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
 
<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8" />
      <title>Yuubi</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <meta name="description" content="" />
      <meta name="author" content="" />
      <link href="<spring:url value='/resources/img/favicon.png'/>" rel="shortcut icon" type="image/x-icon">
      <!-- Le styles -->
      <link rel="stylesheet" type="text/css"
		href="<spring:url value='/resources/css/bootstrap.css'/>" />
		
      <script src="<spring:url value='/resources/js/jquery.js'/>"></script>
	  <script src="<spring:url value='/resources/js/bootstrap.js'/>"></script>
	  <script src="<spring:url value='/resources/js/bootstrap-typeahead.js'/>"></script>
      
      <style type="text/css">
         body {
         padding-top: 60px;
         padding-bottom: 40px;
         }
      </style>
      <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
      <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
      <![endif]-->
   </head>
   <body>
	<div class="navbar navbar-inverse navbar-fixed-top">
	         <div class="navbar-inner">
	            <div class="container">
	               <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
	               <span class="icon-bar"></span>
	               <span class="icon-bar"></span>
	               <span class="icon-bar"></span>
	               </a>
	               <a class="brand" href="<spring:url value='/home'/>">yuubi</a>
	               <div class="nav-collapse collapse">
	                  <ul class="nav">
	                     <li class="active"><a href="<spring:url value='/home'/>"><i class="icon-home"></i> Home</a></li>
	                     <li class="dropdown active">
	                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-user"></i> Actions <b class="caret"></b></a>
	                        <ul class="dropdown-menu">
	                           <li class="nav-header">Component</li>
	                           <li><a href="<spring:url value='/search/component'/>"><i class="icon-edit"></i> Open</a></li>
	                           <li><a href="<spring:url value='/component/add'/>"><i class="icon-plus-sign"></i> Add</a></li>
	                           <li><a href="#stock"><i class="icon-list-alt"></i> Stock Levels</a></li>
	                           <li class="divider"></li>
	                           <li class="nav-header">Supplier</li>
	                           <li><a href="<spring:url value='/search/supplier'/>"><i class="icon-edit"></i> Open</a></li>
	                           <li><a href="<spring:url value='/supplier/add'/>""><i class="icon-plus-sign"></i> Add</a></li>
	                           <li class="divider"></li>
	                           <li class="nav-header">Order</li>
	                           <li><a href="<spring:url value='/search/order'/>"><i class="icon-edit"></i> Open</a></li>
	                           <li><a href="#"><i class="icon-shopping-cart"></i> Add</a></li>
	                           <li><a href="#"><i class="icon-road"></i> Take Delivery</a></li>
	                        </ul>
	                     </li>
	                     
	                     <li class="dropdown active">
	                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-wrench"></i> Configuration <b class="caret"></b></a>
	                        <ul class="dropdown-menu">
	                           <li class="nav-header">User</li>
	                           <li><a href="<spring:url value='/user/add'/>"><i class="icon-plus-sign"></i> Add</a></li>
	                        </ul>
	                     </li>
	                  </ul>
	                  <c:url var="url" value="/search/quicksearch" />
	                  <form:form cssClass="navbar-search pull-left" action="${url}" commandName="search">
	                  	<form:input path="searchString" cssClass="search-query"/>
	                  </form:form>
	                  <div class="navbar-form pull-right">
	                     <div class="btn-group">
	                        <a class="btn btn-primary" href="#"><i class="icon-user icon-white"></i> <sec:authentication property="principal.name" /></a>
	                        <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
	                        <ul class="dropdown-menu">
	                           <li><a href="<spring:url value='/preferences/'/>"><i class="icon-pencil"></i> Preferences</a></li>
	                           <li class="divider"></li>
	                           <li><a href="<spring:url value='/logout'/>"><i class="i"></i> Logout</a></li>
	                        </ul>
	                     </div>
	                  </div>
	               </div>
	            </div>
	         </div>
	      </div>
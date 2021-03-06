<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="title" scope="request" value="Y&#362;BI - Delivery Information"></c:set>
<jsp:include page="header.jsp" />
<body>
	<div class="container">
		<jsp:include page="menu.jsp" />
		<div class="row">
			<div class="span12">
				<div class="row">
					<div class="span12">
						<ul class="breadcrumb">
							<li><a href="<spring:url value="/shop"/>"><i
									class="icon-home"></i></a> <span class="divider">/</span></li>
							<li class="active">Delivery Information</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<h2 class="title">Delivery Information</h2>
				<hr />
				<p>
					<strong>The standard Lorem Ipsum passage, used since the
						1500s</strong><br /> Lorem ipsum dolor sit amet, consectetur adipisicing
					elit, sed do eiusmod tempor incididunt ut labore et dolore magna
					aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
					laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
					dolor in reprehenderit in voluptate velit esse cillum dolore eu
					fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
					proident, sunt in culpa qui officia deserunt mollit anim id est
					laborum.
				</p>
				<p>
					<strong>Section 1.10.32 of "de Finibus Bonorum et
						Malorum", written by Cicero in 45 BC</strong><br /> Sed ut perspiciatis
					unde omnis iste natus error sit voluptatem accusantium doloremque
					laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore
					veritatis et quasi architecto beatae vitae dicta sunt explicabo.
					Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit
					aut fugit, sed quia consequuntur magni dolores eos qui ratione
					voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem
					ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia
					non numquam eius modi tempora incidunt ut labore et dolore magnam
					aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum
					exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid
					ex ea commodi consequatur? Quis autem vel eum iure reprehenderit
					qui in ea voluptate velit esse quam nihil molestiae consequatur,
					vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?
				</p>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
	<script
		src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
	<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>
	<script>
		$(document).ready(function() {
			$('body').on('touchstart.dropdown', '.dropdown-menu', function(e) {
				e.stopPropagation();
			});

		});

		$(document).on('click', '.dropdown-menu a', function() {
			document.location = $(this).attr('href');
		});
	</script>
</body>
</html>
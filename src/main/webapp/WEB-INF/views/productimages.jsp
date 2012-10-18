<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Set some variables -->
<c:set var="readOnly" value="${screenMode == 'ENQUIRE'}" />
<jsp:include page="header.jsp" />
<div class="container-fluid">
	<div class="row-fluid">
		<ul class="thumbnails">
			<li class="span4">
				<div class="thumbnail">
					<img src="http://placehold.it/300x200" alt="">
					<div class="caption">
						<h3>Thumbnail label</h3>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a href="#" class="btn btn-primary">Action</a> <a href="#"
								class="btn">Action</a>
						</p>
					</div>
				</div>
			</li>
			<li class="span4">
				<div class="thumbnail">
					<img src="http://placehold.it/300x200" alt="">
					<div class="caption">
						<h3>Thumbnail label</h3>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a href="#" class="btn btn-primary">Action</a> <a href="#"
								class="btn">Action</a>
						</p>
					</div>
				</div>
			</li>
			<li class="span4">
				<div class="thumbnail">
					<img src="http://placehold.it/300x200" alt="">
					<div class="caption">
						<h3>Thumbnail label</h3>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a href="#" class="btn btn-primary">Action</a> <a href="#"
								class="btn">Action</a>
						</p>
					</div>
				</div>
			</li>
		</ul>
	</div>

</div>
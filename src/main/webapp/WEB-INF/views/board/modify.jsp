<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<session class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box box-primary">
						<h3 class="box-title">READ BOARD</h3>
				</div><!-- box-header end -->
					<form role="form" method="post" action="modify">
						<div class="box-body">
							<div class="form-group">
								<label >Title</label>
								<input type="hidden" name="bno" 
								class="form-control" value="${board.bno }">
								<label>Title</label>
								<input type="text" name="title" 
								class="form-control" value="${board.title }">
								<label>Content</label>
								<textarea rows="5" class="form-control" name="content">
								${board.content }</textarea>
								<label>Writer</label>
								<input readonly="readonly" type="text" name="writer" 
								class="form-control" value="${board.writer }">
								
								<input type="hidden" value="${board.regdate }" name="regdateStr">
								<input type="hidden" value="${board.viewcnt }" name="viewcnt">
							</div>
						</div><!-- box-body end -->
						<div class="box box-footer">
							<button type="submit" class="btn btn-warning">수정완료</button>
						</div><!-- box-footer end -->	
				</form>		
			</div>
		</div>
</session>					


					
<%@ include file="../include/footer.jsp"%>
					
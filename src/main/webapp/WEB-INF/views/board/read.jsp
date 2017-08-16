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
				
				<div class="box-body">
					<div class="form-group">
						<label>Title</label>
						<input readonly="readonly" type="text" name="title" 
						class="form-control" value="${board.title }">
						<label>Content</label>
						<textarea rows="5" class="form-control" name="content" readonly="readonly">
						${board.content }</textarea>
						<label>Writer</label>
						<input readonly="readonly" type="text" name="writer" 
						class="form-control" value="${board.writer }">
					</div>
				</div><!-- box-body end -->
				<div class="box box-footer btn-group">
					<button class="btn btn-warning">수정하기</button>
					<button class="btn btn-danger">삭제하기</button>
					<button class="btn btn-info">돌아가기</button>
				</div><!-- box-footer end -->
				<form role="form" method="post" id="f1">
					<input type="hidden" name="bno" value="${board.bno }">
					<input type="hidden" name="page" value="${cri.page }">
				</form>
			
			</div>
		</div>
</session>

<script>
	$(function () {
		$(".btn-danger").click(function () {
			$("#f1").attr("action","delete"); // post 형식의 delete 커맨드 호출
			$("#f1").submit();
		})
		$(".btn-warning").click(function () {
			$("#f1").attr("action","modify"); // get 형식의 modify 커맨드 호출
			$("#f1").attr("method", "get");
			$("#f1").submit();
		})
		$(".btn-info").click(function () {
			// location.href="listPage?page=${cri.page}";
			$("#f1").attr("action","listPage");
			$("#f1").attr("method","get");
			$("#f1").submit();
		})
	})
</script>
<%@ include file="../include/footer.jsp"%>

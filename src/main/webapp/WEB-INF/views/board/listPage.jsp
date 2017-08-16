<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
	
		function btnRegist(){
			location.href="register";
		}
	
	
</script>
<session class="content">
		<div class="row">
			<div class="col-md-12">
			
				<div class="box box-primary">
						<h3 class="box-title">BOARD LIST ALL</h3>
				</div><!-- box-header end -->
				
				<div class="box-body">
					<table class="table table-boarded">
						<tr>
							<th style="width:10px">BNO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>REGDATE</th>
							<th style="width:10px">VIEWCNT</th>
						</tr>
						<c:forEach var="board" items="${list }">
							<tr>
								<td>${board.bno}</td>
								<td><a href="read?bno=${board.bno}&page=${pageMaker.cri.page}">${board.title}</a></td>
								<td>${board.writer}</td>
								<td><fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd HH:mm"/>  </td>
								<td><span class="badge bg-red">${board.viewcnt}</span></td>
							</tr>
						</c:forEach>
					</table>
				</div><!-- box-body end -->
				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">
							<c:if test="${pageMaker.prev }">
								<li><a href="listPage?page=${pageMaker.startPage-1}">&laquo;</a></li>
							</c:if>
							<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
								<li ${pageMaker.cri.page==idx?'class=active':''}><a href="listPage?page=${idx}">${idx}</a></li>
							</c:forEach>
							<c:if test="${pageMaker.next }">
								<li><a href="listPage?page=${pageMaker.endPage+1}">&raquo;</a></li>
							</c:if>
						</ul>
					</div>
					
					<button type="button" class="btn btn-primary"onclick="btnRegist()">Register</button>
				
				</div>
			</div>
		</div>
</session>
<%@ include file="../include/footer.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
	
		
		$(function () {

			$("#searchBtn").click(function () {
				var keyword = $("input[name='keyword']").val();
				var searchType = $("select").val();
				location.href= "listPage?keyword="+keyword+"&searchType="+searchType;
			});

		});	
	
		function btnRegist(){
			location.href="register";
		}
		
</script>
<session class="content">
		<div class="row">
			<div class="col-md-12">
			
				<div class="box box-primary">
						<h3 class="box-title">BOARD LIST ALL (${login })</h3>
				</div><!-- box-header end -->
				
				<div class="box-body">
					<select name="searchType">
						<option value="n" ${cri.searchType==null ? 'selected' : ''}>---</option>
						<option value="t" ${cri.searchType=='t' ? 'selected' : ''}>Title</option>
						<option value="c" ${cri.searchType=='c' ? 'selected' : ''}>Content</option>
						<option value="w" ${cri.searchType=='w' ? 'selected' : ''}>Writer</option>
						<option value="tc" ${cri.searchType=='tc' ? 'selected' : ''}>Title OR Content</option>
						<option value="cw" ${cri.searchType=='cw' ? 'selected' : ''}>Content OR Writer</option>
						<option value="tcw" ${cri.searchType=='tcw' ? 'selected' : ''}>Title OR Content OR Writer</option>
					</select>	
					<input type="text" value="${cri.keyword }" name="keyword">
					<button id="searchBtn">Search</button>				
				</div>
				
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
								<td><a href="read${pageMaker.makeSearch(pageMaker.cri.page) }&bno=${board.bno}">${board.title} [${board.replycnt}]</a></td>
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
								<li><a href="listPage${pageMaker.makeSearch(pageMaker.startPage-1)}">&laquo;</a></li>
							</c:if>
							<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
								<li ${pageMaker.cri.page==idx?'class=active':''}><a href="listPage${pageMaker.makeSearch(idx) }">${idx}</a></li>
							</c:forEach>
							<c:if test="${pageMaker.next }">
								<li><a href="listPage${pageMaker.makeSearch(pageMaker.endPage+1)}">&raquo;</a></li>
							</c:if>
						</ul>
					</div>
					
					<button type="button" class="btn btn-primary"onclick="btnRegist()">글 등록하기</button>
				
				</div>
			</div>
		</div>
</session>
<%@ include file="../include/footer.jsp"%>

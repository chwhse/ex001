<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
	.itembox{
	 width: 100px;
	 margin:3px;
	 float: left;
	 position: relative;
	}
	
	.delbtn{
		position: absolute;
		top:10px;
		right:-10px;
	} 
	
</style>
<session class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box box-primary">
						<h3 class="box-title">READ BOARD</h3>
				</div><!-- box-header end -->
					<form role="form" method="post" action="modify" enctype="multipart/form-data">
						<div class="box-body">
							<div class="form-group">
								<label >Bno(hide)</label>
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
								<div class="form-group">
			                    <label>File</label>
			                    <input type="file" name="modImgFiles" class="form-control" multiple="multiple">
				                    <div class="form-group" id="divModImgFiles" style="width:900px; height:200px;">
			                        <c:forEach var="item" items="${board.files }">
			                           <div class="itembox">
			                           <img src="displayFile?filename=${item }">
			                           <button class="delbtn" data-src="${item }">x</button>
			                           <input type="hidden" name="deletefiles">
			                           </div>      
			                        </c:forEach>   
			                    	</div>
			                  	</div>
								
								<input type="hidden" value="${board.regdate }" name="regdateStr">
								<input type="hidden" value="${board.viewcnt }" name="viewcnt">
								<input type="hidden" value="${cri.page }" name="page">
								<input type="hidden" value="${cri.perPageNum }" name="perPageNum">
								<input type="hidden" value="${cri.searchType }" name="searchType">
								<input type="hidden" value="${cri.keyword }" name="keyword"> 
							</div>
						</div><!-- box-body end -->
						<div class="box box-footer">
							<button type="submit" class="btn btn-warning">수정완료</button>
						</div><!-- box-footer end -->	
				</form>		
			</div>
		</div>
</session>					
<script>
	$(document).on("click",".delbtn", function (event) {
		event.preventDefault();
		
		var imgpath = $(this).attr("data-src");
		$(this).next("input[name='deletefiles']").val(imgpath);
		$(this).parent(".itembox").hide();
	})
</script>

					
<%@ include file="../include/footer.jsp"%>
					
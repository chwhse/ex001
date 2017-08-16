<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
#imgFiles div{
   width: 150px;
   height: 150px;
   float: left;
}
#imgFiles img{
   float: left;
   min-width: 100%;
   max-width: 100%;
   min-height: 100%;
   max-height: 100%;
}   
</style>
<session class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box box-primary">
						<h3 class="box-title">READ BOARD</h3>
				</div><!-- box-primary end -->
				
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
					<div class="form-group" id="imgFiles" style="width:900px; height:200px;">
                        <c:forEach var="item" items="${board.files }">
                           <div>
                           <img src="displayFile?filename=${item }">
                           </div>      
                        </c:forEach>   
                    </div>
				</div><!-- box-body end -->
				<div class="box box-footer btn-group">
					<button class="btn btn-warning" id="btnBUpd">수정하기</button>
					<button class="btn btn-danger" id="btnBDel">삭제하기</button>
					<button class="btn btn-info" id="btnBack">돌아가기</button>
				</div><!-- box-footer end -->
				<form role="form" method="post" id="f1">
					<input type="hidden" name="bno" value="${board.bno }">
					<input type="hidden" name="page" value="${cri.page }">
					<input type="hidden" name="perPageNum" value="${cri.perPageNum }">
					<input type="hidden" name="searchType" value="${cri.searchType }">
					<input type="hidden" name="keyword" value="${cri.keyword }">
				</form>
			
			</div>
		</div> <!-- end of 게시물 detail -->
		
		<script> /* 게시물 script */
			$(function () {
				$("#btnBDel").click(function () {
					$("#f1").attr("action","delete"); // post 형식의 delete 커맨드 호출
					$("#f1").submit();
				})
				$("#btnBUpd").click(function () {
					$("#f1").attr("action","modify"); // get 형식의 modify 커맨드 호출
					$("#f1").attr("method", "get");
					$("#f1").submit();
				})
				$("#btnBack").click(function () {
					$("#f1").attr("action","listPage");
					$("#f1").attr("method","get");
					$("#f1").submit();
				})
			})
		</script>
		
		<div class="row"> <!-- 댓글 detail -->
			<div class="col-md-12">
				<div class="box box-success">
					<div class="box-header">
						<h3>댓글 추가</h3>
					</div>
					<div class="box-body">
						<label for="newReplyWriter">작성자</label>
						<input type="text" placeholder="USER ID" id="newReplyWriter" class="form-control">
						<br>
						<label for="newReplyText">댓글 내용</label>
						<input type="text" placeholder="Reply content" id="newReplyText" class="form-control">
						
					</div> <!-- end of body -->
					
					<div class="box-footer">
						<button class="btn btn-primary" id="btnAdd">Add Reply</button>
					</div><!-- end of footer --><br>
					
					<ul class="timeline"> 
						<li class="time-label" id="replieslist"> 
							<span class="bg-green" id="btnList">Replies List[${board.replycnt}]</span>
						</li>
					</ul><!-- end of timeline ul tag--><br>
					
				</div>
			</div>
		
		</div> <!-- end of 댓글 detail -->
			<!-- modify modal start 보이진 않음 -->
		<div id="modifyModal" class="modal modal-primary fade" role="dialog">
			  <div class="modal-dialog">
			    <!-- Modal content-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title"></h4>
			      </div>
			      <div class="modal-body" data-rno>
			        <p><input type="text" id="replytext" class="form-control" placeholder="수정내용"></p>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-info" id="replyModBtn">Modify</button>
			        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			      </div>
			    </div>
			  </div>
		</div> 
		
		<!-- 댓글 script -->
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
		<script id="temp" type="text/x-handlebars-template"> 
			{{#each.}}
			<li class="replyLi" data-rno={{rno}}>
				<i class="fa fa-comments bg-blue"></i>
				<div class="timeline-item">
					<span class="time">
						<i class="fa fa-clock-o"></i>{{tempdate regdate}}
					</span>
					<h3 class="timeline-header"><strong>{{rno}}</strong> -{{replyer}}</h3>
					<div class="timeline-body">{{replytext}} </div>
					<div class="timeline-footer">
					   <a class="btn btn-info btn-xs"  id="btnRUpd"  data-rno={{rno}}  data-text={{replytext}}
					    data-toggle="modal" data-target="#modifyModal">Modify</a>
					   <a class="btn btn-primary btn-xs" id="btnRDel"  data-rno={{rno}}
																	>Delete</a>
					</div>			
				</div>
			</li>
			{{/each}}
		</script>
		
		<script>
			Handlebars.registerHelper("tempdate", function (time) {
				var dateObj = new Date(time);
				var year = dateObj.getFullYear();
				var month = dateObj.getMonth() + 1;
				var date = dateObj.getDate();
				
				return year+"/"+month+"/"+date;
				
			})
			// 댓글 리스트 받아오기
			var bno = ${board.bno };
			function getAllList() {
				$.ajax({	// pageContext.getRequest().getContextPath()
					url: "${pageContext.request.contextPath}/replies/all/"+bno,
					type:"get",
					dataType:"json",
					success:function(data){
						console.log(data);
						var source = $("#temp").html();
						var template = Handlebars.compile(source);
						$(".replyLi").remove();
						$(".timeline").append(template(data));
					}
					
				})
			}
			//댓글 삭제
			$(document).on("click", "#btnRDel", function() {
				 var rno = $(this).attr("data-rno");
				 $.ajax({
						url: "${pageContext.request.contextPath}/replies/" + rno, 	// 서버 주소
						type: "delete",	// method
						dataType: "text", // 받아올 데이터 타입
						success: function (data) {
							if(data == "SUCCESS"){
								console.log(data);
								alert("삭제되었습니다.");
								getAllList();
							}
						}
					})
			 })
			//댓글 추가
			$("#btnAdd").click(function(){
				var writer = $("#newReplyWriter").val();
				var text = $("#newReplyText").val();
				var sendData = {bno:bno, replytext:text, replyer:writer};
				
				$.ajax({
					url:"${pageContext.request.contextPath}/replies/add",
					type:"post",
					dataType:"text",
					data:JSON.stringify(sendData),
					headers:{"Content-Type":"application/json"},
					success:function(data){
						console.log(data);
					}
				})
			});
			//댓글 수정
			$(document).on("click", "#btnRUpd", function() {
				 var rno = $(this).attr("data-rno");
				 var replytext = $(this).attr("data-text");
				 $(".modal-title").empty();
				 $(".modal-title").append(rno);
				 $(".form-control").val(replytext);
				
			 })// 수정 modal 창
		
			$("#replyModBtn").click(function() {
				var modalrno = $(".modal-title").html();
				var modaltext = $(this).attr("data-text");
				var sendData = {rno:modalrno,replytext:modaltext};
				 $.ajax({
					url: "${pageContext.request.contextPath}" + modalrno, 	// 서버 주소
					type: "put",
					dataType: "text", 
					data: JSON.stringify(sendData),
					headers : {"Content-Type" : "application/json"},
					success: function (data) {
						console.log(data);
						if(data == "success"){
							console.log(data);
							alert("수정되었습니다.");
							//	$(".btnDel").parent().remove();
							getAllList();
						}
					}
				})
			
			})// modal 안에 수정끝
			
			//list 가져오기
			$("#btnList").click(function(){
				getAllList();
			})
		</script>
</session>

<%@ include file="../include/footer.jsp"%>

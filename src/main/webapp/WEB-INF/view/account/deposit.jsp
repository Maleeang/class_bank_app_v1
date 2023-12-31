<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- header.jsp  -->
<%@include file="/WEB-INF/view/layout/header.jsp"%>

<!-- html 디자인  -->
   <div class="col-sm-8">
      <h2>입금 페이지(인증)</h2>
      <h5>어서오세요 환영합니다</h5>
      <div class="bg-light p-md-5 h-75">
     	<form action="/account/deposit" method="post">
		  <div class="form-group">
		    <label for="amount">입금액:</label>
		    <input type="text" class="form-control" placeholder="입금액을 입력하시오" id="amount" name="amount">
		  </div>
		  <div class="form-group">
		    <label for="dAccountNumber">입금 계좌 번호:</label>
		    <input type="text" class="form-control" placeholder="입금 계좌번호를 입력하시오" id="dAccountNumber" name="dAccountNumber">
		  </div>
		  <button type="submit" class="btn btn-primary">입금</button>
		</form>
      </div>
   </div>
   </div>
   </div>

<!-- footer.jsp  -->
<%@include file="/WEB-INF/view/layout/footer.jsp"%>
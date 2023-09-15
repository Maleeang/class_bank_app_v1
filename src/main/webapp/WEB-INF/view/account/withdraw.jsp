<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- header.jsp  -->
<%@include file="/WEB-INF/view/layout/header.jsp"%>

<!-- html 디자인  -->
   <div class="col-sm-8">
      <h2>출금 페이지(인증)</h2>
      <h5>어서오세요 환영합니다</h5>
      <div class="bg-light p-md-5 h-75">
     	<form action="/user/sign-proc" method="post">
		  <div class="form-group">
		    <label for="amount">출금 금액:</label>
		    <input type="text" class="form-control" placeholder="출금 금액을 입력하시오" id="amount" name="amount">
		  </div>
		  <div class="form-group">
		    <label for="wAccountNumber">출금 계좌 번호:</label>
		    <input type="text" class="form-control" placeholder="출금 계좌번호를 입력하시오" id="wAccountNumber" name="wAccountNumber">
		  </div>
		  <div class="form-group">
		    <label for="wAccountPassword">출금계좌 비밀 번호:</label>
		    <input type="password" class="form-control" placeholder="출금계좌 비밀 번호 입력" id="wAccountPassword" name="wAccountPassword">
		  </div>
		  <button type="submit" class="btn btn-primary">출금</button>
		</form>
      </div>
   </div>
   </div>
   </div>

<!-- footer.jsp  -->
<%@include file="/WEB-INF/view/layout/footer.jsp"%>
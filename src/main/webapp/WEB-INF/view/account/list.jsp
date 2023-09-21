<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- header.jsp  -->
<%@include file="/WEB-INF/view/layout/header.jsp"%>

<!-- html 디자인  -->
   <div class="col-sm-8">
      <h2>나의 계좌 목록(인증)</h2>
      <h5>어서오세요 환영합니다</h5>
      <div class="bg-light p-md-5 h-75">
		<table class="table">
			<thead>
				<tr>
					<th>계좌 번호</th>
					<th>잔액</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="account" items="${resultAccountList}">
				<tr>
					<td><a href="/account/detail/${account.getId()}">${account.getNumber()}</a></td>
					<td><a href="/account/detail/${account.getId()}">${account.getBalance()}</a></td>
				</tr>
				</c:forEach>

			</tbody>
		</table>
      </div>
   </div>
   </div>
   </div>

<!-- footer.jsp  -->
<%@include file="/WEB-INF/view/layout/footer.jsp"%>
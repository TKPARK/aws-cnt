<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Homework 2 알고리즘</title>
<script type="text/javascript">

	function addCoin(){
		var coinCnt = document.getElementById("coinRow").value;
		if(coinCnt == -1)
			coinCnt = 1;
		
		var totalCoin = document.getElementById("totalCoin");
		var frmTag = "<div id='coinRow"+coinCnt+"'>";
		frmTag += "<input name='price["+coinCnt+"]' type='text' value='' />";
		frmTag += " <input name='priceCnt["+coinCnt+"]' type='text' value='' /></div>";
		totalCoin.innerHTML += frmTag;
		
		coinCnt = parseInt(coinCnt) + 1;
		document.getElementById("coinRow").value = coinCnt;
	}
	function delCoin(){
		var coinCnt = document.getElementById("coinRow").value;
		coinCnt = parseInt(coinCnt) - 1;
		
		var totalCoin = document.getElementById("totalCoin");
		var row = document.getElementById("coinRow"+coinCnt);
		totalCoin.removeChild(row);
		
		document.getElementById("coinRow").value = coinCnt;
	}

</script>
</head>
<body>
<h1>
	Homework 2 알고리즘
</h1>

<h3>입력형식</h3>
<form action="coinExchange.do" method="post">
	<div>
		<div>지폐급액</div>
		<input name="amt" type="text" value="${amt}" />
	</div>
	<div>
		<p>동전가지수</p>
		<input name="addButton" type="button" style="cursor:hand" onClick="addCoin();" value="+">
		<input name="delButton" type="button" style="cursor:hand" onClick="delCoin();" value="-">
	</div>
	<div>
		<p>동전금액 개수</p>
	</div>
	<div id="totalCoin">
		
		<c:choose> 
			<c:when test="${coinRow > 0 }">
				<c:forEach var="i" begin="0" end="${coinRow-1 }" step="1">
					<div id="coinRow${i }">
						<input name="price[${i }]" type="text" value="${price[i] }" />
						<input name="priceCnt[${i }]" type="text" value="${priceCnt[i] }" />
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<div id="coinRow0">
					<input name="price[0]" type="text" value="" />
					<input name="priceCnt[0]" type="text" value="" />
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<input type="hidden" id="coinRow" value="${coinRow }">
	<input type="submit" value="계산">
</form>

<h3>출력형식</h3>
<textarea rows="4" cols="50">
${resultTxt}
</textarea>

</body>
</html>

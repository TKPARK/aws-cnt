<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Homework 2 알고리즘</title>
<script type="text/javascript">

	var coinCnt = 1;

	function addCoin(){
		if(coinCnt < 0)
			coinCnt = 0;
		var totalCoin = document.getElementById("totalCoin");
		var frmTag = "<div id='coinRow"+coinCnt+"'>";
		frmTag += "<input name='price["+coinCnt+"]' type='text' value='' />";
		frmTag += " <input name='priceCnt["+coinCnt+"]' type='text' value='' /></div>";
		totalCoin.innerHTML += frmTag;
		coinCnt++;
	}
	function delCoin(){
		coinCnt--;
		var totalCoin = document.getElementById("totalCoin");
		var row = document.getElementById("coinRow"+coinCnt);
		totalCoin.removeChild(row);
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
		<div id="coinRow0">
			<input name="price[0]" type="text" value="" />
			<input name="priceCnt[0]" type="text" value="" />
		</div>
	</div>
	<input type="submit" value="계산">
</form>

<h3>출력형식</h3>
<textarea rows="4" cols="50">
${resultTxt}
</textarea>

</body>
</html>

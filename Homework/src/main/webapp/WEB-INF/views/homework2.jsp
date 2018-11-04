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
		var frmTag = "<tr id='coinRow"+coinCnt+"'>";
		frmTag += "<td></td>";
		frmTag += "<td><input name='price["+coinCnt+"]' oninput='inputNumberFormat(this)' type='text' value='' /></td>";
		frmTag += " <td><input name='priceCnt["+coinCnt+"]' oninput='inputNumberFormat(this)' type='text' value='' /></td></tr>";
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
	
	function inputNumberFormat(obj) { 
	    obj.value = comma(uncomma(obj.value)); 
	} 

	function comma(str) { 
	    str = String(str); 
	    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,'); 
	} 

	function uncomma(str) { 
	    str = String(str); 
	    return str.replace(/[^\d]+/g, ''); 
	}

</script>
</head>
<body>
<h1>
	Homework 2 알고리즘
</h1>

<h3>입력형식</h3>
<form action="coinExchange.do" method="post">
	<table>
		<tbody id="totalCoin">
			<tr>
				<td>지폐급액</td>
				<td><input name="amt" oninput="inputNumberFormat(this)" type="text" value="${amt}" /></td>
			</tr>
			<tr>
				<td>동전의 가지 수</td>
				<td>
					<input name="addButton" type="button" style="cursor:hand" onClick="addCoin();" value="+">
					<input name="delButton" type="button" style="cursor:hand" onClick="delCoin();" value="-">
				</td>
			</tr>
			<tr>
				<td></td>
				<td>동전금액</td>
				<td>개수</td>
			</tr>
			
			<c:choose> 
				<c:when test="${coinRow > 0 }">
					<c:forEach var="i" begin="0" end="${coinRow-1 }" step="1">
						<tr id="coinRow${i }">
							<td></td>
							<td><input name="price[${i }]" oninput="inputNumberFormat(this)" type="text" value="${price[i] }" /></td>
							<td><input name="priceCnt[${i }]" oninput="inputNumberFormat(this)" type="text" value="${priceCnt[i] }" /></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr id="coinRow0">
						<td></td>
						<td><input name="price[0]" oninput="inputNumberFormat(this)" type="text" value="" /></td>
						<td><input name="priceCnt[0]" oninput="inputNumberFormat(this)" type="text" value="" /></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	 
	<input type="hidden" id="coinRow" value="${coinRow }">
	<input type="submit" value="계산">
</form>

<h3>출력형식</h3>
<textarea rows="10" cols="50">
${resultTxt}
</textarea>

</body>
</html>

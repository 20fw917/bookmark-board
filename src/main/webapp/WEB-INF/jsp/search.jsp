<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>search</title>
</head>
<body>
	My Folder
		<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>id</td>
			<td>작성자</td>
			<td>제목</td>
			<td>메모</td>
			<td>썸네일</td>
			<td>공유 유무</td>
			<td>즐겨찾기 유무</td>
		</tr>
		<c:forEach items="${myFolder}" var="mf">
			<tr>
				<td>${mf.id}</td>
				<td>${mf.owner}</td>
				<td>${mf.title}</td>
				<td>${mf.memo}</td>
				<td>${mf.thumbnail}</td>
				<td>${mf.is_shared}</td>
				<td>${mf.is_stared}</td>
			</tr>
		</c:forEach>
	<hr>
	Our Folder
		<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>id</td>
			<td>작성자</td>
			<td>제목</td>
			<td>메모</td>
			<td>썸네일</td>
			<td>생성일</td>
			<td>공유 유무</td>
			<td>즐겨찾기 유무</td>
		</tr>
		<c:forEach items="${ourFolder}" var="of">
			<tr>
				<td>${of.id}</td>
				<td>${of.owner}</td>
				<td>${of.title}</td>
				<td>${of.memo}</td>
				<td>${of.thumbnail}</td>
				<td>${of.is_shared}</td>
				<td>${of.is_stared}</td>
			</tr>
		</c:forEach>
</body>
</html>
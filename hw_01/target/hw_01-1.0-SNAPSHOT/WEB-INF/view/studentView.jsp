<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8" />
    <title>학생 정보 조회</title>
</head>
<body>
    이름: ${student.name}<br />
    이메일: ${student.email}<br />
    <c:if test="${!hideScore.equals('yes')}">
        성적: ${student.score}<br />
        평가: ${student.comment}<br />
    </c:if>
    <br />
<a href="/student/${student.id}/modify">정보 수정</a><br />
</body>
</html>
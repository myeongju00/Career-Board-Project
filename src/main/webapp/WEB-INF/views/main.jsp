<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/sidebar.css">
    <link rel="stylesheet" href="/resources/css/header.css">
    <script>
        let auth = ${auth.nickname != null ? 'true' : null};

        if (auth) {
            // auth 객체가 존재할 경우 sessionStorage에 저장
            sessionStorage.setItem('auth', JSON.stringify({
                user_id: "${auth != null ? auth.user_id : ''}",
                nickname: "${auth != null ? auth.nickname : ''}",
                postCount: "${postCount != null ? postCount : ''}",
                replyCount: "${replyCount != null ? replyCount : ''}"
            }));
        }
    </script>
    <script src="/resources/js/header.js"></script>
    <script src="/resources/js/sidebar.js"></script>
    <title>Main</title>
</head>
<body>
    <jsp:include page="header/header.jsp">
        <jsp:param name="nickname" value="${auth.nickname}"/>
        <jsp:param name="hotPostList" value="${hotPostList}"/>
    </jsp:include>
    <div class="container">
        <jsp:include page="sidebar/sidebar.jsp" />
        <main class="content">
            <div class="post-container">
                <div class="list-title">
                    <c:choose>
                        <c:when test="${keyword != null}">
                            <span>${keyword} 검색결과</span>
                        </c:when>
                        <c:otherwise>
                            <span>글 목록</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:choose>
                    <c:when test="${postList.size() == 0}">
                        <div class="empty-post">
                            <span>글이 없습니다.</span>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${postList}" var="post">
                            <div class="post">
                                <div class="tags">
                                    <a href="<c:url value="/search-position?position=${post.position}"/>">
                                        # ${post.position}
                                    </a>
                                </div>
                                <div class="title">
                                    <a href="/detailPageProcess?post_id=${post.post_id}&commentCount=0">${post.title}</a>
                                </div>
                                <div class="post-content">
                                        ${post.content}
                                </div>
                                <div class="info">
                                    <span> 조회수 ${post.view_count} </span>
                                    <span>댓글 ${post.reply_count} </span>
                                    <span>  ${post.created_date} </span>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- 페이지 번호 네비게이션 -->
            <div class="pagination">
                <c:if test="${postpageCount > 1}">
                    <a href="?postpageCount=${postpageCount - 1}&
                        <c:if test='${not empty param.keyword}'>keyword=${fn:escapeXml(param.keyword)}</c:if>
                        <c:if test='${not empty param.position}'>position=${fn:escapeXml(param.position)}</c:if>">
                        &lt;
                    </a>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <c:choose>
                        <c:when test="${i == postpageCount}">
                            <span class="current">${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="?postpageCount=${i}
                                <c:if test='${not empty param.keyword}'>${'&'}keyword=${fn:escapeXml(param.keyword)}</c:if>
                                <c:if test='${not empty param.position}'>${'&'}position=${fn:escapeXml(param.position)}</c:if>">
                                ${i}
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${postpageCount < totalPages}">
                    <a href="?postpageCount=${postpageCount + 1}
                        <c:if test='${not empty param.keyword}'> &keyword=${fn:escapeXml(param.keyword)}</c:if>
                        <c:if test='${not empty param.position}'> &position=${fn:escapeXml(param.position)}</c:if>">
                        &gt;
                    </a>
                </c:if>
            </div>

        </main>
    </div>
</body>
</html>
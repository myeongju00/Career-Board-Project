<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상세 페이지</title>
    <link rel="stylesheet" href="resources/css/detailPage.css" />
</head>
<body>
    <header>
        <a href="<c:url value='/main' />"><h1 class="logo">직장IN</h1></a>
        <div class="searchBox">
            <form action="" method="POST">
                <input type="text" name="searchBar" placeholder="검색어를 입력하세요." />
            </form>
        </div>
    </header>

    <container>
        <section class="sideNavWrap">
             <a href="<c:url value='/postForm' />"><button>글쓰기</button></a>
            <div class="infoBox">
                <dl>
                    <dt>닉네임</dt>
                    <dd>
                        <span>글
                            <b>${postCount}</b>
                        </span>
                        <span>답변
                            <b>${replyCount}</b>
                        </span>
                        <span>스크랩한 게시물
                            <b>0</b>
                        </span>
                    </dd>
                </dl>
            </div>
        </section>
        <section class="contentWrap">
            <section class="content">
                <div class="top">
                    <a href=""><span>태그</span></a>
                    <button>스크랩</button>
                    <h3>제목</h3>
                </div>
                <div class="date">
                    <span>작성일</span>
                </div>
                <div class="post">
                    <p>내용</p>
                </div>
                <div  class="author">
                    <p>작성자 정보</p>
                </div>
                <div class="tagBox">
                    <a href=""><span>#태그</span></a>
                </div>
            </section>
            
            <form action="" method="POST">
                <input type="text" name="commentBar" id="commentBar" placeholder="답변을 남겨주세요." />
                <button>등록</button>
            </form>

            <section class="warning">
                <ul>
                    <li>
                        답변을 등록하면 닉네임으로 작성자에게 전달됩니다. 
                    </li>
                    <li>
                        개인 정보를 공유 및 요청하거나, 명예훼손, 무단 관고, 불법 정보 유포 시 이에 대한 민형사상 책임은 작성자에게 있습니다.
                    </li>
                    <li>
                        개인정보가 포함되거나 부적절한 댓글은 비노출 또는 해당 서비스 이용 불가 처리될 수 있습니다. 
                    </li>
                </ul>
            </section>

            <section class="commentWrap">
                <h4 class="total-comment">답변 0</h4>
                <div class="comment">
                    <p>닉네임 <span>경력</span></p>
                    <p>댓글 내용</p>
                    <span>작성일</span> <button>수정</button> <button>삭제</button>
                    <hr />
                </div>
                <div class="page">
                    <button>1</button> <button>2</button> <button>3</button> <button>></button> 
                </div>
            </section>
        </section>
    </container>

</body>
</html>
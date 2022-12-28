<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.104.2">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/dashboard/">
    <link rel="stylesheet" href="detail.css">
    <title>폴더 상세</title>
    <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
    <sec:csrfMetaTags/>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>

    <br>
    <!--왼쪽 파트-->
    <div class="row">
        <div class="col-5">
            <div class="card p-4" style="width: 30rem; height: 46rem; float: right; margin-right: 1rem;">
                <img src="#" class="card-img-top detail-pic">
                <div class="card-body">
                    <h5 class="card-title detail-card">Card title</h5>
                    <p class="card-text detail-card">Some quick example text to build on the card title and make up the bulk of the card's content.</p>

                    <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                        <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
                        <label class="btn btn-outline-primary" for="btnradio1">공개</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
                        <label class="btn btn-outline-danger" for="btnradio2">비공개</label>
                    </div>

                    <p class="card-text detail-card">3개의 북마크가 있습니다.</p>

                    <!--편집, 삭제 버튼-->
                    <div class="text-end">
                        <a href="＃" class="btn btn-secondary">
                            <i class="bi bi-pencil"></i>
                            편집
                        </a>
                        <button class="btn btn-danger text-end">
                            <i class="bi bi-trash"></i>
                            삭제
                        </button>
                        <br>
                        <p class="fst-italic">${item.createdAtFormatted}에 추가 됨</p>
                    </div>

                </div>
            </div>
        </div>

        <!--오른쪽 파트-->
        <div class="col-5">
            <div class="row">
                <!--북마크 카드-->
                <div class="card" style="width: 25rem; margin: 10px;">
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">메모</p>
                        <p class="card-text">url</p>
                        <a href="#" class="btn btn-primary">
                            <i class="bi bi-globe"></i>
                            방문
                        </a>
                        <span style="float: right;">
              <a href="＃" class="btn btn-secondary">
                <i class="bi bi-pencil"></i>
                  편집
              </a>
              <button onclick="deleteBookmark(${item.id})" class="btn btn-danger text-end">
                <i class="bi bi-trash"></i>
                  삭제
              </button>
              </span>
                        <br>
                        <p class="fst-italic" style="float: right;">${item.createdAtFormatted}에 추가 됨</p>
                    </div>
                </div>

                <!--북마크 카드-->
                <div class="card" style="width: 25rem; margin: 10px;">
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">메모</p>
                        <p class="card-text">url</p>
                        <a href="#" class="btn btn-primary">
                            <i class="bi bi-globe"></i>
                            방문
                        </a>
                        <span style="float: right;">
              <a href="＃" class="btn btn-secondary">
                <i class="bi bi-pencil"></i>
                  편집
              </a>
              <button onclick="deleteBookmark(${item.id})" class="btn btn-danger text-end">
                <i class="bi bi-trash"></i>
                  삭제
              </button>
              </span>
                        <br>
                        <p class="fst-italic" style="float: right;">${item.createdAtFormatted}에 추가 됨</p>
                    </div>
                </div>

                <div class="card" style="width: 25rem; margin: 10px;">
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">메모</p>
                        <p class="card-text">url</p>
                        <a href="#" class="btn btn-primary">
                            <i class="bi bi-globe"></i>
                            방문
                        </a>
                        <span style="float: right;">
              <a href="＃" class="btn btn-secondary">
                <i class="bi bi-pencil"></i>
                  편집
              </a>
              <button onclick="deleteBookmark(${item.id})" class="btn btn-danger text-end">
                <i class="bi bi-trash"></i>
                  삭제
              </button>
              </span>
                        <br>
                        <p class="fst-italic" style="float: right;">${item.createdAtFormatted}에 추가 됨</p>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <!--페이징-->
    <br>
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li class="page-item"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
    </br>
</body>
</html>


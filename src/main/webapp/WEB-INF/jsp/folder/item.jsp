<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!--카드-->
<div class="card p-3" style="width: 17rem; float: left; margin: 20px;">
    <c:if test="${param.thumbnail ne null && param.thumbnail ne ''}">
        <img src="${pageContext.request.contextPath}/attachment/${param.thumbnail}" class="card-img-top" alt="">
    </c:if>
    <c:if test="${param.thumbnail eq null || param.thumbnail eq ''}">
        <div class="card-img-top">
            <svg xmlns="http://www.w3.org/2000/svg" width="238" height="238" fill="currentColor" class="bi bi-archive"
                 viewBox="0 0 16 16">
                <path d="M0 2a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v2a1 1 0 0 1-1 1v7.5a2.5 2.5 0 0 1-2.5 2.5h-9A2.5 2.5 0 0 1 1 12.5V5a1 1 0 0 1-1-1V2zm2 3v7.5A1.5 1.5 0 0 0 3.5 14h9a1.5 1.5 0 0 0 1.5-1.5V5H2zm13-3H1v2h14V2zM5 7.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5z"></path>
            </svg>
        </div>
    </c:if>
    <div class="card-body">
        <h5 class="card-title">${param.title}</h5>
        <p class="card-text">${param.memo}</p>
        <c:if test="${param.shared eq true}">
            <p class="card-text"><i class="bi bi-hand-thumbs-up-fill"></i>: ${param.likeCount}회</p>
        </c:if>
        <p class="card-text text-end">${param.itemCount}개의 북마크가 있습니다.</p>

        <div class="text-end">
            <c:if test="${param.showToolbar eq true}">
                <!--즐겨찾기 Button-->
                <c:if test="${param.stared eq true}">
                    <button type="button" onclick="updateStaredFolder(${param.id}, false)" class="btn btn-sm">
                        <i class="bi-star-fill" style="font-size:20px; color: #FDD017; cursor: pointer;"></i>
                    </button>
                </c:if>
                <c:if test="${param.stared eq false}">
                    <button type="button" onclick="updateStaredFolder(${param.id}, true)" class="btn btn-sm">
                        <i class="bi-star" style="font-size:20px; color: #FDD017; cursor: pointer;"></i>
                    </button>
                </c:if>

                <%-- 공개 버튼 --%>
                <c:if test="${param.shared eq true}">
                    <button type="button" class="btn btn-sm" onclick="updateSharedFolder(${param.id}, false)">
                        <i class="bi bi-people-fill" style="font-size: 20px;"></i>
                    </button>
                </c:if>
                <c:if test="${param.shared eq false}">
                    <button type="button" class="btn btn-sm" onclick="updateSharedFolder(${param.id}, true)">
                        <i class="bi bi-person" style="font-size: 20px;"></i>
                    </button>
                </c:if>
            </c:if>

            <!--자세히 보기 Button-->
            <a href="${pageContext.request.contextPath}/folder/detail/${param.id}" class="btn btn-sm btn-primary">
                <i class="bi bi-search"></i>
            </a>

            <c:if test="${param.showToolbar eq true}">
                <!--Edit Button-->
                <c:url value="${pageContext.request.contextPath}/folder/update" var="url">
                    <c:param name="id" value="${param.id}"/>
                </c:url>
                <a href="${url}" class="btn btn-sm btn-secondary">
                    <i class="bi bi-pencil-square"></i>
                    <!--Button 내용-->
                </a>

                <!--Delete Button-->
                <button type="button" onclick="deleteFolder(${param.id})" class="btn btn-sm btn-danger">
                    <i class="bi bi-trash"></i>
                    <!--공백란-->
                </button>
            </c:if>
            <c:if test="${param.showToolbar eq false}">
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal" var="principal"/>
                    <c:if test="${principal.userInternalId ne param.owner && param.shared eq true}">
                        <button type="button" onclick="copyFolder(${param.id})" class="btn btn-sm btn-secondary">
                            <i class="bi bi-clipboard-plus"></i>
                        </button>

                        <c:if test="${param.isLiked ne null}">
                            <c:if test="${param.isLiked eq false}">
                                <button type="button" onclick="likeFolder(${param.id}, true)"
                                        class="btn btn-sm btn-secondary">
                                    <i class="bi bi-hand-thumbs-up"></i>
                                </button>
                            </c:if>
                            <c:if test="${param.isLiked eq true}">
                                <button type="button" onclick="likeFolder(${param.id}, false)"
                                        class="btn btn-sm btn-secondary">
                                    <i class="bi bi-hand-thumbs-up-fill"></i>
                                </button>
                            </c:if>
                        </c:if>
                    </c:if>
                </sec:authorize>
            </c:if>
        </div>
    </div>
</div>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>BookSaw - Kết Quả Tìm Kiếm</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/icomoon/icomoon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/vendor.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body data-bs-spy="scroll" data-bs-target="#header" tabindex="0">
<div id="header-wrap">
    <div class="top-content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-6">
                    <div class="social-links">
                        <ul>
                            <li><a href="#"><i class="icon icon-facebook"></i></a></li>
                            <li><a href="#"><i class="icon icon-twitter"></i></a></li>
                            <li><a href="#"><i class="icon icon-youtube-play"></i></a></li>
                            <li><a href="#"><i class="icon icon-behance-square"></i></a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="right-element">
                        <a href="#" class="user-account for-buy"><i class="icon icon-user"></i><span>Tài Khoản</span></a>
                        <a href="#" class="cart for-buy"><i class="icon icon-clipboard"></i><span>Giỏ Hàng:(0 $)</span></a>
                        <div class="action-menu">
                            <div class="search-bar">
                                <a href="#" class="search-button search-toggle" data-selector="#header-wrap">
                                    <i class="icon icon-search"></i>
                                </a>
                                <form role="search" method="get" class="search-box" action="${pageContext.request.contextPath}/search">
                                    <input class="search-field text search-input" placeholder="Tìm kiếm sách" type="search" name="query" required>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <header id="header">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2">
                    <div class="main-logo">
                        <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/main-logo.png" alt="logo"></a>
                    </div>
                </div>
                <div class="col-md-10">
                    <nav id="navbar">
                        <div class="main-menu stellarnav">
                            <ul class="menu-list">
                                <li class="menu-item"><a href="${pageContext.request.contextPath}/">Trang Chủ</a></li>
                                <!-- Các mục menu khác -->
                            </ul>
                            <div class="hamburger">
                                <span class="bar"></span>
                                <span class="bar"></span>
                                <span class="bar"></span>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </div>
    </header>
</div>

<section id="search-results" class="py-5 my-5">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="section-header align-center">
                    <div class="title">
                        <span>Kết Quả Tìm Kiếm</span>
                    </div>
                    <h2 class="section-title">Sách Tìm Thấy cho "<c:out value="${query}" />"</h2>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger text-center">
                        <c:out value="${error}" />
                    </div>
                </c:if>

                <c:choose>
                    <c:when test="${empty searchResults}">
                        <div class="alert alert-info text-center">
                            Không tìm thấy sách nào cho "<c:out value="${query}" />". Hãy thử từ khóa khác.
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="product-list" data-aos="fade-up">
                            <div class="row">
                                <c:forEach var="book" items="${searchResults}">
                                    <div class="col-md-3">
                                        <div class="product-item">
                                            <figure class="product-style">
                                                <img src="${book.imageUrl != null ? book.imageUrl : '/images/books/default.jpg'}" alt="${book.title}" class="product-item">

                                            </figure>
                                            <figcaption>
                                                <h3><c:out value="${book.title}" /></h3>
                                                <span><c:out value="${book.author}" /></span>
                                                <div class="item-price">$<fmt:formatNumber value="${book.price}" pattern="#.00" /></div>
                                            </figcaption>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</section>

<script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/plugins.js"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
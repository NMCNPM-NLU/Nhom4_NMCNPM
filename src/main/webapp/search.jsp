<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>BookSaw - Search Results</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="format-detection" content="telephone=no">
    <meta name="author" content="">
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">

    <link rel="stylesheet" type="text/css" href="assets/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="icomoon/icomoon.css">
    <link rel="stylesheet" type="text/css" href="assets/css/vendor.css">
    <link rel="stylesheet" type="text/css" href="assets/css/style.css">
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
                        <a href="#" class="user-account for-buy"><i class="icon icon-user"></i><span>Account</span></a>
                        <a href="#" class="cart for-buy"><i class="icon icon-clipboard"></i><span>Cart:(0 $)</span></a>
                        <div class="action-menu">
                            <div class="search-bar">
                                <a href="#" class="search-button search-toggle" data-selector="#header-wrap">
                                    <i class="icon icon-search"></i>
                                </a>
                                <form role="search" method="get" class="search-box" action="search.jsp">
                                    <input class="search-field text search-input" placeholder="Search" type="search" name="query">
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
                        <a href="index.jsp"><img src="images/main-logo.png" alt="logo"></a>
                    </div>
                </div>
                <div class="col-md-10">
                    <nav id="navbar">
                        <div class="main-menu stellarnav">
                            <ul class="menu-list">
                                <li class="menu-item"><a href="index.jsp">Home</a></li>
                                <li class="menu-item has-sub">
                                    <a href="#pages" class="nav-link">Pages</a>
                                    <ul>
                                        <li><a href="index.jsp">Home</a></li>
                                        <li><a href="index.jsp">About</a></li>
                                        <li><a href="index.jsp">Styles</a></li>
                                        <li><a href="index.jsp">Blog</a></li>
                                        <li><a href="index.jsp">Post Single</a></li>
                                        <li><a href="index.jsp">Our Store</a></li>
                                        <li><a href="index.jsp">Product Single</a></li>
                                        <li><a href="index.jsp">Contact</a></li>
                                        <li><a href="index.jsp">Thank You</a></li>
                                    </ul>
                                </li>
                                <li class="menu-item"><a href="#featured-books" class="nav-link">Featured</a></li>
                                <li class="menu-item"><a href="#popular-books" class="nav-link">Popular</a></li>
                                <li class="menu-item"><a href="#special-offer" class="nav-link">Offer</a></li>
                                <li class="menu-item"><a href="#latest-blog" class="nav-link">Articles</a></li>
                                <li class="menu-item"><a href="#download-app" class="nav-link">Download App</a></li>
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
                        <span>Search Results</span>
                    </div>
                    <h2 class="section-title">Books Found for "<c:out value="${param.query}" />"</h2>
                </div>

                <c:choose>
                    <c:when test="${empty searchResults}">
                        <div class="alert alert-info text-center">
                            No books found for your search query "<c:out value="${param.query}" />". Try a different search term.
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="product-list" data-aos="fade-up">
                            <div class="row">
                                <c:forEach var="book" items="${searchResults}">
                                    <div class="col-md-3">
                                        <div class="product-item">
                                            <figure class="product-style">
                                                <img src="${book.imageUrl}" alt="${book.title}" class="product-item">
                                                <button type="button" class="add-to-cart" data-product-tile="add-to-cart">Add to Cart</button>
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

<footer id="footer">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="footer-item">
                    <div class="company-brand">
                        <img src="images/main-logo.png" alt="logo" class="footer-logo">
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sagittis sed ptibus liberolectus nonet psryroin.</p>
                    </div>
                </div>
            </div>
            <div class="col-md-2">
                <div class="footer-menu">
                    <h5>About Us</h5>
                    <ul class="menu-list">
                        <li class="menu-item"><a href="#">vision</a></li>
                        <li class="menu-item"><a href="#">articles</a></li>
                        <li class="menu-item"><a href="#">careers</a></li>
                        <li class="menu-item"><a href="#">service terms</a></li>
                        <li class="menu-item"><a href="#">donate</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-md-2">
                <div class="footer-menu">
                    <h5>Discover</h5>
                    <ul class="menu-list">
                        <li class="menu-item"><a href="#">Home</a></li>
                        <li class="menu-item"><a href="#">Books</a></li>
                        <li class="menu-item"><a href="#">Authors</a></li>
                        <li class="menu-item"><a href="#">Subjects</a></li>
                        <li class="menu-item"><a href="#">Advanced Search</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-md-2">
                <div class="footer-menu">
                    <h5>My account</h5>
                    <ul class="menu-list">
                        <li class="menu-item"><a href="#">Sign In</a></li>
                        <li class="menu-item"><a href="#">View Cart</a></li>
                        <li class="menu-item"><a href="#">My Wishlist</a></li>
                        <li class="menu-item"><a href="#">Track My Order</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-md-2">
                <div class="footer-menu">
                    <h5>Help</h5>
                    <ul class="menu-list">
                        <li class="menu-item"><a href="#">Help center</a></li>
                        <li class="menu-item"><a href="#">Report a problem</a></li>
                        <li class="menu-item"><a href="#">Suggesting edits</a></li>
                        <li class="menu-item"><a href="#">Contact us</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</footer>

<div id="footer-bottom">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="copyright">
                    <div class="row">
                        <div class="col-md-6">
                            <p>© 2022 All rights reserved. Free HTML Template by <a href="https://www.templatesjungle.com/" target="_blank">TemplatesJungle</a></p>
                        </div>
                        <div class="col-md-6">
                            <div class="social-links align-right">
                                <ul>
                                    <li><a href="#"><i class="icon icon-facebook"></i></a></li>
                                    <li><a href="#"><i class="icon icon-twitter"></i></a></li>
                                    <li><a href="#"><i class="icon icon-youtube-play"></i></a></li>
                                    <li><a href="#"><i class="icon icon-behance-square"></i></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="js/jquery-1.11.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
        crossorigin="anonymous"></script>
<script src="js/plugins.js"></script>
<script src="js/script.js"></script>
</body>
</html>
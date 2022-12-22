<%-- 
    Document   : user
    Created on : Nov 16, 2021, 1:04:50 PM
    Author     : Admin
--%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Shopyy</title>

        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link href="${pageContext.request.contextPath}/css/variables.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/auth-modals.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/header.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/footer.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/item.css" rel="stylesheet" />

        <link href="${pageContext.request.contextPath}/css/user.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/user-order.css" rel="stylesheet" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body class="bg-light">
        <jsp:include page="components/header.jsp"></jsp:include>
            <div class="container px-5 mt-5">
                <div class="row">
                    <div class="col-2">
                        <div class="user__general-info">
                            <img class="user__avatar rounded-circle" src="https://cf.shopee.vn/file/1d74524ec09542f944ad95a2a6fd111d_tn" alt="user avatar">
                            <div class="d-flex flex-column ms-3">
                                <div class="user__name mb-1"><c:out value="${user.account.username}" /></div>
                            <div class="user__edit-profile-ctrl text-capitalize text-muted">
                                <i class="fas fa-pen"></i>
                                Sửa hồ sơ
                            </div>
                        </div>
                    </div>

                    <hr class="text-muted"/>

                    <div class="user__nav">
                        <ul class="nav-list list-unstyled">
                            <li class="nav-item">
                                <a href="" class="d-flex align-items-center">
                                    <img class="img-fluid me-2" src="https://cf.shopee.vn/file/84feaa363ce325071c0a66d3c9a88748">
                                    <span>
                                        Ưu đãi dành riêng cho bạn
                                    </span>
                                </a>
                            </li>
                            <li class="nav-item active">
                                <a href="${pageContext.request.contextPath}/user/account/profile"  class="d-flex align-items-center">
                                    <i class="far fa-user me-2 text-primary"></i>
                                    <span>
                                        Tài khoản của tôi
                                    </span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/user/order">
                                    <i class="fas fa-clipboard-list me-2"></i>
                                    Đơn hàng
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col-10">
                    <div class="user-order">
                        <div class="bg-white shadow-sm rounded  px-4 py-3">
                            <ul class="list-unstyled row order-list-nav mb-0">
                                <li class="order-list-nav__link col-3"><a href="">Tất cả</a></li>
                                <li class="order-list-nav__link col-3"><a href="">Chờ xác nhận</a></li>
                                <li class="order-list-nav__link col-3"><a href="">Đang giao</a></li>
                                <li class="order-list-nav__link col-3"><a href="">Đã giao</a></li>
                            </ul>
                        </div>
                        <div class="order-list bg-white shadow-sm rounded  px-4 py-3 mt-3">

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="components/footer.jsp"></jsp:include>
    </body>
</html>

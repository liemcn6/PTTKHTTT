<%-- 
    Document   : checkout-success
    Created on : Nov 16, 2021, 12:47:38 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Shopyy | Checkout</title>

        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link href="${pageContext.request.contextPath}/css/variables.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/header.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/footer.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/item.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/checkout.css" rel="stylesheet" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>

    <body>

        <jsp:include page="components/header.jsp"></jsp:include>

            <!-- Cart -->
            <div class="container my-5 p-3">
                <br>
                <div class="ps-3 text-capitalize">
                    <h3>Hoàn thành</h3>
                </div>
                <hr>
                <div class="row row-cols-3 checkout-progress">
                    <div class="col-3"></div>
                    <div class="col-6">
                        <div class="position-relative">
                            <div class="progress" style="height: 2px;">
                                <div class="progress-bar progress-bar-animated" style="width: 100%; height: 2px;"></div>
                            </div>
                            <div class="position-absolute text-center align-content-center" style="left: 16%; bottom: -8px;">
                                <div><i class="bi-cart-fill me-1 text-primary"></i></div>
                                <div>
                                    <p class="text-primary mb-2">Giỏ hàng</p>
                                </div>
                                <div><span class="badge bg-primary text-white ms-1 rounded-pill my-0">1</span></div>
                            </div>
                            <div class="position-absolute text-center align-content-center" style="left: 45%; bottom: -8px;">
                                <div><i class="bi-currency-dollar me-1 text-primary"></i></div>
                                <div>
                                    <p class="text-primary mb-1">Thanh toán</p>
                                </div>
                                <div><span class="badge bg-primary text-white ms-1 rounded-pill my-0">2</span></div>
                            </div>
                            <div class="position-absolute text-center align-content-center" style="right: 16%; bottom: -8px;">
                                <div><i class="bi-check-circle-fill me-1 text-primary"></i></div>
                                <div>
                                    <p class="text-primary mb-1">Hoàn tất</p>
                                </div>
                                <div><span class="badge bg-primary text-white ms-1 rounded-pill my-0">3</span></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-3"></div>
                </div>
                <br><br>
                <div class="row mt-5 p-0">
                    <div class="text-center text-success">
                        <h4>Đặt hàng hoàn tất <i class="bi-check-circle"></i></h4>
                    </div>
                </div>
            </div>

            <!-- Footer -->
        <jsp:include page="components/footer.jsp"></jsp:include>

    </body>
</html>

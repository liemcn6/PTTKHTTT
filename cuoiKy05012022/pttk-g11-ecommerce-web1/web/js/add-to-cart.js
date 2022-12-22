/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//add item to cart
(function () {
    const addToCartBtn = document.querySelector('#add-to-cart');
    const buyNowBtn = document.querySelector('#buy-now');

    async function addItemToCart() {
        const formData = new URLSearchParams();
        const itemId = document.getElementById('item-detail').getAttribute('itemid');
        const quantity = document.querySelector('.number > input').value;
        console.log(itemId, quantity)
        formData.append('itemid', itemId);
        formData.append('quantity', quantity);

        const response = await fetch('http://localhost:8080/g11/cart/add-to-cart',
                {
                    method: "POST",
                    contentType: "application/x-www-form-urlencoded",
                    body: formData,
                }
        );

        const data = await response.text();
        if (data) {
            const dataTokens = data.split(';');
            return dataTokens[0];
        }

        return '503';
    }

    addToCartBtn.addEventListener('click', async () => {
        const responseCode = await addItemToCart();

        if (responseCode === '201') {
            Swal.fire({
                icon: 'success',
                title: 'Sản phẩm đã được thêm vào giỏ hàng',
                timer: 2000,
                showCancelButton: false,
                showConfirmButton: false,
            });
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Thêm sản phẩm vào giỏ hàng thất bại, vui lòng thử lại sau',
                timer: 2000,
                showCancelButton: false,
                showConfirmButton: false,
            });
        }
    });

    buyNowBtn.addEventListener('click', async () => {
        const responseCode = await addItemToCart();

        if (responseCode === '201') {
            setTimeout(() => {
                document.location.pathname = "/g11/cart";
            }, 2000);

            Swal.fire({
                icon: 'success',
                title: 'Sản phẩm đã được thêm vào giỏ hàng',
                timer: 1000,
                showCancelButton: false,
                showConfirmButton: false,
            });
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Thêm sản phẩm vào giỏ hàng thất bại, vui lòng thử lại sau',
                timer: 2000,
                showCancelButton: false,
                showConfirmButton: false,
            });
        }
    });
})();

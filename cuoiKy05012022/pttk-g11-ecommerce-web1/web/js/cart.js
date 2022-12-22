/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let currentBill = 0;
let totalBill = 0;
const billContainerEle = document.getElementById('bill-container');

function checkBillUpdate(updatedItem, checkboxChecked) {
    if (typeof updatedItem === 'object') {
//        checkbox update event
        if (checkboxChecked !== null) {
            const changeAmount = document.querySelector('.item-total-price > input[type="hidden"]').value;

            if (checkboxChecked) {
                currentBill += changeAmount;
            } else {
                currentBill -= changeAmount;
            }

            billContainerEle.innerText = currentBill;
        }

        const selector = updatedItem.querySelector('.item-selector');
        if (selector.checked) {
            updateBill();
        }
    } else if (typeof updatedItem === 'string' && updatedItem === 'all') {

    }

    function updateBill() {

    }
}

//select all checkbox
(function () {
    const billContainer = document.getElementById('bill-container');
    const totalBillInput = document.querySelector('.item-total-bill__input');
    const allCheckbox = Array.from(document.getElementsByClassName('item-selector'));

    allCheckbox.forEach((element) => {
        element.addEventListener('change', function () {
//            checkbox change -> update total price
            const container = this.closest('[itemid]');
            const price = container.querySelector('.item-total-price__input').value;

            if (this.checked) {
                const newPrice = totalBillInput.value + price;
                totalBill = newPrice;

                billContainer.innerHTML = currencyFormat(newPrice) + "<sup>đ</sub>"
                totalBillInput.value = newPrice;
            } else {
                const newPrice = totalBillInput.value - price;
                totalBill = newPrice;

                billContainer.innerHTML = currencyFormat(newPrice) + "<sup>đ</sub>"
                totalBillInput.value = newPrice;
            }
        });
    })

//  select all checkbox -> update total price
    document.getElementById('select-all-checkbox').addEventListener('change', function () {
        const checkboxStatus = this.checked;

        allCheckbox.forEach((element) => {
            element.checked = checkboxStatus;
            element.dispatchEvent(new Event("change"))
        });

        billContainer.innerHTML = currencyFormat(totalBill) + "<sup>đ</sub>";
        totalBillInput.value = totalBill;
    });

    function currencyFormat(amount) {
        let formatted = "";
        let currentAmount = Math.trunc(amount);

        const baseDivider = 1000;
        let divider = baseDivider;

        while (Math.trunc(currentAmount / divider) > 0) {
            const mod = currentAmount % divider;

            if (mod == 0) {
                formatted = formatted + mod + mod + mod + ".";
            } else {
                formatted = formatted + mod;
                formatted = formatted.split("").reverse().join("") + ".";
            }
            currentAmount = Math.trunc(currentAmount / divider);
            divider *= baseDivider;
        }

        formatted = formatted.split("").reverse().join("")
        formatted = currentAmount + formatted;
        return formatted;
    }
})();

//select checkbox update total price
(function () {

})();

//update item amount
(function () {
    Array.from($('.item-counter > .minus')).forEach((ele) => {
        ele.addEventListener('click', function () {
            const input = $(this).parent().find('input');
            const itemElement = this.closest('.item[itemid]');
            const itemId = itemElement.getAttribute('itemId');

//            disable more event
            Object.assign(this.parentElement.style,
                    {
                        pointerEvents: 'none',
                        opacity: '0.4'
                    }
            );

            const fetchResult = fetchUpdate('');

            if (fetchResult.statusCode == 200) {
                let count = parseInt(input.val()) - 1;
                count = count < 1 ? 1 : count;
                input.val(count);
                input.change();

                const priceContainer = itemElement.querySelector('.item-total-price > input[type="hidden"]');
                priceContainer.value = fetchResult.totalPrice;
            } else {
                console.log(fetchResult);
            }

//          active more event
            Object.assign(element.parentElement.style,
                    {
                        pointerEvents: null,
                        opacity: null
                    }
            );
        });
    });

    Array.from($('.item-counter > .plus')).forEach((ele) => {
        ele.addEventListener('click', function () {
            var $input = $(this).parent().find('input');
            $input.val(parseInt($input.val()) + 1);
            $input.change();
            return false;
        });
    });

    function fetchUpdate(url) {
        const fetchResult = {statusCode: 200, totalPrice: 20000};
        return fetchResult;
    }

})();

//remove item in cart
(function () {
    Array.from(document.getElementsByClassName('delete-item-btn')).forEach((element) => {
        element.addEventListener('click', function () {
//            fetch
            const fetchResult = true;

            if (fetchResult) {
                document.querySelector(`.item[itemId="${this.getAttribute('for')}"]`).remove();
            }
        });
    })
})();


//checkout
(function () {
    const itemIdStr = Array.from(document.querySelectorAll('.item-selector[itemid]:checked')).reduce((total, element) => {
        const itemId = element.getAttribute('itemid');
        total += itemId + ";";
    }, "");

    const createOrderBtn = document.getElementById('pay-now');
    createOrderBtn.addEventListener('click', createOrder)

    async function createOrder() {
        const formData = new URLSearchParams();

        formData.append('itemIdStr', itemIdStr);

        const response = await fetch('http://localhost:8080/g11/user/order/create-order-cart',
                {
                    method: "POST",
                    contentType: "application/x-www-form-urlencoded",
                    body: formData,
                }
        );

        const data = await response.text();

        if (data) {
            const dataTokens = data.split(';');
            if (dataTokens[0] === '201') {
                window.location.href = 'http://localhost:8080/g11/user/order/fill-info';
            } else if (dataTokens[0] === '503') {
                Swal.fire({
                    icon: 'error',
                    title: 'Đã có lỗi xảy ra, vui lòng thử lại sau',
                    timer: 2000,
                    showCancelButton: false,
                });
            }
        }
    }

})()
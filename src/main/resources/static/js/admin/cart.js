$( document ).ready(function() {

    $(".btn-update-cart" ).click(function() {
        id = $(this).attr("bookid");
        quantity = $('.quantity-' + id).val();
        url = WebContext.contextPath + 'book/update-cart/' + id + "/" + quantity;
        document.location = url;
    });

    $("#btn-pay" ).click(function() {
        var payMethod = $('#payOption').find(":selected").val();
        if(payMethod === 'PayPal'){
            document.location = WebContext.contextPath + 'pay/paypal';
        }else{
            document.location = WebContext.contextPath + 'pay/COD'
        }
    });

});

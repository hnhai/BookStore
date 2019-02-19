$( document ).ready(function() {
    $( ".btn-update-cart" ).click(function() {
        id = $(this).attr("bookid");
        quantity = $('.quantity-' + id).val();
        url = WebContext.contextPath + 'book/update-cart/' + id + "/" + quantity;
        document.location = url;
    });
});

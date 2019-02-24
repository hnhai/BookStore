$( document ).ready(function() {
    var token = $('#_csrf_token').attr('content');
    var header = $('#_csrf_header').attr('content');

    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });

    $(document).on("click",".btn-update-status",function() {
        var orderID = $(this).attr('id');
        var status = $('#order-status-' + orderID).val();
        $.ajax({
            type: "POST",
            url: WebContext.contextPath + "employee/order/" + orderID + "/" + status,
            success: function (data) {
                if (data == true){
                    window.location.href = WebContext.contextPath + "employee/orders";
                }
            },
            error: function (jqHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
    });
})
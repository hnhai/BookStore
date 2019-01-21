$( document ).ready(function() {
    var token = $('#_csrf_token').attr('content');
    var header = $('#_csrf_header').attr('content');
    console.log(WebContext.contextPath + "admin/users");

    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });

    // Even getAll click
    $(document).on("change","#getAll",function() {
        if ($(this).is(":checked"))
        {
            $.each($('.getIDs'), function(){
                if (!$(this).is(":checked"))
                {
                    $(this).prop('checked', true);
                }
            });
        }
        if (!$(this).is(":checked"))
        {
            $.each($('.getIDs'), function(){
                if ($(this).is(":checked"))
                {
                    $(this).prop('checked', false);
                }
            });
        }
    });
    //Even delete click
    $(document).on("click","#btn-delete-user",function() {
        var ids = [];
        $.each($("input[class='getIDs']:checked"), function(){
            ids.push($(this).data("id"));
        });
        $.ajax({
            type: "POST",
            url: WebContext.contextPath + "admin/users",
            data: {
                ids: ids
            },
            success: function (data) {
                if (data == true){
                    window.location.href = WebContext.contextPath + "admin/users";
                }
            },
            error: function (jqHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
    });

    $(document).on("click",".getIDs",function() {
        $(".getIDs").each(function() {
            $('#getAll').prop('checked', $(".getIDs").filter(':checked').length ==  $(".getIDs").length);
        });
    });

});
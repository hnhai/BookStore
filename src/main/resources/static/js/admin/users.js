$( document ).ready(function() {
    var token = $('#_csrf_token').attr('content');
    var header = $('#_csrf_header').attr('content');

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
        if(ids.length > 0){
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
        }
    });

    $(document).on("click",".getIDs",function() {
        $(".getIDs").each(function() {
            $('#getAll').prop('checked', $(".getIDs").filter(':checked').length ==  $(".getIDs").length);
        });
    });

    $('#form-createUser').submit(function (e) {
        e.preventDefault();
        var form = $(this);
        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: form.serialize(),
            success: function (msg) {
                if(msg == true){
                    window.location.href = WebContext.contextPath;
                }else{
                    $('#update-password-message').html(update_password_fail);
                }
            },
            error: function (e) {
                console.log(e);
            }
        });
    });

});
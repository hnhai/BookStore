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

    $(document).on("click",".getIDs",function() {
        $(".getIDs").each(function() {
            $('#getAll').prop('checked', $(".getIDs").filter(':checked').length ==  $(".getIDs").length);
        });
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
                url: WebContext.contextPath + "employee/deleteAuthor",
                data: {
                    ids: ids
                },
                success: function (data) {
                    if (data == true){
                        window.location.href = WebContext.contextPath + "employee/authors";
                    }
                },
                error: function (jqHR, textStatus, errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });
        }
    });
});
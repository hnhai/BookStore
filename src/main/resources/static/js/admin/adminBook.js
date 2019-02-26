$( document ).ready(function() {

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

    $(document).on("change",".image-input",function() {
        var filename = $(this).val().split('\\').pop();
        var id = '#lable-' + $(this).attr('id');
        $(id).text(filename);
        $(this).attr('name', 'images');
    });

    $(document).on("click",".btn-remove-image",function() {
        event.preventDefault();
        var id = $(this).attr('data-img-id');
        $(this).parent().attr("hidden", true);
        $('#img' + id).attr('name', 'removeImages');
        $('#img' + id).attr('type', 'text');
        $('#img' + id).attr('value', $('#lable-img' + id).text());
    });

    $(document).on("click",".getIDs",function() {
        $(".getIDs").each(function() {
            $('#getAll').prop('checked', $(".getIDs").filter(':checked').length ==  $(".getIDs").length);
        });
    });

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

    $(document).on("click",".btn-delete-image",function() {
        $(this).parent().remove();
    });

    $(document).on("click",".btn-add-image",function(e) {
        event.preventDefault();
        $(this).parent().clone().appendTo("#block-images");
    });

    $(document).on("click",".update-book",function() {
        $.ajax({
            type: "GET",
            url: WebContext.contextPath + "employee/book/" + $(this).data("id"),
            success: function (data) {
                console.log(data);
            },
            error: function (jqHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
    });

    $(document).on("keyup","#bookName",function(e) {
        var alias = convertToSlug($(this).val()).trim();
        $('#aliasName').val(alias);
        $.ajax({
            type: "GET",
            url: WebContext.contextPath + "employee/api-check-book-name/" + alias,
            success: function (data) {
                if (data == true){
                    $('#bookName-error').text($('#msg-duplicate').val());
                    $("#btn-addBook").prop('disabled', true);
                }else{
                    $("#btn-addBook").prop('disabled', false);
                    $('#bookName-error').text('');
                }
            },
            error: function (jqHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
    });

});
function convertToSlug(slug)
{
    slug = slug.replace(/á|à|ả|ạ|ã|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ/gi, 'a');
    slug = slug.replace(/é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ/gi, 'e');
    slug = slug.replace(/i|í|ì|ỉ|ĩ|ị/gi, 'i');
    slug = slug.replace(/ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ/gi, 'o');
    slug = slug.replace(/ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự/gi, 'u');
    slug = slug.replace(/ý|ỳ|ỷ|ỹ|ỵ/gi, 'y');
    slug = slug.replace(/đ/gi, 'd');
    slug = slug.replace(/\`|\~|\!|\@|\#|\||\$|\%|\^|\&|\*|\(|\)|\+|\=|\,|\.|\/|\?|\>|\<|\'|\"|\:|\;|_/gi, '');
    slug = slug.replace(/ /gi, "-");
    slug = slug.replace(/\-\-\-\-\-/gi, '-');
    slug = slug.replace(/\-\-\-\-/gi, '-');
    slug = slug.replace(/\-\-\-/gi, '-');
    slug = slug.replace(/\-\-/gi, '-');
    slug = '@' + slug + '@';
    slug = slug.replace(/\@\-|\-\@|\@/gi, '');
    return slug;
}

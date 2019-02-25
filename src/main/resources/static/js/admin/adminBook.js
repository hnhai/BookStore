$( document ).ready(function() {

    $(document).on("click",".btn-delete-image",function() {
        $(this).parent().remove();
    });
    $(document).on("click",".btn-add-image",function(e) {
        event.preventDefault();
        $(this).parent().clone().appendTo("#block-images");
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

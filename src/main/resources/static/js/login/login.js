var AGREE_TERM = true;

$( document ).ready(function() {
    $(document).on("change","#agree-terms",function() {
        if($(this).prop("checked") != true){
            AGREE_TERM = false;
            $('#btnRegister').prop("disabled", true);
        }else{
            AGREE_TERM = true;
            $('#btnRegister').prop("disabled", false);
        }
    });

    $('#formRegister').submit(function () {
        if(!AGREE_TERM){
            return false;
        }
    });
});
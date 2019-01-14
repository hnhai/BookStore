// Start validate Register Page
var AGREE_TERM = true;
var VALID_USERNAME = true;
var VALID_EMAIL = true;

$( document ).ready(function() {

    //Validate AgreeTerm
    $(document).on("change","#agree-terms",function() {
        if($(this).prop("checked") != true){
            AGREE_TERM = false;
            $('#btnRegister').prop("disabled", true);
        }else{
            AGREE_TERM = true;
            $('#btnRegister').prop("disabled", false);
        }
    });

    // Validate Form
    $('#formRegister').submit(function () {
        if(!AGREE_TERM || !VALID_USERNAME || !VALID_EMAIL){
            return false;
        }
    });

    // Validate UserName
    $(document).on("keyup", "#username", function () {
        if ($(this).val() != '') {
            $.ajax({
                type: "GET",
                url: WebContext.contextPath + "api-check-username/" + $(this).val(),
                success: function (data) {
                    if (data == true) {
                        $('#username').removeClass('parsley-success');
                        $('#username').addClass('parsley-error');
                        VALID_USERNAME = false;
                        $('#btnRegister').prop("disabled", true);
                        $('#username-error').html('This username is exsisted !')
                    } else {
                        $('#username').removeClass('parsley-error');
                        $('#username').addClass('parsley-success');
                        VALID_USERNAME = true;
                        $('#btnRegister').prop("disabled", false);
                        $('#username-error').html('');
                    }
                },
                error: function (jqHR, textStatus, errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });
        }
    });

    // Validate Email
    $(document).on("keyup", "#email", function () {
        if ($(this).val() != '') {
            $.ajax({
                type: "GET",
                url: WebContext.contextPath + "api-check-email/" + $(this).val(),
                success: function (data) {
                    if (data == true) {
                        $('#email').removeClass('parsley-success');
                        $('#email').addClass('parsley-error');
                        VALID_EMAIL = false;
                        $('#btnRegister').prop("disabled", true);
                        $('#email-error').html('This email is exsisted !')
                    } else {
                        $('#email').removeClass('parsley-error');
                        $('#email').addClass('parsley-success');
                        VALID_EMAIL = true;
                        $('#btnRegister').prop("disabled", false);
                        $('#email-error').html('');
                    }
                },
                error: function (jqHR, textStatus, errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });
        }
    });

});

// End validate Register Page
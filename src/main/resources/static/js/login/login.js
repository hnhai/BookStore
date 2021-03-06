// Start validate Register Page
var AGREE_TERM = true;
var VALID_USERNAME = true;
var VALID_EMAIL = true;

$( document ).ready(function() {
    var msg_username_existed = $('#register-existed-account').val();
    var msg_email_existed = $('#register-existed-email').val();
    var update_password_fail = $('#update-password-fail').val();
    var login_error_username_not_exitst = $('#login-reset-username-not-exitst').val();
    var login_reset_success = $('#login-reset-succcess').val();
    var token = $('#_csrf_token').attr('content');
    var header = $('#_csrf_header').attr('content');

    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });

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
                        $('#username-error').html(msg_username_existed);
                    } else {
                        $('#username').removeClass('parsley-error');
                        $('#username').addClass('parsley-success');
                        VALID_USERNAME = true;
                        if(VALID_EMAIL){
                            $('#btnRegister').prop("disabled", false);
                        }
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
                        $('#email-error').html(msg_email_existed);
                    } else {
                        $('#email').removeClass('parsley-error');
                        $('#email').addClass('parsley-success');
                        VALID_EMAIL = true;
                        if(VALID_USERNAME){
                            $('#btnRegister').prop("disabled", false);
                        }
                        $('#email-error').html('');
                    }
                },
                error: function (jqHR, textStatus, errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });
        }
    });
    // End validate Register Page

    // On click reset-password
    $(document).on("click","#reset-password",function() {
        if($('#username-login').val() == ''){
            $('#username-error').html('Please input username or email !');
        }else{
            $('#username-error').html('');
            $.ajax({
                type: "POST",
                url: WebContext.contextPath + "reset-password/" + $('#username-login').val(),
                success: function (data) {
                    if(data == true){
                        $('#reset-password-message').removeClass('message-parsley-errors');
                        $('#reset-password-message').addClass('message-parsley-success');
                        $('#reset-password-message').html(login_reset_success)
                    }else{
                        $('#reset-password-message').removeClass('message-parsley-success');
                        $('#reset-password-message').addClass('message-parsley-errors');
                        $('#reset-password-message').html(login_error_username_not_exitst)
                    }
                },
                error: function (jqHR, textStatus, errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });
        }
    });

    $('#form-update-password').submit(function (e) {
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
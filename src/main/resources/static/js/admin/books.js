$( document ).ready(function() {
    $( "#btn-search" ).click(function() {
        var query = [];
        var url = document.location.href;
        //remove old param
        url = removeURLParameter(url, 'name');
        url = removeURLParameter(url, 'author');
        url = removeURLParameter(url, 'price1');
        url = removeURLParameter(url, 'price2');
        //add new params
        query.push('name=' + $('#book-name').val());
        query.push('author=' + $('#author-name').val());
        query.push('price1=' + $('#price2').val());
        query.push('price2=' + $('#price1').val());
        if(url.indexOf('?') > -1) {
            url = url +"&" + query.join('&');
        }else{
            url =  url +"?" + query.join('&');
        }
        document.location = url;
    });

    //Set value from URL to input
    $('#book-name').val(getUrlParameter('name'));
    $('#author-name').val(getUrlParameter('author'));
    $('#price2').val(getUrlParameter('price1'));
    $('#price1').val(getUrlParameter('price2'));
});

//function get param
function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};

//function removeParam
function removeURLParameter(url, parameter) {
    //prefer to use l.search if you have a location/link object
    var urlparts = url.split('?');
    if (urlparts.length >= 2) {

        var prefix = encodeURIComponent(parameter) + '=';
        var pars = urlparts[1].split(/[&;]/g);

        //reverse iteration as may be destructive
        for (var i = pars.length; i-- > 0;) {
            //idiom for string.startsWith
            if (pars[i].lastIndexOf(prefix, 0) !== -1) {
                pars.splice(i, 1);
            }
        }

        return urlparts[0] + (pars.length > 0 ? '?' + pars.join('&') : '');
    }
    return url;
}


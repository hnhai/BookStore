$(document).ready(function() {
    $(function() {
        $('.date-picker').datepicker( {
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,
            dateFormat: 'mm/yy',
            onClose: function(dateText, inst) {
                $(this).datepicker('setDate', new Date(inst.selectedYear, inst.selectedMonth, 1));
                var month = $('#startDate').val().substring(0, 2);
                var year = $('#startDate').val().substring(3, 7);
                $.ajax({
                    type: "GET",
                    url: WebContext.contextPath + "admin/api/chart/" + month + "/" + year,
                    success: function (data) {
                        series = data;
                        var chart = {
                            type : 'column'
                        };
                        var title = {
                            text : 'Category have Books sell in a month'
                        };
                        var credits = {
                            enabled : false
                        };
                        var json = {};
                        json.chart = chart;
                        json.title = title;
                        json.credits = credits
                        json.series = series;
                        console.log(series);
                        $('#container').highcharts(json);
                    },
                    error: function (jqHR, textStatus, errorThrown) {
                        console.log(textStatus, errorThrown);
                    }
                });
            }
        });
    });

});
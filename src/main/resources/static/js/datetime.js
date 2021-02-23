// timepickerDuration

$(document).ready(function () {
    $('.timepicker').timepicker({
        timeFormat: 'HH:mm',
        interval: 15,
        minTime: '13:00',
        maxTime: '21:00',
        defaultTime: '16:00',
        startTime: '12:00',
        dynamic: false,
        dropdown: true,
        scrollbar: true,
        zindex: 9999999
    });

    $('.timepickerDuration').timepicker({
        timeFormat: 'HH:mm',
        interval: 15,
        minTime: '00:15',
        maxTime: '02:00',
        defaultTime: '00:15',
        startTime: '00:00',
        dynamic: false,
        dropdown: true,
        scrollbar: true,
        zindex: 9999999
    });

    $(".reservationDate").flatpickr({
        enableTime: false,
        dateFormat: "Y-m-d",
        minDate: "today"
    });


});


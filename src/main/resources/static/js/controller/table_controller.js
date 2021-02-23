'use strict';


App.controller('TableController', ['$scope', 'Reservation', 'Table', '$timeout', 'Util', function ($scope, Reservation, Table, $timeout, Util) {


    var self = this;

    self.tables = [];
    self.date = '';
    self.startTime = '';
    self.duration = '';

    self.invalidDate = false;
    self.invalidStartTime = false;
    self.invalidDuration = false;


    self.feeback = false;
    self.feebackMessage = '';

    self.errorMessage = "" ;


    self.setFeedback = function (message, timeout) {
        self.feebackMessage = message;
        self.feeback = true;

        $timeout(function () {
            self.feeback = false;
        }, timeout);


    };

    self.getTables = function () {
        self.startTime = document.getElementById('startTime').value;
        self.duration = document.getElementById('duration').value;

        let validationFail = false;

        if (!self.date) {
            self.invalidDate = true;
            validationFail = true;
        }

        if (!self.startTime) {
            self.invalidStartTime = true;
            validationFail = true;
        }

        if (!self.duration) {
            self.invalidDuration = true;
            validationFail = true;
        }

        if (validationFail)
            return;

        self.invalidDate = false;
        self.invalidStartTime = false;
        self.invalidDuration = false;


        // console.log(self.date + "|" + self.startTime + "|" + self.duration);


        let startTimeMoment = moment(self.date + ' ' + self.startTime, "YYYY-MM-DD HH:mm", true);
        let startTimeString = startTimeMoment.format("YYYY-MM-DD HH:mm:ss", true);

        let endtimeMoment = startTimeMoment.add(moment.duration(self.duration + ':00'));
        let endTimeString = endtimeMoment.format("YYYY-MM-DD HH:mm:ss", true);

        console.log(startTimeString);
        console.log(endTimeString);


        Table.query({
            startTime: startTimeString,
            endTime: endTimeString
        }, function (result, responseHeaders) {
            self.tables = result;
            // console.log(JSON.stringify(self.tables));
            if (!result.length)
                self.setFeedback("No free tables", 2000);
        }, function (httpResponse) {
            console.log('Error: ' + httpResponse);
            self.setFeedback("Couldnt get tables", 3000);

            if(httpResponse.data)
                self.errorMessage = Util.parseError(httpResponse.data) ;
            else
                self.errorMessage = Util.parseError(httpResponse.status) ;

        });


    };



}]);
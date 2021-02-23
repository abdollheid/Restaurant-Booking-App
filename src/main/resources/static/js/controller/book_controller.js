'use strict';


App.controller('BookController', ['$scope', 'Reservation', 'Table', '$timeout', '$http' ,'Util', function ($scope, Reservation, Table, $timeout, $http, Util) {

    var self = this;

    self.reservations = [];
    self.date = '';
    self.startTime = '';
    self.duration = '';
    self.size = 0;

    self.invalidDate = false;
    self.invalidStartTime = false;
    self.invalidDuration = false;
    self.invalidSize = false;

    self.feeback = false;
    self.feebackMessage = '';


    self.setFeedback = function (message, timeout) {
        self.feebackMessage = message;
        self.feeback = true;

        $timeout(function () {
            self.feeback = false;
        }, timeout);


    };



    self.errorMessage = "";


    $http.defaults.headers.common['Accept'] = 'application/json';

    self.bookTable = function () {

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

        if (!self.size || self.size < 1) {
            self.invalidSize = true;
            validationFail = true;
        }

        if (validationFail)
            return;

        self.invalidDate = false;
        self.invalidStartTime = false;
        self.invalidDuration = false;
        self.invalidSize = false;
        self.errorMessage = '';


        // console.log(self.date + "|" + self.startTime + "|" + self.duration + "|" + self.size);


        let startTimeMoment = moment(self.date + ' ' + self.startTime, "YYYY-MM-DD HH:mm", true);
        let startTimeString = startTimeMoment.format("YYYY-MM-DD HH:mm:ss", true);

        let endtimeMoment = startTimeMoment.add(moment.duration(self.duration + ':00'));
        let endTimeString = endtimeMoment.format("YYYY-MM-DD HH:mm:ss", true);

        // console.log(startTimeString);
        // console.log(endTimeString);


        $http.post('/api/reservation', {
            startTime: startTimeString,
            endTime: endTimeString,
            size: self.size
        })
            .success(function (data, status, headers, config) {
                console.log('success');
                console.log('status' + status);
                // console.log('data' + JSON.stringify(data));
                switch (status) {
                    case 201:
                        self.setFeedback("Reservation made", 1000);
                        self.reservations.push(data);
                        break;
                    case 204:
                        self.setFeedback("Reservation couldnt be made, No available tables for the specified date/size", 2000);
                        break;
                    default:
                        console.log("Unexpected status: " + status);
                        self.setFeedback("Unknown status: " + status + ",result: " + data, 30000);
                }

            })
            .error(function (data, status, headers, config) {
                console.log('failed');
                console.log('status' + status);
                // console.log('data' + JSON.stringify(data));
                self.setFeedback("Error heppened", 3000);
                self.errorMessage = Util.parseError(data);

                // console.log(self.errorMessage);

            });


    };


}]);

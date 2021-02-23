'use strict';


App.controller('AllReservationController', ['$scope', 'AdminReservation', '$timeout', function ($scope, AdminReservation, $timeout) {

    var self = this;


    self.reservations = [];

    self.date = '';
    self.invalidDate = false;


    self.feeback = false;
    self.feebackMessage = '';


    self.setFeedback = function (message, timeout) {
        self.feebackMessage = message;
        self.feeback = true;

        $timeout(function () {
            self.feeback = false;
        }, timeout);


    };

    self.getReservations = function () {
        if (!self.date) {
            self.invalidDate = true;
            return;
        }

        self.invalidDate = false;

        AdminReservation.query({date: self.date}, function (result, responseHeaders) {
            self.reservations = result;
            // console.log(JSON.stringify(self.reservations));

            if (!result.length)
                self.setFeedback("No reservation found", 2000);

        }, function (httpResponse) {
            console.log('Error: ' + httpResponse);
            self.setFeedback("Error: " + httpResponse, 3000);
        });
    };


}]);
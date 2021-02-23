'use strict';


App.controller('HomeController', ['$scope', 'Reservation', 'Table', '$timeout', function ($scope, Reservation, Table, $timeout) {

    var self = this;


    self.reservations = [];
    self.date = '';
    self.startTime = '';
    self.duration = '';

    self.invalidDate = false;
    self.invalidStartTime = false;
    self.invalidDuration = false;

    self.feeback = false;
    self.feebackMessage = '';


    self.setFeedback = function (message, timeout) {
        self.feebackMessage = message;
        self.feeback = true;

        $timeout(function () {
            self.feeback = false;
        }, timeout);


    };

    self.fetchAllReservation = function () {
        Reservation.query(function (result, responseHeaders) {
            self.reservations = result;
            // console.log(JSON.stringify(self.reservations));

        }, function (httpResponse) {
            console.log('Error Fetching Reservaton: ' + httpResponse, 3000);
            self.setFeedback('Error Fetching Reservaton: ' + httpResponse, 3000);
        });

    };


    self.fetchAllReservation();



}]);
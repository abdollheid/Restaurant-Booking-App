'use strict';

App.controller('AuhController', ['$scope', '$location', '$http', function ($scope, $location, $http) {
    var self = this;

    $http.defaults.headers.common['Accept'] = 'application/json';


    self.reset = function () {
        self.unAuthorized = false;

        self.invalidEmail = false;
        self.emailMessage = '';

        self.invalidName = false;
        self.nameMessage = '';

        self.invalidPassword = false;
        self.passwordMessage = '';

        self.invalidMobile = false;
        self.mobileMessage = '';

    }

    self.reset();


    self.feeback = false;
    self.feebackMessage = '';


    self.setFeedback = function (message, timeout) {
        self.feebackMessage = message;
        self.feeback = true;

        $timeout(function () {
            self.feeback = false;
        }, timeout);


    };

    self.submit = function () {
        self.reset();
        var auth = btoa(self.user.email + ':' + self.user.password);

        $http.defaults.headers.common['Authorization'] = 'Basic ' + auth;


        $http.get('/')
            .success(function (data, status, headers, config) {
                console.log('success:');
                window.location = '/';
            })
            .error(function (data, status, headers, config) {
                console.log('failed');
                if (status === 401) {
                    self.unAuthorized = true;
                }
            });
    };


    self.registerUser = function () {
        self.reset();


        $http.post('/api/register', {
            name: self.user.name,
            email: self.user.email,
            password: self.user.password,
            mobile: self.user.mobile
        })
            .success(function (data, status, headers, config) {
                console.log('success:');
                console.log('status' + status);
                // console.log('data' + JSON.stringify(data));
                window.location = '/';
            })
            .error(function (data, status, headers, config) {
                console.log('failed');
                console.log('status' + status);
                // console.log('data' + JSON.stringify(data));

                if (data.errors && data.errors[0].error && data.errors[0].message) {
                    for (let i = 0; i < data.errors.length; ++i) {
                        // console.log(data.errors[i]);
                        switch (data.errors[i].error) {
                            case "email":
                                self.invalidEmail = true;
                                self.emailMessage = self.emailMessage.length < 1 ? data.errors[i].message : self.emailMessage + ', ' + data.errors[i].message;
                                break;
                            case "name":
                                self.invalidName = true;
                                self.nameMessage = self.nameMessage.length < 1 ? data.errors[i].message : self.nameMessage + ', ' + data.errors[i].message;
                                break;
                            case "password":
                                self.invalidPassword = true;
                                self.passwordMessage = self.passwordMessage.length < 1 ? data.errors[i].message : self.passwordMessage + ', ' + data.errors[i].message;
                                break;
                            case "mobile":
                                self.invalidMobile = true;
                                self.mobileMessage = self.mobileMessage.length < 1 ? data.errors[i].message : self.mobileMessage + ', ' + data.errors[i].message;

                                break;
                            default:
                                console.log("Unexpected error: " + data.errors[i].error);
                        }
                    }
                }else{
                    console.log("Unexpected error: " +  JSON.stringify(data));
                    self.setFeedback("Unexpected error, see Console for more information", 30000) ;
                }
            });
    };


}]);

'use strict';


App.controller('AllTableController', ['$scope',  'AdminTable', '$timeout', function ($scope, AdminTable, $timeout) {


    var self = this;


    self.tables = [];


    self.size = 0 ;
    self.invalidSize = false ;

    self.feeback = false;
    self.feebackMessage = '';


    self.setFeedback = function (message, timeout) {
        self.feebackMessage = message;
        self.feeback = true;

        $timeout(function () {
            self.feeback = false;
        }, timeout);


    };






    self.getTables = function () {


        AdminTable.query({}, function (result, responseHeaders) {
            self.tables = result;
            // console.log(JSON.stringify(self.tables));
            if (!result.length)
                self.setFeedback("No Tables Found tables", 2000);
        }, function (httpResponse) {
            console.log('Error: ' + httpResponse);
            self.setFeedback("Couldnt get tables: " + httpResponse, 3000);
        });


    };

    self.addTable = function () {
        if(!self.size || self.size < 1){
            self.size = 0 ;
            self.invalidSize = true ;
            return ;
        }
        console.log(self.size) ;
        self.invalidSize = false;

        AdminTable.save({},{
            size: self.size
        }, function (result, responseHeaders){
            // console.log(JSON.stringify(result));
            self.setFeedback("Table added",1000);
            self.getTables();


        }, function (httpResponse) {
            console.log('Error while adding table: ' + httpResponse);
            self.setFeedback("Couldnt add table" + httpResponse, 3000);
        })

    }


    self.getTables();




}]);
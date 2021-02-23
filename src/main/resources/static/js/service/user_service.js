'use strict';


App.factory('Reservation', ['$resource', function ($resource) {
    //$resource() function returns an object of resource class
    return $resource(
        '/api/reservation',
        {},
        {}
    );
}])
    .factory('Table', ['$resource', function ($resource) {
        //$resource() function returns an object of resource class
        return $resource(
            '/api/table',
            {},
            {}
        );
    }]);
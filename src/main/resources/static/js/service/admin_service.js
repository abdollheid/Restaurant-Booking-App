'use strict';


App.factory('AdminReservation', ['$resource', function ($resource) {
    //$resource() function returns an object of resource class
    return $resource(
        '/api/admin/reservation',
        {},
        {}
    );
}])
    .factory('AdminTable', ['$resource', function ($resource) {
        //$resource() function returns an object of resource class
        return $resource(
            '/api/admin/table',
            {},
            {}
        );
    }]);
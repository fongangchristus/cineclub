(function() {
    'use strict';
    angular
        .module('cineclubApp')
        .factory('TicketReserve', TicketReserve);

    TicketReserve.$inject = ['$resource'];

    function TicketReserve ($resource) {
        var resourceUrl =  'api/ticket-reserves/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

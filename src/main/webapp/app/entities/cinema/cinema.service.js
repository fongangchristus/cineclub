(function() {
    'use strict';
    angular
        .module('cineclubApp')
        .factory('Cinema', Cinema);

    Cinema.$inject = ['$resource'];

    function Cinema ($resource) {
        var resourceUrl =  'api/cinemas/:id';

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

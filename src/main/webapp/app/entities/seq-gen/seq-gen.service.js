(function() {
    'use strict';
    angular
        .module('cineclubApp')
        .factory('SeqGen', SeqGen);

    SeqGen.$inject = ['$resource'];

    function SeqGen ($resource) {
        var resourceUrl =  'api/seq-gens/:id';

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

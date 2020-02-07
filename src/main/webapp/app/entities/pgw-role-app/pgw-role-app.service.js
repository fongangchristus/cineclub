(function() {
    'use strict';
    angular
        .module('cineclubApp')
        .factory('PgwRoleApp', PgwRoleApp);

    PgwRoleApp.$inject = ['$resource'];

    function PgwRoleApp ($resource) {
        var resourceUrl =  'api/pgw-role-apps/:id';

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

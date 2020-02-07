(function() {
    'use strict';
    angular
        .module('cineclubApp')
        .factory('Film', Film);

    Film.$inject = ['$resource', 'DateUtils'];

    function Film ($resource, DateUtils) {
        var resourceUrl =  'api/films/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateSortie = DateUtils.convertLocalDateFromServer(data.dateSortie);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateSortie = DateUtils.convertLocalDateToServer(copy.dateSortie);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateSortie = DateUtils.convertLocalDateToServer(copy.dateSortie);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('cineclubApp')
        .factory('Formule', Formule);

    Formule.$inject = ['$resource', 'DateUtils'];

    function Formule ($resource, DateUtils) {
        var resourceUrl =  'api/formules/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateReservation = DateUtils.convertLocalDateFromServer(data.dateReservation);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateReservation = DateUtils.convertLocalDateToServer(copy.dateReservation);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateReservation = DateUtils.convertLocalDateToServer(copy.dateReservation);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('rsdataApp')
        .factory('Accident', Accident);

    Accident.$inject = ['$resource', 'DateUtils'];

    function Accident ($resource, DateUtils) {
        var resourceUrl =  'api/accidents/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateAcc = DateUtils.convertDateTimeFromServer(data.dateAcc);
                        data.dateCreation = DateUtils.convertDateTimeFromServer(data.dateCreation);
                        data.heure = DateUtils.convertDateTimeFromServer(data.heure);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

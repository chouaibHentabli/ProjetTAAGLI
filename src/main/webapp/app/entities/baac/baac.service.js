(function() {
    'use strict';
    angular
        .module('rsdataApp')
        .factory('Baac', Baac);

    Baac.$inject = ['$resource', 'DateUtils'];

    function Baac ($resource, DateUtils) {
        var resourceUrl =  'api/baacs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateCreation = DateUtils.convertDateTimeFromServer(data.dateCreation);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('rsdataApp')
        .factory('Modification', Modification);

    Modification.$inject = ['$resource', 'DateUtils'];

    function Modification ($resource, DateUtils) {
        var resourceUrl =  'api/modifications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateMod = DateUtils.convertDateTimeFromServer(data.dateMod);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

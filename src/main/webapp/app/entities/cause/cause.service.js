(function() {
    'use strict';
    angular
        .module('rsdataApp')
        .factory('Cause', Cause);

    Cause.$inject = ['$resource'];

    function Cause ($resource) {
        var resourceUrl =  'api/causes/:id';

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

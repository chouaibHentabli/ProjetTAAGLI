(function() {
    'use strict';
    angular
        .module('rsdataApp')
        .factory('Variable', Variable);

    Variable.$inject = ['$resource'];

    function Variable ($resource) {
        var resourceUrl =  'api/variables/:id';

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

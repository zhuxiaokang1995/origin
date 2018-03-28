(function() {
    'use strict';
    angular
        .module('holleyImsApp')
        .factory('Sn', Sn);

    Sn.$inject = ['$resource', 'DateUtils'];

    function Sn ($resource, DateUtils) {
        var resourceUrl =  'api/sns/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.bindingTime = DateUtils.convertDateTimeFromServer(data.bindingTime);
                        data.unbundlingTime = DateUtils.convertDateTimeFromServer(data.unbundlingTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

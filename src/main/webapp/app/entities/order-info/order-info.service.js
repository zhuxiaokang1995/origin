(function() {
    'use strict';
    angular
        .module('holleyImsApp')
        .factory('OrderInfo', OrderInfo);

    OrderInfo.$inject = ['$resource'];

    function OrderInfo ($resource) {
        var resourceUrl =  'api/order-infos/:id';

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

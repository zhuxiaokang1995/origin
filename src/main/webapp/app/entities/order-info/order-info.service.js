(function() {
    'use strict';
    angular
        .module('holleyImsApp')
        .factory('OrderInfo', OrderInfo);

    OrderInfo.$inject = ['$resource', 'DateUtils'];

    function OrderInfo ($resource, DateUtils) {
        var resourceUrl =  'api/order-infos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.planStartDate = DateUtils.convertDateTimeFromServer(data.planStartDate);
                        data.planEndDate = DateUtils.convertDateTimeFromServer(data.planEndDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

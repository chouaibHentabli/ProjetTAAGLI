(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('baac', {
            parent: 'accident',
            url: '/{id}/baac',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rsdataApp.baac.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/baac/baacs.html',
                    controller: 'BaacController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            /*resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('baac');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }*/
            resolve: {

                            entity: ['$stateParams', 'Accident', function($stateParams, Accident) {
                                return Accident.get({id : $stateParams.id}).$promise;
                            }],
                            previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'accident',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                        }
        })
        .state('baac-detail', {
            parent: 'entity',
            url: '/baac/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rsdataApp.baac.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/baac/baac-detail.html',
                    controller: 'BaacDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('baac');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Baac', function($stateParams, Baac) {
                    return Baac.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'baac',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('baac-detail.edit', {
            parent: 'baac-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/baac/baac-dialog.html',
                    controller: 'BaacDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Baac', function(Baac) {
                            return Baac.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('baac.new', {
            parent: 'baac',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/baac/baac-dialog.html',
                    controller: 'BaacDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dateCreation: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('baac', null, { reload: 'baac' });
                }, function() {
                    $state.go('baac');
                });
            }]
        })
        .state('baac.edit', {
            parent: 'baac',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/baac/baac-dialog.html',
                    controller: 'BaacDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Baac', function(Baac) {
                            return Baac.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('baac', null, { reload: 'baac' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('baac.delete', {
            parent: 'baac',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/baac/baac-delete-dialog.html',
                    controller: 'BaacDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Baac', function(Baac) {
                            return Baac.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('baac', null, { reload: 'baac' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

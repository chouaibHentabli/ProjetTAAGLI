(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('accident', {
            parent: 'entity',
            url: '/accident?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rsdataApp.accident.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/accident/accidents.html',
                    controller: 'AccidentController',
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
            resolve: {
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
                    $translatePartialLoader.addPart('accident');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('accident-detail', {
            parent: 'entity',
            url: '/accident/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rsdataApp.accident.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/accident/accident-detail.html',
                    controller: 'AccidentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('accident');
                    return $translate.refresh();
                }],
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
        .state('accident-detail.edit', {
            parent: 'accident-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/accident/accident-dialog.html',
                    controller: 'AccidentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Accident', function(Accident) {
                            return Accident.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('accident.new', {
            parent: 'accident',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/accident/accident-dialog.html',
                    controller: 'AccidentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dateAcc: null,
                                dateCreation: null,
                                heure: null,
                                latitude: null,
                                longitude: null,
                                status: null,
                                nbrBlesse: null,
                                nbrHospitalise: null,
                                nbrMorts: null,
                                nbrIndemne: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('accident', null, { reload: 'accident' });
                }, function() {
                    $state.go('accident');
                });
            }]
        })
        .state('accident.edit', {
            parent: 'accident',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/accident/accident-dialog.html',
                    controller: 'AccidentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Accident', function(Accident) {
                            return Accident.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('accident', null, { reload: 'accident' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('accident.delete', {
            parent: 'accident',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/accident/accident-delete-dialog.html',
                    controller: 'AccidentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Accident', function(Accident) {
                            return Accident.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('accident', null, { reload: 'accident' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

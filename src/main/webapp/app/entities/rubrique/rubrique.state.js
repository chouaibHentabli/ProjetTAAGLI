(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rubrique', {
            parent: 'entity',
            url: '/rubrique?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rsdataApp.rubrique.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rubrique/rubriques.html',
                    controller: 'RubriqueController',
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
                    $translatePartialLoader.addPart('rubrique');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('rubrique-detail', {
            parent: 'entity',
            url: '/rubrique/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rsdataApp.rubrique.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rubrique/rubrique-detail.html',
                    controller: 'RubriqueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rubrique');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Rubrique', function($stateParams, Rubrique) {
                    return Rubrique.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rubrique',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rubrique-detail.edit', {
            parent: 'rubrique-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rubrique/rubrique-dialog.html',
                    controller: 'RubriqueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rubrique', function(Rubrique) {
                            return Rubrique.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rubrique.new', {
            parent: 'rubrique',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rubrique/rubrique-dialog.html',
                    controller: 'RubriqueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                rubUnique: null,
                                active: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rubrique', null, { reload: 'rubrique' });
                }, function() {
                    $state.go('rubrique');
                });
            }]
        })
        .state('rubrique.edit', {
            parent: 'rubrique',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rubrique/rubrique-dialog.html',
                    controller: 'RubriqueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rubrique', function(Rubrique) {
                            return Rubrique.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rubrique', null, { reload: 'rubrique' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rubrique.delete', {
            parent: 'rubrique',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rubrique/rubrique-delete-dialog.html',
                    controller: 'RubriqueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Rubrique', function(Rubrique) {
                            return Rubrique.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rubrique', null, { reload: 'rubrique' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

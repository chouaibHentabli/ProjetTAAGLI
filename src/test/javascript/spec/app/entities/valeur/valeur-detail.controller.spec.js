'use strict';

describe('Controller Tests', function() {

    describe('Valeur Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockValeur, MockVariable, MockBaac;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockValeur = jasmine.createSpy('MockValeur');
            MockVariable = jasmine.createSpy('MockVariable');
            MockBaac = jasmine.createSpy('MockBaac');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Valeur': MockValeur,
                'Variable': MockVariable,
                'Baac': MockBaac
            };
            createController = function() {
                $injector.get('$controller')("ValeurDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rsdataApp:valeurUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

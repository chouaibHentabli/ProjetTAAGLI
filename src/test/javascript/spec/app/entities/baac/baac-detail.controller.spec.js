'use strict';

describe('Controller Tests', function() {

    describe('Baac Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBaac, MockModification, MockValeur, MockAccident, MockRubrique;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBaac = jasmine.createSpy('MockBaac');
            MockModification = jasmine.createSpy('MockModification');
            MockValeur = jasmine.createSpy('MockValeur');
            MockAccident = jasmine.createSpy('MockAccident');
            MockRubrique = jasmine.createSpy('MockRubrique');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Baac': MockBaac,
                'Modification': MockModification,
                'Valeur': MockValeur,
                'Accident': MockAccident,
                'Rubrique': MockRubrique
            };
            createController = function() {
                $injector.get('$controller')("BaacDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rsdataApp:baacUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

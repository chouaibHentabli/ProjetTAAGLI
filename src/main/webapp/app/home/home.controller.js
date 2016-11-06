(function() {
	'use strict';

	var rsdataApp = angular.module('rsdataApp').controller('HomeController', HomeController);

	HomeController.$inject = [ '$scope', 'Principal', 'LoginService', '$state',
			'Accident' ];

	function HomeController($scope, Principal, LoginService, $state, Accident) {
		var vm = this;
		

//		  $scope.labels = ["January", "February", "March", "April", "May", "June", "July"];
//		  $scope.series = ['Series A', 'Series B'];
//		  $scope.data = [
//		    [65, 59, 80, 81, 56, 55, 40],
//		    [28, 48, 40, 19, 86, 27, 90]
//		  ];
		  
		  $scope.onClick = function (points, evt) {
		    console.log(points, evt);
		  };
		  $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
//		  $scope.options = {
//		    scales: {
//		      yAxes: [
//		        {
//		          id: 'y-axis-1',
//		          type: 'linear',
//		          display: true,
//		          position: 'left'
//		        },
//		        {
//		          id: 'y-axis-2',
//		          type: 'linear',
//		          display: true,
//		          position: 'right'
//		        }
//		      ]
//		    }
//		  };

		vm.account = null;
		vm.isAuthenticated = null;
		vm.login = LoginService.open;
		vm.register = register;
		$scope.$on('authenticationSuccess', function() {
			getAccount();
		});

		getAccount();

		function getAccount() {
			Principal.identity().then(function(account) {
				vm.account = account;
				vm.isAuthenticated = Principal.isAuthenticated;
				Accident.query().$promise.then(function(dataResponse) {
					vm.accidents = dataResponse
				}, function(error) {
					console.error("%o", error);
				})
			});
		}
		function register() {
			$state.go('register');
		}
	}
	rsdataApp.controller('accidentListController',
			accidentListController);

	accidentListController.$inject = [ '$scope', 'Principal', 'LoginService',
			'$state', 'Accident' ];

	function accidentListController($scope, Principal, LoginService, $state,
			Accident) {

		$scope.maxScreenHeight = {'max-height':$( window ).height()-200};
		Accident.query().$promise.then(function(dataResponse) {
			$scope.accidents = dataResponse
			console.info("list: %o", dataResponse);
		}, function(error) {
			console.error("%o", error);
		});
		$scope.showDetail = function(item) {
			$scope.tab = 'home';
			$scope.labels = ["Injured people", "Hospitilized people", "Unscathed", "Dead people"];
			$scope.data = [item.nbrBlesse, item.nbrHospitalise, item.nbrIndemne, item.nbrMorts];
			$scope.options = {legend: {display: true, position: 'left'}};
			$scope.selectedItem = item;
		}
		$scope.closeDetail = function(){
	        $scope.selectedItem = null;
	    }
	}
	rsdataApp.filter('groupByDay', [
		'$parse',
		function($parse) {
			return function(list, group_by) {

				var filtered = [];
				var prev_item = null;
				var group_changed = false;
				// this is a new field which is added to each item where we
				// append "_CHANGED"
				// to indicate a field change in the list
				// was var new_field = group_by + '_CHANGED'; - JB 12/17/2013
				var new_field = 'group_by_day_CHANGED';
				// loop through each item in the list
				angular.forEach(list, function(item) {
					group_changed = false;
					// if not the first item
					if (prev_item !== null) {
						// check if any of the group by field changed
						// force group_by into Array
						group_by = angular.isArray(group_by) ? group_by
								: [ group_by ];
						// check each group by parameter
						for (var i = 0, len = group_by.length; i < len; i++) {
							$parse(group_by[i])(prev_item);
							$parse(group_by[i])(item);

							var date1 = new Date(prev_item.dateAcc);
							var date2 = new Date(item.dateAcc);
//							date1.setTime(prev_item.dateAcc);
//							date2.setTime(item.dateAcc);
							var day1 = date1.getUTCFullYear() + "-"
									+ date1.getUTCMonth() + 1 + "-"
									+ date1.getUTCDate();
							var day2 = date2.getUTCFullYear() + "-"
									+ date2.getUTCMonth() + 1 + "-"
									+ date2.getUTCDate();

							if (day1 !== day2) {
								group_changed = true;
							}
							// if ($parse(group_by[i])(prev_item) !==
							// $parse(group_by[i])(item) ) {
							// group_changed = true;
							// }
						}
					}// otherwise we have the first item in the list which is
					// new
					else {
						group_changed = true;
					}
					// if the group changed, then add a new field to the item
					// to indicate this
					if (group_changed) {
						item[new_field] = true;
					} else {
						item[new_field] = false;
					}
					filtered.push(item);
					prev_item = item;
				});
				return filtered;
			};
		} ]);
})();



	
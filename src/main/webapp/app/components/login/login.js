//La premiere instruction c'est de declarer un module
var login = angular.module("login",["ngRoute", "ngResource", "ui-notification"]);

login.config(function ($routeProvider) {
    $routeProvider
        
		.when('/login',
            {
                templateUrl:'login.html'
            })
        .when('/regitre',
        {
            templateUrl:'inscription.html'
        })
       
        .otherwise({ redirectTo: '/login' });
})       .run(function($rootScope, $location){
    $rootScope.$on("$routeChangeStart", function (event, next, current) {
        $rootScope.view = $location.path();

    });
    
});


login.controller('loginController', function ($scope, $location, Notification) {

    $scope.tryToLogin = function (user) {
        console.info("user %o",user);
		if(user != undefined && user.email != undefined
		&& user.password != undefined){
			if(user.email === "anis@anis.com" && user.password === "anis"){
				Notification.success('Success notification');
				window.location.replace("index2.html");
			}	else{
				Notification.error('Error User undefined');
			}				
			
			/*LoginService.tryToLogin().$promise.then(
				function(dataResponse){
					console.info("response %o", dataResponse);
				}
				, function(error){
					console.error(" error %o", error);
				});*/
			
			//if($scope.user
		}else{
			
		}
    };

	 $scope.navigateTo = function (view) {
		 $location.path(view);
	 };
     
});

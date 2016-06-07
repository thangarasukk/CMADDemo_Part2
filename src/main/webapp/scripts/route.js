angular.module('CMAD').config(function($routeProvider){
	$routeProvider.when('/login',{
		templateUrl : 'tmpl/login.html',
		controller : 'LoginController',
		controllerAs : 'loginCtrl'
	})

	.when('/signup', {
		templateUrl : 'tmpl/signup.html',
		controller : 'SignUpController',
		controllerAs : 'signupCtrl'
	})

	.when('/', {
		templateUrl : 'tmpl/home.html',
		controller : 'HomeController',
		controllerAs : 'homeCtrl'
	})
	.otherwise({
		redirectTo : '/'
	});
});
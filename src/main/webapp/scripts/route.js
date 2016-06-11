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
	}).
	when('/allblogs',{
		templateUrl:'tmpl/allblogs.html',
		controller:'AllBlogsController'
	}).
	when('/categorylist',{
		templateUrl:'tmpl/categorylist.html',
		controller:'CategoryListController'
	}).
	when('/singleblog',{
		templateUrl:'tmpl/singleblog.html',
		controller:'SingleBlogController'
	}).
	when('/singleblogpost',{
		templateUrl:'tmpl/singleblogpost.html',
		controller:'SingleBlogPostController'
	}).
	when('/', {
		redirectTo : '/allblogs'
	})
	.otherwise({
		redirectTo : '/'
	});
});
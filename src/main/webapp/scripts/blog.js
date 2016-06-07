(function(){
	var app = angular.module('Airlines',['ngRoute','angularUtils.directives.dirPagination']);

	
	app.config(['$routeProvider', 
	    function($routeProvider){
			$routeProvider.
                when('/login',{
            		templateUrl : 'tmpl/login.html',
            		controller : 'LoginController',
            		controllerAs : 'loginCtrl'
            	}).
				when('/allblogs',{
					templateUrl:'allblogs.html',
					controller:'AllBlogsController'
				}).
				when('/singleblog',{
					templateUrl:'singleblog.html',
					controller:'SingleBlogController'
				}).
				when('/singleblogpost',{
					templateUrl:'singleblogpost.html',
					controller:'SingleBlogPostController'
				}).

				when('/signup',{
					templateUrl:'signup.html',
					controller:'SignupController'
				}).
				when('/users',{
					templateUrl:'users.html',
					controller:'UserController'
				}).
				when('/products',{
					templateUrl:'products.html',
					controller:'ProductController'
				}).
				when('/', {
            		templateUrl : 'tmpl/home.html',
            		controller : 'HomeController',
            		controllerAs : 'homeCtrl'
            	}).
				otherwise({
					redirectTo: '/'
				});
		}]);
	
	app.controller('UserController',function($http, $log, $scope){
		var controller = this;
		$scope.users=[];
		$scope.loading = true;
		$log.debug("Getting users...");
		$http.get('rest/user').
		  success(function(data, status, headers, config) {
			  $scope.users = data;
			  $scope.loading = false;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.loading = false;
			  $scope.error = status;
		  });
		
		$scope.addUser2 = function(user){
			$log.debug(user);
			$.post("rest/user",user,function(data){
				$log.debug(data);
				$scope.users.push(user);
				$scope.$digest();
			});
		}
		$scope.addUser = function(user){
			$http.post("rest/user",user)
			.success(function(data){
				$log.debug(data);
				$scope.users.push($scope.user);
			});
		};
		
		$scope.editUser = function(user){
			console.log(user);
			$scope.user = user;
			$scope.showEditForm=true;
			$scope.showAddForm=false;
		}
		
		$scope.updateUser = function(user){
			$log.debug(user);
			$http.put('rest/user',user).
			  success(function(data, status, headers, config) {
				  console.log(data);
				  $scope.showEditForm=false;
			  }).
			  error(function(data, status, headers, config) {
				  $scope.error = status;
				  $scope.showEditForm=false;
			  });

		}
	});
	
	app.controller('AddController',function($http, $log, $scope){
		
	});
	
	app.controller('ProductController',function($http, $log, $scope){
		var controller = this;
		$scope.products=[];
		$log.debug("Getting products...");

		$http.get('product.json').
		  success(function(data, status, headers, config) {
			  $scope.products = data;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.error = status;
		  });
	});

    app.controller('SignupController',function($http, $log, $scope){
		var controller = this;
		$scope.user=[];
		$log.debug("Getting blogs...");

 		$scope.addUser = function(user){
     		$log.debug("SignupController...in addUser");
 		    $log.debug(user);

            var password_confirm = user.password.localeCompare(user.confirmpassword);

            $log.debug("user.password   " + "user.confirmpassword");
            
            if(password_confirm == 0){
                $log.debug("pass word matched");
                var data = JSON.stringify(
                    {
                      name: user.name,
                      email:user.email,
                      password: user.password,
                      age:user.age
                    });
                $log.debug(data);
                $log.debug("before rest/user");
                $http.post("rest/user",data)
        			.success(function(data){
        				$log.debug(data);
                        $scope.showEditForm=true;
                        $scope.showAddForm=false;
        			})
                    .error(function(data, status, headers, config) {
                          $log.debug("error occured");
        				  $scope.error = status;
        				  $scope.showEditForm=false;
        			});
            }
            else{
                alert("passwords are not matched");
            }

		};
	});
    
	app.controller('AllBlogsController',function($http, $log, $scope, GlobalStroage){
		var controller = this;
		$scope.blogs=[];
		$log.debug("Getting blogs...");
        this.selectedBlog;

		$http.get('rest/blog').
		  success(function(data, status, headers, config) {
			  $scope.blogs = data;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.error = status;
		  });

        $scope.updateSelectedBlog = function(blog){
    		this.selectedBlog = blog;
    		$log.debug("inside updateSelectedBlog");
            $log.debug(blog);
            GlobalStroage.setSelectedBlogId(blog.id);
            $log.debug(GlobalStroage.getSelectedBlogId());
    	}
        
        $scope.convertJSONDateToJavascriptDate = function(jsonDate){
        	return new Date(jsonDate).toUTCString();
        }
	});

	app.controller('SingleBlogController',function($http, $log, $scope, GlobalStroage){
		var controller = this;
        var selectedBlogId = GlobalStroage.getSelectedBlogId();
		$scope.blog=[];
        $scope.postedDate="";
		$scope.loading = true;
		$log.debug("Getting blog in SingleBlogController...");
		$http.get('rest/blog/'+selectedBlogId).
		  success(function(data, status, headers, config) {
			  $scope.blog = data;
			  $scope.loading = false;
              $scope.postedDate = $scope.convertJSONDateToJavascriptDate($scope.blog.postedDate);
              $log.debug($scope.postedDate);
		  }).
		  error(function(data, status, headers, config) {
			  $scope.loading = false;
			  $scope.error = status;
		  });

        $scope.convertJSONDateToJavascriptDate = function(jsonDate){
        	/* var dateStr = JSON.parse(jsonDate); */
        	return new Date(jsonDate).toUTCString();
        };

            
	});

    app.service('GlobalStroage', function($log) {
        this.selectedBlogId = "defaultblogID";
        
        this.getSelectedBlogId = function(){
                $log.debug("Inside getSelectedBlogId..");
                return this.selectedBlogId;
            }        
        this.setSelectedBlogId = function(selectedBlogId){
                $log.debug("Inside setSelectedBlogId..");
                this.selectedBlogId = selectedBlogId;
            }   
    });

	app.controller('SingleBlogPostController',function($http, $log, $scope, GlobalStroage){
		var controller = this;
		$scope.blog=[];
		$scope.loading = true;
		$log.debug("SingleBlogPostController...");
        $scope.showEditForm=false;
        $scope.showAddForm=true;
        $scope.blog.title="";
        $scope.blog.synopsis="";
        $scope.blog.content="";
        $scope.blog.posterUrl="images/content_image/default.jpg";
        $scope.blog.tags="";
        $scope.blog.postedUserName="test_name";
        $scope.blog.postedUserId="5722ffa441fbd6d042b07202";

        $log.debug(GlobalStroage.getSelectedBlogId());

 		$scope.addBlog = function(blog){
     		$log.debug("SingleBlogPostController...in addBlog");
 		    $log.debug(blog);

            var data = JSON.stringify(
                    {
                      title: blog.title,
                      synopsis:blog.synopsis,
                      content: blog.content,
                      posterUrl:blog.posterUrl,
                      tags: blog.tags,
                      postedUserName : blog.postedUserName,
                      postedUserId : blog.postedUserId
                    });
                  alert( data); 

            $log.debug(data);
            $log.debug("rest/blog");
            $http.post("rest/blog",data)
    			.success(function(data){
    				$log.debug(data);
                    $scope.showEditForm=true;
                    $scope.showAddForm=false;
    			})
                .error(function(data, status, headers, config) {
                      $log.debug("error occured");
    				  $scope.error = status;
    				  $scope.showEditForm=false;
    			});
		};
	});


	app.controller('LoginController', ['$http' ,'$location', '$rootScope', function($http, $location,$rootScope){
		this.login = {};
		console.log("[AniB]: loginCtrl: ");
		this.checkUser = function(){
			str = JSON.stringify(this.login);
			console.log("[AniB]: checkUser(): " +str);
			$http.post("/login", this.login).then(
				function(response){
					//Sucess Callback
					str = JSON.stringify(response);
					console.log("[AniB]: Sucess Callback: " +str);
					
					$rootScope.user.name = response.data.username;
					$rootScope.user.isAuthenticated = true;

					$location.path('/home');
				},
				function(response){
					str = JSON.stringify(response);
					console.log("[AniB]: Failure Callback: " +response.status);
				});
			this.login = {};
		};
	}]);

	app.controller('HomeController', ['$http', function($http){
		console.log("HomeController: ");
	}]);
    
	app.controller('HeaderController',['$http' ,'$location', '$rootScope',  function($http,$location,$rootScope){
		this.user = {};		
		this.searchField = {};

		$http.get("webapi/user/").then(
			function(response){
				//Sucess Callback
				str = JSON.stringify(response);
				console.log("[AniB]: Sucess Callback: " +str);
				
			},
			function(response){
				//failure callback
				str = JSON.stringify(response);
				console.log("[AniB]: Failure Callback: " +response.status);
				if (response.status === 401 || response.status === 404 ) {
					$location.path('/login');
				}
			});
		
		this.search = function(){
			str = JSON.stringify(this.search);
			console.log("[AniB]: search(): " +str);
			this.search = {};
		};
		this.logout = function(){
			console.log("[AniB]: Logout");
			$http.get("LogoutServlet").then(
			function(response){
				//Sucess Callback
				str = JSON.stringify(response);
				console.log("[AniB]: Sucess Callback: " +str);
				if (response.status == 200) {
					$rootScope.user.name = "default";
					$rootScope.user.isAuthenticated = false;
					$location.path('/login');
				};
				
			},
			function(response){
				//failure callback
				str = JSON.stringify(response);
				console.log("[AniB]: Failure Callback: " +response.status);
				if (response.status === 401 || response.status === 404 ) {
					$location.path('/login');
				}
			});
		};


		
	}]);
    
})();


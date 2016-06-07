(function(){
	var app = angular.module('Airlines',['ngRoute','angularUtils.directives.dirPagination']);
	
	app.config(['$routeProvider', 
	    function($routeProvider){
			$routeProvider.
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
		$scope.blogs=[];
		$log.debug("Getting blogs...");

		$http.get('rest/blog').
		  success(function(data, status, headers, config) {
			  $scope.blogs = data;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.error = status;
		  });
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
        	var dateStr = JSON.parse(jsonDate);
        	return new Date(dateStr).toUTCString();
        }
	});

	app.controller('SingleBlogController',function($http, $log, $scope, GlobalStroage){
		var controller = this;
        var selectedBlogId = GlobalStroage.getSelectedBlogId();
		$scope.blog=[];
		$scope.loading = true;
		$log.debug("Getting blog in SingleBlogController...");
		$http.get('rest/blog/'+selectedBlogId).
		  success(function(data, status, headers, config) {
			  $scope.blog = data;
			  $scope.loading = false;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.loading = false;
			  $scope.error = status;
		  });

        $scope.convertJSONDateToJavascriptDate = function(jsonDate){
        	var dateStr = JSON.parse(jsonDate);
        	return new Date(dateStr).toUTCString();
        	//return new Date(dateStr).getUTCMonth();
        }

            
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

    app.service('Utility', function($log) {
        this.convertJSONDateToJavascriptDate = function(jsonDate){
        	var dateStr = JSON.parse(jsonDate);
        	return new Date(dateStr).toUTCString();
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
    
})();


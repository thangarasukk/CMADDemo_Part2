(function(){

	var app = angular.module('CMAD', ['ngRoute','angularUtils.directives.dirPagination','textAngular', 'angular-loading-bar', 'ngAnimate']).run(function($rootScope){
		$rootScope.user = {};
		$rootScope.user.name = "default";
		$rootScope.user.isAuthenticated = false;
		
	});

	

    app.controller('SignUpController',function($http, $log, $scope, $location, $rootScope){
    	console.log("[AniB]: SignUpController()");
    	this.signup = {};
		console.log("[AniB]: signupCtrl: " +this.signup);
		this.addUser = function(){
			str = JSON.stringify(this.signup);
			console.log("[AniB]: addUser(): " +str);
			$http.post("rest/user", this.signup).then(
				function(response){
					//Sucess Callback
					str = JSON.stringify(response);
					console.log("[AniB]: Sucess Callback: " +str);
					$location.path('/login');
				},
				function(response){
					str = JSON.stringify(response);
					console.log("[AniB]: Failure Callback: " +response.status);
				});
            $scope.signupForm.$setPristine();
			this.signup = {};
		};
    });
    
	app.controller('AllBlogsController',function($http, $log, $scope, GlobalStroage, $location){
		var controller = this;
		$scope.blogs={};
		console.log("[AniB]: blog.js :: AllBlogsController: " +$scope.blogs);
        this.selectedBlog = {};

		$http.get('rest/blog').
		  success(function(data, status, headers, config) {
			  $scope.blogs = data;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.error = status;
			  if (status === 401) {
					$location.path('/signup');
				}

		  });

        $scope.updateSelectedBlog = function(blog){
    		this.selectedBlog = blog;
    		$log.debug("inside updateSelectedBlog");
            $log.debug(blog);
            GlobalStroage.setSelectedBlogId(blog.id);
            $log.debug(GlobalStroage.getSelectedBlogId());
            GlobalStroage.setSelectedBlogDetails(blog);
    	}
        
        $scope.convertJSONDateToJavascriptDate = function(jsonDate){
        	return new Date(jsonDate).toUTCString();
        }

        $scope.setMostViewedCategory = function(){
    		var selectedCategory = "MostViewedCategory";
    		$log.debug("inside setMostViewedCategory");
            $log.debug(selectedCategory);
            GlobalStroage.setSelectedCategory(selectedCategory);
    	}
        $scope.setMovieTagCategory = function(){
    		var selectedCategory = "MovieTagCategory";
            $log.debug(selectedCategory);
            GlobalStroage.setSelectedCategory(selectedCategory);
    	}
        $scope.setCiscoTagCategory = function(){
    		var selectedCategory = "CiscoTagCategory";
            $log.debug(selectedCategory);
            GlobalStroage.setSelectedCategory(selectedCategory);
    	}

        $scope.setCmadTagCategory = function(){
    		var selectedCategory = "CmadTagCategory";
            $log.debug(selectedCategory);
            GlobalStroage.setSelectedCategory(selectedCategory);
    	}

        $scope.setPoliticsTagCategory = function(){
    		var selectedCategory = "PoliticsTagCategory";
            $log.debug(selectedCategory);
            GlobalStroage.setSelectedCategory(selectedCategory);
    	}

        $scope.setMilitaryTagCategory = function(){
    		var selectedCategory = "MilitaryTagCategory";
            $log.debug(selectedCategory);
            GlobalStroage.setSelectedCategory(selectedCategory);
    	}

        $scope.setGeneralTagCategory = function(){
    		var selectedCategory = "GeneralTagCategory";
            $log.debug(selectedCategory);
            GlobalStroage.setSelectedCategory(selectedCategory);
    	}

        $scope.setMyBlogsCategory = function(){
    		var selectedCategory = "MyBlogsCategory";
            $log.debug(selectedCategory);
            GlobalStroage.setSelectedCategory(selectedCategory);
    	}
	});

	app.controller('CategoryListController',function($http, $log, $scope, GlobalStroage, $location, $rootScope){
		var controller = this;
		$scope.blogs=null;
		console.log("[AniB]: blog.js :: CategoryListController");

        $scope.selectedCategory = GlobalStroage.getSelectedCategory();
        $log.debug("selectedCategory = " + $scope.selectedCategory);

        $scope.restQuery = null;
        $scope.selectedCategoryTitle = "Title";
        $scope.isMostViewedCategory = false;
        $scope.isBlogListValid = true;

        if(true == angular.equals($scope.selectedCategory, "MostViewedCategory")){
            $scope.restQuery = "rest/blog?orderBy=viewedCount";
            $scope.selectedCategoryTitle = "Most Viewed Blogs";
            $scope.isMostViewedCategory = true;
        } else if(true == angular.equals($scope.selectedCategory, "MovieTagCategory")){
            $scope.restQuery = "rest/blog?tag=movies";
            $scope.selectedCategoryTitle = "Movie related Blogs";
        } else if(true == angular.equals($scope.selectedCategory, "CiscoTagCategory")){
            $scope.restQuery = "rest/blog?tag=cisco";
            $scope.selectedCategoryTitle = "Cisco related Blogs";
        } else if(true == angular.equals($scope.selectedCategory, "CmadTagCategory")){
            $scope.restQuery = "rest/blog?tag=cmad";
            $scope.selectedCategoryTitle = "CMAD related Blogs";
        } else if(true == angular.equals($scope.selectedCategory, "PoliticsTagCategory")){
            $scope.restQuery = "rest/blog?tag=politics";
            $scope.selectedCategoryTitle = "Politics related Blogs";
        } else if(true == angular.equals($scope.selectedCategory, "MilitaryTagCategory")){
            $scope.restQuery = "rest/blog?tag=military";
            $scope.selectedCategoryTitle = "Military related Blogs";
        } else if(true == angular.equals($scope.selectedCategory, "GeneralTagCategory")){
            $scope.restQuery = "rest/blog?tag=general";
            $scope.selectedCategoryTitle = "General related Blogs";
        } else if(true == angular.equals($scope.selectedCategory, "MyBlogsCategory")){
            $scope.restQuery = "rest/blog?userName=" + $rootScope.user.name;
            $scope.selectedCategoryTitle = "My Blogs";
        }
        
        $log.debug("restQuery = " + $scope.restQuery);

        if($scope.restQuery !== null){
            $http.get($scope.restQuery).
                success(function(data, status, headers, config) {
                $scope.blogs = data;

                console.log("[AniB]: data: " +data);
                $log.debug("restQuery return success with size = " + $scope.blogs.length);
                if($scope.blogs.length <= 0){
                    $scope.isBlogListValid = false;
                }
            }).
            error(function(data, status, headers, config) {
                $scope.error = status;
                $scope.isBlogListValid = false;
                $log.debug("restQuery return error");
                if (status === 401) {
                	$location.path('/login');
                }
            });
        }

        $scope.updateSelectedBlog = function(blog){
    		this.selectedBlog = blog;
    		$log.debug("inside CategoryListController.updateSelectedBlog");
            $log.debug(blog);
            GlobalStroage.setSelectedBlogId(blog.id);
            $log.debug(GlobalStroage.getSelectedBlogId());
            GlobalStroage.setSelectedBlogDetails(blog);
    	}

        $scope.convertJSONDateToJavascriptDate = function(jsonDate){
        	return new Date(jsonDate).toUTCString();
        }


	});

	app.controller('SingleBlogController',function($http, $log, $scope, GlobalStroage, $location){
		var controller = this;
        var selectedBlogId = GlobalStroage.getSelectedBlogId();
		$scope.blog;
        $scope.postedDate="";
		$scope.loading = true;
		console.log("[AniB]: blog.js :: SingleBlogController");
        $log.debug("SingleBlogController path -- " + $location.path());
/*
		$http.get('rest/blog/'+selectedBlogId).
		  success(function(data, status, headers, config) {
			  $scope.blog = data;
			  $scope.loading = false;
              $scope.postedDate = $scope.convertJSONDateToJavascriptDate($scope.blog.postedDate);
              $log.debug($scope.postedDate);
              document.getElementById('blog_post_content').innerHTML = $scope.blog.content;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.loading = false;
			  $scope.error = status;
			  if (status === 401) {
					$location.path('/login');
				}

		  });
*/

        this.convertJSONDateToJavascriptDate = function(jsonDate){
        	/* var dateStr = JSON.parse(jsonDate); */
        	return new Date(jsonDate).toUTCString();
        };

        $scope.blog = GlobalStroage.getSelectedBlogDetails();
        $log.debug($scope.blog);
        document.getElementById('blog_post_content').innerHTML = $scope.blog.content;
        if (angular.isDefined( $scope.blog.postedDate)){
            $scope.postedDate = "on " + this.convertJSONDateToJavascriptDate($scope.blog.postedDate);
        }
        else{
            $scope.postedDate = "";
        }
            
        $log.debug("$scope.blog.postedDate is " + $scope.blog.postedDate);
        $log.debug("$scope.postedDate is " + $scope.postedDate);

        if (angular.isDefined( $scope.blog.id)){
            $http.put('rest/blog/viewedcount/'+$scope.blog.id).
                success(function(data, status, headers, config) {
                    $log.debug("rest/blog/viewedcount return success for blog id " + $scope.blog.id);
                }).
                error(function(data, status, headers, config) {
                    $log.debug("rest/blog/viewedcount return failure for blog id " + $scope.blog.id);
                });
        }
	});

    app.service('GlobalStroage', function($log) {
        this.selectedBlogId = "defaultblogID";
        this.selectedBlogDetails;
        this.selectedCategory;
        
        this.getSelectedBlogId = function(){
                $log.debug("Inside getSelectedBlogId..");
                return this.selectedBlogId;
            }
        this.setSelectedBlogId = function(selectedBlogId){
                $log.debug("Inside setSelectedBlogId..");
                this.selectedBlogId = selectedBlogId;
            }
        this.getSelectedBlogDetails = function(){
                $log.debug("Inside getSelectedBlogDetails..");
                return this.selectedBlogDetails;
            }
        this.setSelectedBlogDetails = function(selectedBlogDetails){
                $log.debug("Inside setSelectedBlogDetails..");
                this.selectedBlogDetails = selectedBlogDetails;
                $log.debug(selectedBlogDetails);
            }

        this.getSelectedCategory = function(){
                $log.debug("Inside getSelectedCategory..");
                return this.selectedCategory;
            }
        this.setSelectedCategory = function(selectedCategory){
                $log.debug("Inside setSelectedCategory..");
                this.selectedCategory = selectedCategory;
            }
    });

	app.controller('SingleBlogPostController',function($http, $log, $scope, GlobalStroage, $location, $rootScope){
		var controller = this;
		$scope.blog=[];
		$scope.loading = true;
		console.log("[AniB]: blog.js :: SingleBlogPostController");
        $scope.showEditForm=false;
        $scope.showAddForm=true;
        $scope.blog.title="";
        $scope.blog.synopsis="";
        $scope.blog.content="";
        $scope.blog.posterUrl="";
        $scope.blog.tags="general";
        $scope.blog.postedUserName=$rootScope.user.name;
        $scope.blog.postedUserId="5722ffa441fbd6d042b07202";
        $scope.selectedBlogDetails;

        $log.debug(GlobalStroage.getSelectedBlogId());

 		$scope.addBlog = function(blog){
     		$log.debug("SingleBlogPostController...in addBlog");
            $log.debug("$scope.blog.posterUrl = " + $scope.blog.posterUrl);
            if($scope.blog.posterUrl == ""){
                $scope.blog.posterUrl="images/content_image/default.jpg";
            }
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
                  /* alert( data); */
                  $scope.selectedBlogDetails = blog;

            $log.debug(data);
            $log.debug("rest/blog");
            $http.post("rest/blog",data)
    			.success(function(data){
    				$log.debug(data);
                    $scope.showEditForm=true;
                    $scope.showAddForm=false;
                    GlobalStroage.setSelectedBlogDetails($scope.selectedBlogDetails);
                    $log.debug("before setting path -- " + $location.path());
                    $location.path('singleblog');
                    $log.debug("after setting path -- " + $location.path());
                    $log.debug("$scope.selectedBlogDetails = " + $scope.selectedBlogDetails);
    			})
                .error(function(data, status, headers, config) {
                      $log.debug("error occured");
    				  $scope.error = status;
    				  $scope.showEditForm=false;
    				  if (status === 401) {
						$location.path('/login');
					}

    			});
		};
	});


	app.controller('LoginController', ['$http' ,'$location', '$rootScope', '$scope', function($http, $location,$rootScope, $scope){
		this.login = {};
		console.log("[AniB]: blog.js :: LoginController");
		this.checkUser = function(){
			str = JSON.stringify(this.login);
			console.log("[AniB]: checkUser(): " +str);
			$http.post("rest/login", this.login).then(
				function(response){
					//Sucess Callback
					str = JSON.stringify(response);
					console.log("[AniB]: Sucess Callback: " +str);
					$rootScope.user.name = response.data.username;
					$rootScope.user.isAuthenticated = true;
					$location.path('/');
				},
				function(response){
					str = JSON.stringify(response);
					console.log("[AniB]: Failure Callback: " +response.status);
				});
            $scope.loginForm.$setPristine();
			this.login = {};
		};
	}]);

	app.controller('HomeController', ['$http', function($http){
		console.log("HomeController: ");
	}]);
    
	app.controller('HeaderController',['$http' ,'$location', '$rootScope', '$scope', 'GlobalStroage',  function($http,$location,$rootScope, $scope, GlobalStroage){
		console.log("[AniB]: blog.js :: HeaderController");		
		$scope.isActive = function (viewLocation) { 
        	return viewLocation === $location.path();
    	};

        $scope.setMyBlogsCategory = function(){
            var selectedCategory = "MyBlogsCategory";
            console.log("[AniB]: selectedCategory: " +selectedCategory);
            GlobalStroage.setSelectedCategory(selectedCategory);
        }
        
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


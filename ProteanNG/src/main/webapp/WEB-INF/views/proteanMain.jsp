<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  


<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Simple Sidebar - Start Bootstrap Template</title>

  <!-- Bootstrap core CSS -->
  <link href="resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- JS Table Filter simple insensitive -->
  <!-- <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css"> -->
  <!-- Table with users --> 
  <!-- <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-css"> -->
  <!-- Table -->
  <!-- <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css"> --> 
  <!-- Custom styles for this template -->
  <link href="resources/css/simple-sidebar.css" rel="stylesheet">

</head>

<body>

  <div class="d-flex" id="wrapper">

    <!-- Sidebar -->
    <div class="bg-light border-right" id="sidebar-wrapper">
      <div class="sidebar-heading">Start Bootstrap </div>
      <div class="list-group list-group-flush">
        <a href="#" class="list-group-item list-group-item-action bg-light">Dashboard</a>
        <a href="#" class="list-group-item list-group-item-action bg-light">Shortcuts</a>
        <a href="#" class="list-group-item list-group-item-action bg-light">Overview</a>
        <a href="#" class="list-group-item list-group-item-action bg-light">Events</a>
        <a href="#" class="list-group-item list-group-item-action bg-light">Profile</a>
        <a href="#" class="list-group-item list-group-item-action bg-light">Status</a>
      </div>
    </div>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">

      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
        <button class="btn btn-primary" id="menu-toggle">Toggle Menu</button>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
            <li class="nav-item active">
              <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Dropdown
              </a>
              <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">Something else here</a>
              </div>
            </li>
          </ul>
        </div>
      </nav>
      
  <h2>Protean Data Chart</h2>
  <p>The .table-hover class enables a hover state on table rows:</p>            
  <table id="paginationNumbers" class="table table-hover">
    <thead>
      <tr>
        <th>Content</th>
        <th>Country</th>
        <th>UP_Date</th>
        <th>Create_Date</th>
        <th>User_Name</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <c:forEach items="${list}" var="proteanSelect">
          <tr>     
             <td>${proteanSelect.content}</td>
             <td>${proteanSelect.country}</td>
             <td>${proteanSelect.up_date}</td>
             <td>${proteanSelect.create_date}</td>
             <td>${proteanSelect.user_name}</td>
          </tr>           
        </c:forEach> 
      </tr>                                                                  
    </tbody>
  </table>
  
<ul class="pagination pagination-lg justify-content-center">
  <li class="page-item"><a class="page-link" href="#">《《</a></li>
  <li class="page-item"><a class="page-link" href="#">1</a></li>
  <li class="page-item"><a class="page-link" href="#">2</a></li>
  <li class="page-item"><a class="page-link" href="#">3</a></li>
  <li class="page-item"><a class="page-link" href="#">》》</a></li>
</ul>
</div>
    <!-- /#page-content-wrapper -->

  </div>
  <!-- /#wrapper -->

  <!-- Bootstrap core JavaScript -->
  <script src="resources/vendor/jquery/jquery.min.js"></script>
  <script src="resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!--   <!-- JS Table Filter simple insensitive -->
<!--   <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
  <script src="//code.jquery.com/jquery-1.11.1.min.js"></script> -->
  <!-- Table with users -->
<!--  <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
  <script src="//code.jquery.com/jquery-1.11.1.min.js"></script> -->
  <!-- Table -->
  <!-- <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
  <script src="//code.jquery.com/jquery-1.11.1.min.js"></script> -->
  <script src="resources/vendor/bootstrap/js/custom.js"></script> 
  
 <!-- Menu Toggle Script -->
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>

</body>

</html>
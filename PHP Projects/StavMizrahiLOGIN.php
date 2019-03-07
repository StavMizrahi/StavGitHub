<?php		
session_start();
define("HOST","localhost");
define("USER","root");
define("PASS","");
define("DB","hagasha2");
$init = 0;
	
	if(isset($_POST['logout']))
	{
		session_destroy();
	}
	
	if(isset($_POST['user'], $_POST['pass']))
	{
		$user = $_POST['user'];//stav
		$pass = md5($_POST['pass']);//12345 MD5
		

		//connect to server
		$con = mysqli_connect(HOST,USER,PASS,DB);
		
		if(!$con){die('Could not connect: '.mysqli_error($con));}// if database not connected
	
		$sql = "SELECT * from hagasha2a WHERE username='$user' AND password='$pass'";
		$res = mysqli_query($con,$sql);
		if (mysqli_num_rows($res)==1)
		{	
			$init = 1;
			$_SESSION['username'] = $user;
			$_SESSION['password'] = $pass;
			
		echo "<fieldset style='width:400px; 	hieght:300px; 	background:skyblue;'>
		<h1>Hello ".$user."</h1>
		<br><button><a href='?loguot'>Log out</a></button>
		</fieldset>
		";
		}
		else
		{//false information
			header('Refresh:2;url=login1.php');
			echo "<h1>premission denide!!!</h1>";
			exit();
		}
		
		mysqli_close($con);	
	}
	
	if($init == 0 )
	{
		echo "<form method='post' >
		<fieldset style='width:400px; 	hieght:300px; 	background:skyblue;'>
		User name:<input type='text' name='user'><br><br>
		Password :<input type='password' name='pass'><br><br>
		<input type='submit' value='LOG IN'>
		</fieldset>
		</form>";
	}
	
?>
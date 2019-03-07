<?php

if(!empty($_GET))
{
	if(isset($_GET["my_answer"]))
	{
		$points = (int)$_GET["points"];
		$guessing = (int)$_GET["my_answer"];
		$num1 = (int)$_GET["num1"];
		$num2 = (int)$_GET["num2"];
		$turn = (int)$_GET["turn"];
		$answer = (int)$_GET["answer"];
		
		if ($turn < 5)
		{	
		
			
			if (md5($guessing) == $answer)
			{
				$points++;
				$turn++;
				
			}
			else
			{
				$turn++;
			}
		}
		else
		{
			$score = round($points*100/$turn);
			?>
				<form method = "GET" action="<?php echo $_SERVER["PHP_SELF"]; ?>" >
				<fieldset style="padding:20px; font-size:30px; hieght:400px; width:300px; background:darkturquoise;	border-style:none;">
				<?php if(md5($guessing) === $answer){echo "Correct<br>";} ?>
				<br><br><hr>
				<?php echo "Your score is: $score<br>" ?>
				<br>
					<input type="submit" value="NEW Session" />
				<br>	
				<br>
				</fieldset>		
				</form>
	<?php
			exit();
		}
	}
	$num1 = mt_rand(1,10);
	$num2 = mt_rand(1,10);
}
else{
	$points = 0;
	$turn = 1;
	$guessing = -1;
	$num1 = mt_rand(1,10);
	$num2 = mt_rand(1,10);
	$answer = 0;
	
}		
?>
	
		

<!DOCTYPE html>
<html>
<head>
	<style>
		fieldset { padding:20px;font-size:20px;
		width:250px; 
		hieght:400px; 	
		background:darkturquoise;	
		border-style: none;
			}
		h1 {color:red;}	
	</style>
</head>
<body>

	<form method = "GET" action="<?php echo $_SERVER["PHP_SELF"]; ?>" >
	
	<fieldset>
	<?php if(md5($guessing) == $answer){echo "Correct<br>";} ?>
	<br><br>
	Points: <?php echo " $points<br>" ?>
	Turn: <?php echo " $turn<br>" ?>
	<hr>
	<h1><?php echo "$num1 x $num2 = " ?></h1>
		<input type="text" name="my_answer" autofocus >
		
		<input type="hidden" name="num1" value="<?php echo $num1; ?>" >
		<input type="hidden" name="num2" value="<?php echo $num2; ?>" >
		<input type="hidden" name="points"  value="<?php echo $points; ?>">
		<input type="hidden" name="turn"  value="<?php echo $turn; ?>">
		<input type="hidden" name="answer"  value="<?php echo md5($num1*$num2);  ?>">
		<br><br>
		
		<button type="submit" name="submit" value="submit" >submit</button>
	</fieldset>
	</form>

</body>
</html>
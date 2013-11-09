package com.example.hackyaleandroid;

public class Week {

	public Boolean Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;
	
	public Week()
	{
		Monday = Tuesday = Wednesday = Thursday = Friday = Saturday = Sunday = false;
	}
	public Week(Boolean M, Boolean T, Boolean W, Boolean Th, Boolean F, Boolean Sat, Boolean Sun)
	{
		Monday = M;
		Tuesday = T;
		Wednesday = W;
		Thursday = Th;
		Friday = F;
		Saturday = Sat;
		Sunday = Sun;
	}
}

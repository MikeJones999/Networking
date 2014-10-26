package Chapter3.Threads;

public class ThreadHelloCount
{
	public static void main (String[] args)
	{
		HelloThread hello = new HelloThread();
		CountThread count = new CountThread();
		
		
		//inherits the thread class - therefore cn utilise run() method.
		hello.start();
		count.start();
		
		
	}	
}



class HelloThread extends Thread
{
	public void run()
	{
		int pause;
		
		for (int i = 0; i < 5; i++)
		{
			System.out.println("Hello");
			
			pause = (int) (Math.random() * 3000);
			
		}
		
		
	}
	
	
}

class CountThread extends Thread
{
	public void run()
	{
		int pause;
		
		for (int i = 0; i < 5; i++)
		{
			System.out.println(i);
			
			pause = (int) (Math.random() * 3000);
			
		}
		
		
	}
	
}



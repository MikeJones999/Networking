package Chapter3.Threads;

public class TestThread
{
	public static void main(String[] args)
	{
		//two different ways to run a thread using runnable
		Thread one = new Thread(new RunnerThread());
		RunnerThread twoThread = new RunnerThread();
		Thread two = new Thread(twoThread);
		
		one.start();
		two.start();	
		
	}	
	
}

class RunnerThread implements Runnable 
{

		
	public void run()
	{
		int pause;
		
		for(int i = 0; i < 5; i++)
		{
			
			//need try catch for the interrupt
			try {
				
				//	as threads here do not extend thread - must use thread class to obtain thread name
				System.out.println(Thread.currentThread().getName());
				//cast to int as it returns naturally a double
				pause = (int) (Math.random() * 3000);
				
				//put thread to sleep
				Thread.sleep(pause);
				}
			catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
	
}
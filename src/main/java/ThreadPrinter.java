package main.java;

public class ThreadPrinter {
	
	boolean running = false;
	
    public static void main( String args[] ) {
    	ThreadPrinter tp = new ThreadPrinter();
    	
        Printer1 printer1 = new Printer1(0);
        printer1.setName("Printer1-");
        printer1.setThreadPrinter( tp );
        Printer2 printer2 = new Printer2(1);
        printer2.setName("Printer2-");
        printer2.setThreadPrinter( tp );

        printer1.start();
        printer2.start();
    }

    public synchronized void print1( int num ) throws InterruptedException {
    	if( running ) {
    		wait();
    	}
    	running = true;
    	System.out.println( Thread.currentThread().getName()+num);
    	notifyAll();
    }
    
    public synchronized void print2( int num ) throws InterruptedException {
    	if( !running ) {
    		wait();
    	}
    	running = false;
    	System.out.println( Thread.currentThread().getName()+num);
    	notifyAll();
    }
    
}

class Printer1 extends Thread {
	private int start;
	private ThreadPrinter tp;
	
	public Printer1( int start ) {
		this.start = start;
	}
	
	public void setThreadPrinter( ThreadPrinter tp ) {
		this.tp = tp;
	}
	
	public void run() {
		for( int i=0; i < 10; i++ ) {
			try {
				tp.print1(start);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			start += 2;
		}
	}
}

class Printer2 extends Thread {
	private int start;
	private ThreadPrinter tp;
	
	public Printer2( int start ) {
		this.start = start;
	}
	
	public void setThreadPrinter( ThreadPrinter tp ) {
		this.tp = tp;
	}
	
	public void run() {
		for( int i=0; i < 10; i++ ){
			try {
				tp.print2(start);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			start += 2;
		}
	}
}

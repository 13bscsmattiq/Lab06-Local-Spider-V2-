import java.util.*;

public class Others {

	 LinkedList<String> direct;
	 private int size; 
	 private boolean finished; 
	 
	 
	 
	public synchronized void add(String s) {
			direct.add(s);
			size++;
			notifyAll();
		}
	

	public Others() {
		direct = new LinkedList<String>();
		finished = false;
		size = 0;
	}

	

	public synchronized String delete() {
		String s;
		while (!finished && size == 0) {
			try {
				wait();
			} catch (Exception e) {};
		}
		if (size > 0) {
			s = direct.remove();
			size--;
			notifyAll();
		} else
			s = null;
		return s;
	}

	public synchronized void finish() {
		finished = true;
		notifyAll();
	}
}

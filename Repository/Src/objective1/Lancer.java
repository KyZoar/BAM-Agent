package objective1;

public class Lancer {
	
	public static void main(String[] args){
		ServerHotel serv1 = new ServerHotel();
		ServerHotel serv2 = new ServerHotel();
		ServerHotel serv3 = new ServerHotel();
		ServerHotel serv4 = new ServerHotel();
		String[] servArgu = new String[3];
		
		
		Thread t1 =new Thread(){
			public void run() {
				servArgu[0] = "2222";
				servArgu[1] = "chaine";
				servArgu[2] = "1";
				serv1.main(servArgu);
			}
		};
		
		Thread t2 = new Thread(){
			public void run(){
				servArgu[0] = "3333";
				servArgu[1] = "chaine";
				servArgu[2] = "2";
				serv2.main(servArgu);
			}
		};
		
		Thread t3 = new Thread(){
			public void run(){
				servArgu[0] = "4444";
				servArgu[1] = "chaine";
				servArgu[2] = "3";
				serv3.main(servArgu);
			}
		};

		Thread t4 = new Thread(){
			public void run(){
				servArgu[0] = "5555";
				servArgu[1] = "annuaire";
				servArgu[2] = "4";
				serv4.main(servArgu);
				
			}
		};
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
	/*	String[] s=new String[1];
		s[0]="Paris";
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LookForHotel lf = new LookForHotel(s);*/
	}
}

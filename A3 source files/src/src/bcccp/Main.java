
package bcccp;

import java.awt.EventQueue;
import bcccp.carpark.CarSensor;
import bcccp.carpark.Carpark;
import bcccp.carpark.Gate;
import bcccp.carpark.entry.EntryController;
import bcccp.carpark.entry.EntryUI;
import bcccp.carpark.exit.ExitController;
import bcccp.carpark.exit.ExitUI;
import bcccp.carpark.paystation.PaystationController;
import bcccp.carpark.paystation.PaystationUI;
import bcccp.tickets.adhoc.AdhocTicketFactory;
import bcccp.tickets.adhoc.IAdhocTicket;
import bcccp.tickets.adhoc.AdhocTicketDAO;
import bcccp.tickets.adhoc.IAdhocTicketDAO;
import bcccp.tickets.season.ISeasonTicket;
import bcccp.tickets.season.ISeasonTicketDAO;
import bcccp.tickets.season.SeasonTicket;
import bcccp.tickets.season.SeasonTicketDAO;
import bcccp.tickets.season.UsageRecordFactory;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CarSensor eos = new CarSensor("Entry Outside Sensor", 20, 100);
					Gate egate = new Gate(20, 320);
					CarSensor eis = new CarSensor("Entry Inside Sensor", 20, 440);
					EntryUI eui = new EntryUI(320, 100);	
					
					PaystationUI pui = new PaystationUI(660, 100);
					
					ExitUI xui = new ExitUI(1000, 100);	
					CarSensor xis = new CarSensor("Exit Inside Sensor", 1330, 100);
					Gate xgate = new Gate(1330, 320);
					CarSensor xos = new CarSensor("Exit Outside Sensor", 1330, 440);
					
					IAdhocTicketDAO adhocTicketDAO = new AdhocTicketDAO(new AdhocTicketFactory());
					ISeasonTicketDAO seasonTicketDAO = new SeasonTicketDAO(new UsageRecordFactory());
					
					Carpark carpark = new Carpark("Bathurst Chase", 5, adhocTicketDAO, seasonTicketDAO);
					Carpark carpark2 = new Carpark("Anh Nguyen", 10, adhocTicketDAO, seasonTicketDAO);
					
										
					ISeasonTicket t1 = new SeasonTicket("S1111","Bathurst Chase", 0L, 0L);
					ISeasonTicket t2 = new SeasonTicket("S2222","Bathurst Chase", 0L, 0L);
					ISeasonTicket t3 = new SeasonTicket("test1","Anh Nguyen", 0L, 0L);
					ISeasonTicket t4 = new SeasonTicket("test2","Anh Nguyen", 0L, 0L);
					
					
					carpark.registerSeasonTicket(t1);
					carpark.registerSeasonTicket(t2);
					carpark2.registerSeasonTicket(t3);
					carpark2.registerSeasonTicket(t4);
					
					//issue a ticket so that paystation can be tested
					carpark.issueAdhocTicket();
					carpark.recordAdhocTicketEntry();
					carpark.recordSeasonTicketEntry(t1.getId());
					carpark.recordSeasonTicketEntry(t2.getId());
					
					//issue a ticket for carpark2
					carpark2.issueAdhocTicket();
					carpark2.recordAdhocTicketEntry();
					carpark2.recordSeasonTicketEntry(t3.getId());
					
					IAdhocTicket ticket = carpark.issueAdhocTicket();
					ticket.pay(System.currentTimeMillis(), 5.0f);
					IAdhocTicket ticket2 = carpark2.issueAdhocTicket();
					ticket2.pay(System.currentTimeMillis(), 5.0f);
					
					@SuppressWarnings("unused")
					EntryController entryController = 
							new EntryController(carpark2, egate, eos, eis, eui);
					
					@SuppressWarnings("unused")
					PaystationController payController = 
							new PaystationController(carpark2, pui);
					
					@SuppressWarnings("unused")
					ExitController exitController = 
					new ExitController(carpark2, xgate, xis, xos, xui);
					
					eos.setVisible(true);
					egate.setVisible(true);
					eis.setVisible(true);
					eui.setVisible(true);
					
					pui.setVisible(true);
					
					xui.setVisible(true);
					xis.setVisible(true);
					xgate.setVisible(true);
					xos.setVisible(true);
					
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

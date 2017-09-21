package bcccp.tickets.adhoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdhocTicketDAO  implements IAdhocTicketDAO  {

	private Map<String, IAdhocTicket> currentTickets;
	private IAdhocTicketFactory adhocTicketFactory_;
	private int currentTicketNo;

	
	
	public AdhocTicketDAO(IAdhocTicketFactory factory) {
		//TODO Implement constructor           
            this.factory = factory;
            
	}

	
	
	@Override
	public IAdhocTicket createTicket(String carparkId) {
		IAdhocTicket ticket = adhocTicketFactory_.make(carparkId, ++currentTicketNo);
		currentTickets.put(ticket.getBarcode(), ticket);
		return ticket;	
	}
	
	
	
	@Override
	public IAdhocTicket findTicketByBarcode(String barcode) {
		return currentTickets.get(barcode);
	}	

	
	
	@Override
	public List<IAdhocTicket> getCurrentTickets() {
		
		return this.ticketList;
	}



}

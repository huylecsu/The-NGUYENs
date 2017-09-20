package bcccp.carpark;

import java.util.ArrayList;
import java.util.List;

import bcccp.tickets.adhoc.IAdhocTicket;
import bcccp.tickets.adhoc.IAdhocTicketDAO;
import bcccp.tickets.season.ISeasonTicket;
import bcccp.tickets.season.ISeasonTicketDAO;

public class Carpark implements ICarpark {
	
	private List<ICarparkObserver> observers;
	private String carparkId;
	private int capacity;
	private int nParked;
	private IAdhocTicketDAO adhocTicketDAO;
	private ISeasonTicketDAO seasonTicketDAO;
	
	
	
	public Carpark(String name, int capacity, 
			IAdhocTicketDAO adhocTicketDAO, 
			ISeasonTicketDAO seasonTicketDAO) {
		this.carparkId = name;
		this.capacity = capacity;
		observers = new ArrayList<>();
		this.adhocTicketDAO = adhocTicketDAO;
		this.seasonTicketDAO = seasonTicketDAO;
	}

	
	
	@Override
	public void register(ICarparkObserver observer) {
		// Registers observer as an entity 
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	
	
	@Override
	public void deregister(ICarparkObserver observer) {
 
		// remove observer as an entity to be notified 
		// Auto_generated stub

		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}
	
	
	
	private void notifyObservers() {
		// notifyCarparkEvent method notify the registers observer when the carpark is full and
		// spaces become available
		for (ICarparkObserver observer : observers) {
			observer.notifyCarparkEvent();
		}
	}

	
	
	@Override
	public String getName() {
		// return the carpark name
		return carparkId;
	}
	
	
	
	@Override
	// Return a boolean indicating whether the carpark is full
	public boolean isFull() {
		return nParked + seasonTicketDAO.getNumberOfTickets() == capacity;
	}
	
	
	
	@Override
	public IAdhocTicket issueAdhocTicket() {
		return adhocTicketDAO.createTicket(carparkId);
	}
	
	
	@Override
	public IAdhocTicket getAdhocTicket(String barcode) {
		return adhocTicketDAO.findTicketByBarcode(barcode);
	}
	
	
		
	@Override
	public float calculateAddHocTicketCharge(long entryDateTime) {
		//TODO Implement charge logic
		return 3.0f;
	}

	
	
	@Override
	public boolean isSeasonTicketValid(String barcode) {		
		ISeasonTicket ticket = seasonTicketDAO.findTicketById(barcode);
		
		// Updated
		Date date = new Date();
		Calendar c = Calendar.getInstance();
            	c.setTime(date);
            	int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //returns day of week as int, 1 is Sunday and 7 is Saturday
            	if(dayOfWeek != 1 || dayOfWeek != 7){ 
            	//if it is not a weekend            
	if(numberOfCarsParked >= (capacity*.9))
			return true;
		else
	return false;
            }
            if(dayOfWeek == 1 || dayOfWeek == 7){
            //if it is a weekend
                if(numberOfCarsParked >= capacity)
                    return true;
                else
                    return false;
            }
            else
		return false;
	}
	

	
	
	@Override
	public void registerSeasonTicket(ISeasonTicket seasonTicket) {
		seasonTicketDAO.registerTicket(seasonTicket);		
	}



	@Override
	public void deregisterSeasonTicket(ISeasonTicket seasonTicket) {
		seasonTicketDAO.deregisterTicket(seasonTicket);		
	}

	
	
	@Override
	public void recordSeasonTicketEntry(String ticketId) {
		ISeasonTicket ticket = seasonTicketDAO.findTicketById(ticketId);
		if (ticket == null) throw new RuntimeException("recordSeasonTicketEntry: invalid ticketId - " + ticketId);
		
		seasonTicketDAO.recordTicketEntry(ticketId);
		log(ticket.toString());
	}

	
	
	private void log(String message) {
		System.out.println("Carpark : " + message);
	}



	@Override
	public void recordAdhocTicketEntry() {
		nParked++;
		
	}



	@Override
	public void recordAdhocTicketExit() {
		nParked--;
		notifyObservers();		
	}



	@Override
	public void recordSeasonTicketExit(String ticketId) {
		ISeasonTicket ticket = seasonTicketDAO.findTicketById(ticketId);
		if (ticket == null) throw new RuntimeException("recordSeasonTicketExit: invalid ticketId - " + ticketId);
		
		seasonTicketDAO.recordTicketExit(ticketId);
		log(ticket.toString());
	}



	@Override
	public boolean isSeasonTicketInUse(String ticketId) {
		ISeasonTicket ticket = seasonTicketDAO.findTicketById(ticketId);
		if (ticket == null) throw new RuntimeException("recordSeasonTicketExit: invalid ticketId - " + ticketId);
		
		return ticket.inUse();
	}

















}

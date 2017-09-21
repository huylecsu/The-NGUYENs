package bcccp.tickets.season;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeasonTicket implements ISeasonTicket {
	
	private List<IUsageRecord> usages;
	private IUsageRecord currentUsage = null;
	
	private String ticketId;
	private String carparkId;
	private long startValidPeriod;
	private long endValidPeriod;


	public SeasonTicket (String ticketId, String carparkId, 
			             long startValidPeriod,
			             long endValidPeriod) {
		this.ticketId = ticketId;
		this.carparkId =carparkId;
		this.startValidPeriod = startValidPeriod;
		this.endValidPeriod = endValidPeriod;
		this.currentUsage = new UsageRecord(ticketId, System.currentTimeMillis());
		usages = new ArrayList<IUsageRecord>();

	}


	@Override
	public String getId() {
		return ticketId;
	}


	@Override
	public String getCarparkId() {
		return carparkId;
	}


	@Override
	public long getStartValidPeriod() {
		return startValidPeriod;
	}


	@Override
	public long getEndValidPeriod() {
		return endValidPeriod;
	}


	@Override
	public boolean inUse() {
		if (currentUsage.getStartTime() != 0 && currentUsage.getEndTime() == 0) {
               		return true; 
           	}
           	else {
                	return false;
           	 }
	}


	@Override
	public void recordUsage(IUsageRecord record) {
		currentUsage = record;
		if (!usages.contains(record) ) {
			usages.add(record);
		}
		
	}


	@Override
	public IUsageRecord getCurrentUsageRecord() {
		for (IUsageRecord u : usages) {
               		if (this.ticketId.equals(u.getSeasonTicketId())) {
                    		return u;
			}
           	 }
            return null;
	}


	@Override
	public List<IUsageRecord> getUsageRecords() {
		return Collections.unmodifiableList(usages);
	}


	@Override
	public void endUsage(long dateTime) {
		if (currentUsage == null) throw new RuntimeException("SeasonTicket.endUsage : ticket is not in use");
		
		currentUsage.finalise(dateTime);
		currentUsage = null;
		
	}


	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Carpark    : " + carparkId + "\n" +
		       "Ticket No  : " + ticketId + "\n" );
		for (IUsageRecord usage : usages) {
			builder.append(usage.toString() + "\n");
		}
		return builder.toString();
	}


}

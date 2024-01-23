package reflection.section_6.modifiers.example.auction;

import java.util.*;

public class Auction {
    private final List<Bid> bids = new ArrayList<>();
    private transient volatile boolean isAuctionStarted;
    public synchronized void addBid(Bid bid){
        this.bids.add(bid);
    }
    public synchronized List<Bid> getAllBids(){
        return Collections.unmodifiableList(bids);
    }
    public synchronized Optional<Bid> getHighestBid(){
        return bids.stream().max(Comparator.comparing(Bid::getPrice));
    }

    public void startAuction(){
        this.isAuctionStarted=true;
    }

    public void stopAuction(){
        this.isAuctionStarted=false;
    }

    public boolean isAuctionStarted(){
        return this.isAuctionStarted;
    }
}

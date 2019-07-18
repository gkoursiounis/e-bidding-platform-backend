package com.Auctions.backEnd.controllers;

import com.Auctions.backEnd.models.*;
import com.Auctions.backEnd.repositories.*;
import com.Auctions.backEnd.responses.BidRes;
import com.Auctions.backEnd.responses.Message;
import com.Auctions.backEnd.services.File.DBFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/bid")
public class BidController extends BaseController {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BidRepository bidRepository;

    @Autowired
    public BidController(UserRepository userRepository, ItemRepository itemRepository,
                          BidRepository bidRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.bidRepository = bidRepository;
    }


    /**
     * User can participate to an auction making a bid
     *
     * @param offer
     * @return created bid
     */
    @GetMapping("/makeBid/{itemId}")
    public ResponseEntity makeBid(@PathVariable (value = "itemId") long itemId,
                                  @RequestParam Double offer) {

        User requester = requestUser();

        Item item = itemRepository.findItemById(itemId);
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
                    "Error",
                    "Item not found. Invalid item Id"
            ));
        }

        if(item.isAuctionCompleted()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(
                    "Error",
                    "Auction has been completed and no bids can be made"
            ));
        }

        if(item.getSeller().equals(requester)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(
                    "Error",
                    "You cannot bid at your own auction"
            ));
        }

        if((!item.getBids().isEmpty() && java.lang.Double.compare(offer, item.getCurrently()) <= 0) ||
                (item.getBids().isEmpty() && java.lang.Double.compare(offer, item.getFirstBid()) < 0)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(
                    "Error",
                    "Offer cannot be less than the current best offer or the initial price"
            ));
        }

        item.setCurrently(offer);
        if(java.lang.Double.compare(item.getBuyPrice(), offer) >= 0){
            item.setAuctionCompleted(true);
        }
        itemRepository.save(item);

        //??????
        item.getSeller().getItems().add(item);
        userRepository.save(item.getSeller());

        Bid bid = new Bid();
        bid.setBidder(requester);
        bid.setItem(item);
        bid.setOffer(offer);
        bidRepository.save(bid);

        requester.getBids().add(bid);
        userRepository.save(requester);

        return ResponseEntity.ok(new BidRes(bid, item.isAuctionCompleted()));
    }
}